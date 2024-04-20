package chat;

public interface IChatServerCallback extends IChatCallback {
    void handleNewConnection(ChatClient client);
}
