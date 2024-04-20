package chat;

import common.ISocketListenerCallback;
import common.SocketListener;
import message.Message;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;

public class ChatServer implements ISocketListenerCallback, IChatCallback {
    private final SocketListener listener;
    private final IChatServerCallback callback;
    private final ArrayList<ChatClient> clients;
    private int clientCount = 0;

    public ChatServer(InetSocketAddress address, IChatServerCallback callback) throws IOException {
        this.callback = callback;
        clients = new ArrayList<>();
        listener = new SocketListener(address, this);
    }

    public void serve() {
        if (listener.isRunning())
            throw new RuntimeException("Tried to start new instance of server but failed as the server is already in running phase");
        listener.start();
    }

    public void stop() throws IOException {
        if (!listener.isRunning()) throw new RuntimeException("Cannot stop server that is not in running phase");
        listener.stopServer();
        clients.forEach(ChatClient::stopClient);
        assert clients.isEmpty();
    }

    @Override
    public void handleNewSocket(Socket clientEndpoint) {
        try {
            var client = new ChatClient(clientEndpoint, this);
            clients.add(client);
            clientCount = clients.size();

            callback.handleNewConnection(client);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void broadcastMessage(Message message) {
        clients.forEach(chatClient -> chatClient.sendMessage(message));
    }

    public void broadcastMessage(Message message, ChatClient source) {
        clients.stream().filter(client -> client != source).forEach(chatClient -> chatClient.sendMessage(message));
    }

    public int getClientCount() {
        return clientCount;
    }

    @Override
    public void handleCloseConnection(ChatClient client) {
        clients.remove(client);
        clientCount = clients.size();
        callback.handleCloseConnection(client);
    }

    @Override
    public void handleNewMessage(Message message, ChatClient source) {
        callback.handleNewMessage(message, source);
    }

    @Override
    public void handleSendMessage(Message message, ChatClient source) {
        callback.handleSendMessage(message, source);
    }
}
