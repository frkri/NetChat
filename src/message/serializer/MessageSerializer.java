package message.serializer;

import message.Message;

public class MessageSerializer {

    protected static void checkNull(Message message) throws NullPointerException, IllegalArgumentException {
        if (message == null) throw new NullPointerException("Message cannot be null");
        if (message.getContent() == null) throw new IllegalArgumentException("Message content cannot be null");
        if (message.getType() == null) throw new IllegalArgumentException("Message type cannot be null");
        if (message.getDate() == null) throw new IllegalArgumentException("Message date cannot be null");
    }

}
