package server;

import chat.ChatClient;
import chat.ChatServer;
import chat.IChatServerCallback;
import common.Util.Connection;
import message.Message;
import message.MessageType;
import message.YaffMessage;
import message.serializer.ContentType;
import message.serializer.ObjectSerializer;
import message.serializer.StringSerializer;
import yaff.Yaff;

import javax.swing.*;

public class Controller implements IChatServerCallback {
    private final View frame;
    private ChatServer chatServer;

    public Controller(boolean shouldStart) {
        frame = new View();
        frame.setVisible(true);
        frame.getComboBoxYaffFormat().setModel(new DefaultComboBoxModel<>(ContentType.values()));
        frame.getBtnServerStart().addActionListener(e -> startServer());
        frame.getBtnServerStop().addActionListener(e -> stopServer());

        // Automatically start the server
        if (shouldStart) startServer();
    }

    private void startServer() {
        try {
            // Inform Yaff that it can (de)serialize Message objects, using either String or ObjectSerializer
            Yaff yaff = new Yaff();
            if (frame.getComboBoxYaffFormat().getSelectedItem() == ContentType.Object) {
                yaff.registerSerializer(Message.class, new StringSerializer());
                yaff.registerSerializer(Message.class, new ObjectSerializer()); // The last entry takes precedence for serialization
            } else {
                yaff.registerSerializer(Message.class, new ObjectSerializer());
                yaff.registerSerializer(Message.class, new StringSerializer());
            }
            YaffMessage.YAFF_MESSAGE = yaff;

            var fieldAddr = frame.getTextFieldServerAddr();
            var fieldPort = frame.getTextFieldServerPort();
            var connectionTuple = new Connection(fieldAddr.getText(), fieldPort.getText());

            chatServer = new ChatServer(connectionTuple.toSocketAddress(), this);
            System.out.println("Starting server on " + connectionTuple);

            fieldAddr.setEnabled(false);
            fieldPort.setEnabled(false);
            frame.getBtnServerStart().setEnabled(false);
            frame.getBtnServerStop().setEnabled(true);
            frame.getComboBoxYaffFormat().setEnabled(false);

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
            frame.getComboBoxYaffFormat().setEnabled(true);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }

    @Override
    public void handleNewConnection(ChatClient client) {
        System.out.println("New connection from " + client);
        var message = new Message(MessageType.System, "New connection from " + client);
        frame.getLog().addMessage(message);
        chatServer.broadcastMessage(message, client);

        frame.getLbActiveClientsCount().setText(Integer.toString(chatServer.getClientCount()));
    }

    @Override
    public void handleCloseConnection(ChatClient client) {
        System.out.println("Connection closed from " + client);
        frame.getLog().addMessage(new Message(MessageType.System, "Connection closed from " + client));
        frame.getLbActiveClientsCount().setText(Integer.toString(chatServer.getClientCount()));
    }

    @Override
    public void handleNewMessage(Message message, ChatClient source) {
        System.out.println("New message from " + source + ": " + message);
        if (message.getType() != MessageType.User)
            return; // Basic check to prevent system messages from being broadcast from clients
        chatServer.broadcastMessage(message, source);
        frame.getLog().addMessage(message);
    }

    @Override
    public void handleSendMessage(Message message, ChatClient source) {
        // Do nothing
    }
}
