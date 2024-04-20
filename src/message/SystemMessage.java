package message;

import java.time.LocalDateTime;

public class SystemMessage extends Message {

    public SystemMessage(LocalDateTime date, String content) {
        super(date, content);
    }

    public SystemMessage(String content) {
        super(content);
    }

    @Override
    public String toString() {
        return "[System] " + super.toString();
    }
}
