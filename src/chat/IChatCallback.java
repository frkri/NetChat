package chat;

import message.Message;

public interface IChatCallback {
    void handleCloseConnection(ChatClient client);

    void handleNewMessage(Message message, ChatClient source);

    void handleSendMessage(Message message, ChatClient source);
}
