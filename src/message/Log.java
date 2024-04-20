package message;

import javax.swing.*;
import java.io.Serial;

public class Log extends JList<Message> {
    @Serial
    private static final long serialVersionUID = -9158233918306677059L;
    private final DefaultListModel<Message> messages;

    public Log() {
        super();
        this.messages = new DefaultListModel<>();

        super.setModel(messages);
        super.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }

    public synchronized void addMessage(Message message) {
        messages.addElement(message);
        if (!super.isFocusOwner()) super.ensureIndexIsVisible(messages.indexOf(message));
    }

    public synchronized void clear() {
        messages.clear();
    }

}
