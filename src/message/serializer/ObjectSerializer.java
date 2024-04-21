package message.serializer;

import message.Message;

import java.io.*;

public class ObjectSerializer extends MessageSerializer {

    public static Message deserialize(ObjectInputStream in) throws IllegalArgumentException {
        try {
            return (Message) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new IllegalArgumentException("Invalid message format", e);
        }
    }

    public static Message deserialize(byte[] serializedMessage) throws IllegalArgumentException {
        try {
            var bis = new ByteArrayInputStream(serializedMessage);
            var in = new ObjectInputStream(bis);
            return (Message) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new IllegalArgumentException("Invalid message format", e);
        }
    }

    public static byte[] serialize(Message message) throws IOException {
        checkNull(message);

        var bos = new ByteArrayOutputStream();
        var out = new ObjectOutputStream(bos);
        out.writeObject(message);
        return bos.toByteArray();
    }

}
