package yaff;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Type;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.HashMap;

public class Yaff {
    private final int MAGIC_NUMBER = 0x79616666; // 'yaff' -> Yet Another File Format
    private final int VERSION = 0;
    private final int HEADER_SIZE = Integer.BYTES * 4;
    private final HashMap<Integer, IYaffSerializer<?>> serializers;
    private final HashMap<Integer, Integer> classId;

    public Yaff() {
        this.serializers = new HashMap<>();
        this.classId = new HashMap<>();
    }

    public void registerSerializer(Type type, IYaffSerializer<?> serializer) {
        if (serializer == null) throw new RuntimeException("Serializer cannot be null");

        serializers.put(serializer.getUniqueIdentifier(), serializer);
        classId.put(type.getTypeName().hashCode(), serializer.getUniqueIdentifier());
    }

    public byte[] serialize(Object object) throws YaffException {
        //noinspection unchecked, safe enough ¯\_(ツ)_/¯
        var serializer = (IYaffSerializer<Object>) serializers.get(classId.get(object.getClass().getTypeName().hashCode()));
        var data = serializer.serialize(object);

        var buffer = ByteBuffer.allocate(HEADER_SIZE + data.length);
        buffer.putInt(MAGIC_NUMBER);
        buffer.putInt(VERSION);
        buffer.putInt(serializer.getUniqueIdentifier());
        buffer.putInt(data.length);
        buffer.put(data);

        return buffer.array();
    }

    public void serialize(Object object, OutputStream stream) throws IOException, YaffException {
        stream.write(serialize(object));
        stream.flush();
    }

    public Object deserialize(byte[] data) throws YaffException {
        var buffer = ByteBuffer.wrap(data);
        verifyHeader(buffer);
        var networkId = buffer.getInt();
        var dataLength = buffer.getInt();

        assert HEADER_SIZE == buffer.position();
        assert dataLength == data.length - HEADER_SIZE;

        //noinspection unchecked, safe enough ¯\_(ツ)_/¯
        var serializer = (IYaffSerializer<Object>) serializers.get(networkId);
        return serializer.deserialize(Arrays.copyOfRange(data, HEADER_SIZE, dataLength));
    }

    public Object deserialize(InputStream stream) throws YaffException, IOException {
        var header = new byte[HEADER_SIZE];
        if (stream.readNBytes(header, 0, HEADER_SIZE) != HEADER_SIZE) throw new YaffException("Failed to read header");

        var headerBuffer = ByteBuffer.wrap(header);
        verifyHeader(headerBuffer);
        var networkId = headerBuffer.getInt();
        var dataLength = headerBuffer.getInt();

        assert HEADER_SIZE == headerBuffer.position();
        var data = new byte[dataLength];
        if (stream.readNBytes(data, 0, dataLength) != dataLength) throw new YaffException("Failed to read data");

        //noinspection unchecked, safe enough ¯\_(ツ)_/¯
        var serializer = (IYaffSerializer<Object>) serializers.get(networkId);
        return serializer.deserialize(data);
    }

    private void verifyHeader(ByteBuffer buffer) throws YaffException {
        if (buffer.getInt() != MAGIC_NUMBER) throw new YaffException("Magic number mismatch");
        if (buffer.getInt() != VERSION) throw new YaffException("Unsupported version");
    }
}

