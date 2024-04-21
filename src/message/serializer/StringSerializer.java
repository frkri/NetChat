package message.serializer;

import message.Message;
import message.MessageType;
import yaff.IYaffSerializer;
import yaff.YaffException;

import java.time.LocalDateTime;
import java.time.ZoneOffset;


public class StringSerializer implements IYaffSerializer<Message> {
    protected static final ZoneOffset ZONE_OFFSET = ZoneOffset.UTC;
    private static final String DELIMITER = ";";

    public byte[] serialize(Message message) {
        if (message.getContent().contains(DELIMITER)) // TODO: fix bad implementation
            throw new IllegalArgumentException("Message content cannot contain delimiter");

        var parts = new String[]{message.getType().toString(), String.valueOf(message.getDate().toEpochSecond(ZONE_OFFSET)), message.getContent()};
        System.out.println("Using StringSerializer to serialize message");
        return String.join(DELIMITER, parts).getBytes();
    }

    @Override
    public Message deserialize(byte[] data) throws YaffException {
        var parts = new String(data).split(DELIMITER);
        if (parts.length != 3) throw new YaffException("Invalid message format");

        var date = LocalDateTime.ofEpochSecond(Long.parseLong(parts[1]), 0, ZONE_OFFSET);
        System.out.println("Using StringSerializer to deserialize message");
        return new Message(MessageType.valueOf(parts[0]), date, parts[2]);
    }

    @Override
    public int getUniqueIdentifier() {
        return ContentType.String.ordinal();
    }
}
