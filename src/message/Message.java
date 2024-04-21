package message;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

public class Message implements Serializable {
    private final LocalDateTime date;
    private final String content;
    private final MessageType type;

    public Message(MessageType type, LocalDateTime date, String content) {
        this.type = type;
        this.date = date;
        this.content = content;
    }

    public Message(MessageType type, String content) {
        this.type = type;
        this.date = LocalDateTime.now();
        this.content = content;
    }

    public Message(String content) {
        this.type = MessageType.User;
        this.date = LocalDateTime.now();
        this.content = content;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public String getContent() {
        return content;
    }

    public MessageType getType() {
        return type;
    }

    @Override
    public String toString() {
        return (type == null ? "" : String.format("[%s] ", type)) + String.format("%s %s", date.format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT)), content);
    }
}
