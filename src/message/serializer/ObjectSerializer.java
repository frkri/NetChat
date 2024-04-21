package message.serializer;

import message.Message;
import yaff.IYaffSerializer;
import yaff.YaffException;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class ObjectSerializer implements IYaffSerializer<Message> {


    @Override
    public byte[] serialize(Message obj) throws YaffException {
        var out = new ByteArrayOutputStream();
        try {
            var obOut = new ObjectOutputStream(out);
            System.out.println("Using ObjectSerializer to serialize message");
            obOut.writeObject(obj);
        } catch (Exception e) {
            throw new YaffException("Failed to serialize object", e);
        }

        return out.toByteArray();
    }

    @Override
    public Message deserialize(byte[] data) throws YaffException {
        var in = new ByteArrayInputStream(data);
        try {
            var obIn = new ObjectInputStream(in);
            System.out.println("Using ObjectSerializer to deserialize message");
            return (Message) obIn.readObject();
        } catch (Exception e) {
            throw new YaffException("Failed to deserialize object", e);
        }
    }

    @Override
    public int getUniqueIdentifier() {
        return ContentType.Object.ordinal();
    }
}
