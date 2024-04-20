package message;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

public class Message {
    private final LocalDateTime date;
    private final String content;

    public Message(LocalDateTime date, String content) {
        this.date = date;
        this.content = content;
    }

    public Message(String content) {
        this.date = LocalDateTime.now();
        this.content = content;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public String getContent() {
        return content;
    }

    @Override
    public String toString() {
        return date.toLocalTime().format(DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT)) + " " + content;
    }
}
