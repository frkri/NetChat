package server;

import chat.ChatClient;
import chat.ChatServer;
import chat.IChatServerCallback;
import common.Util.Connection;
import message.Message;
import message.SystemMessage;

public class Controller implements IChatServerCallback {
    private final View frame;
    private ChatServer chatServer;

    public Controller(boolean shouldStart) {
        frame = new View();
        frame.setVisible(true);
        frame.getBtnServerStart().addActionListener(e -> startServer());
        frame.getBtnServerStop().addActionListener(e -> stopServer());

        // Automatically start the server
        if (shouldStart) startServer();
    }

    private void startServer() {
        try {
            var fieldAddr = frame.getTextFieldServerAddr();
            var fieldPort = frame.getTextFieldServerPort();
            var connectionTuple = new Connection(fieldAddr.getText(), fieldPort.getText());

            chatServer = new ChatServer(connectionTuple.toSocketAddress(), this);
            System.out.println("Starting server on " + connectionTuple);

            fieldAddr.setEnabled(false);
            fieldPort.setEnabled(false);
            frame.getBtnServerStart().setEnabled(false);
            frame.getBtnServerStop().setEnabled(true);

            chatServer.serve();
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }

    private void stopServer() {
        try {
            chatServer.stop();

            frame.getTextFieldServerAddr().setEnabled(true);
            frame.getTextFieldServerPort().setEnabled(true);
            frame.getBtnServerStart().setEnabled(true);
            frame.getBtnServerStop().setEnabled(false);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }

    @Override
    public void handleNewConnection(ChatClient client) {
        System.out.println("New connection from " + client);
        frame.getLog().addMessage(new SystemMessage("New connection from " + client));

        frame.getLbActiveClientsCount().setText(Integer.toString(chatServer.getClientCount()));
    }

    @Override
    public void handleCloseConnection(ChatClient client) {
        System.out.println("Connection closed from " + client);
        frame.getLog().addMessage(new SystemMessage("Connection closed from " + client));
        frame.getLbActiveClientsCount().setText(Integer.toString(chatServer.getClientCount()));
    }

    @Override
    public void handleNewMessage(Message message, ChatClient source) {
        chatServer.broadcastMessage(message, source);
        frame.getLog().addMessage(message);
    }

    @Override
    public void handleSendMessage(Message message, ChatClient source) {
    }
}
