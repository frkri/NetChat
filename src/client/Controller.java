package client;

import chat.ChatClient;
import chat.IChatCallback;
import common.Util.Connection;
import message.Log;
import message.Message;
import message.YaffMessage;
import message.serializer.ContentType;
import message.serializer.ObjectSerializer;
import message.serializer.StringSerializer;
import yaff.Yaff;

import javax.swing.*;
import java.io.IOException;

public class Controller implements IChatCallback {
    private final View frame;
    private final Log chatLog;
    private ChatClient chatClient;

    public Controller(boolean shouldStart) {
        this.frame = new View();
        this.chatLog = frame.getLog();
        frame.setVisible(true);
        frame.getComboBoxYaffFormat().setModel(new DefaultComboBoxModel<>(ContentType.values()));
        frame.getBtnConnectionStart().addActionListener(e -> startClient());
        frame.getBtnConnectionStop().addActionListener(e -> stopClient());
        frame.getBtnMessageSend().addActionListener(e -> {
            var text = frame.getTextFieldMessage().getText();
            if (text.isBlank()) return;
            chatClient.sendMessage(new Message(text));
        });

        // Automatically start the client
        if (shouldStart) startClient();
    }

    private void startClient() {
        try {
            Yaff yaff = new Yaff();
            if (frame.getComboBoxYaffFormat().getSelectedItem() == ContentType.Object) {
                yaff.registerSerializer(Message.class, new StringSerializer());
                yaff.registerSerializer(Message.class, new ObjectSerializer());
            } else {
                yaff.registerSerializer(Message.class, new ObjectSerializer());
                yaff.registerSerializer(Message.class, new StringSerializer());
            }
            YaffMessage.YAFF_MESSAGE = yaff;

            var fieldAddr = frame.getTextFieldServerAddr();
            var fieldPort = frame.getTextFieldServerPort();
            var connectionTuple = new Connection(fieldAddr.getText(), fieldPort.getText());

            chatClient = new ChatClient(connectionTuple.toSocketAddress(), this);
            System.out.println("Connected to server on " + connectionTuple);

            fieldAddr.setEnabled(false);
            fieldPort.setEnabled(false);
            frame.getBtnConnectionStart().setEnabled(false);
            frame.getBtnConnectionStop().setEnabled(true);
            frame.getBtnMessageSend().setEnabled(true);
            frame.getComboBoxYaffFormat().setEnabled(false);
        } catch (IOException e1) {
            System.err.println("Failed to connect to provided address");
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }

    private void stopClient() {
        chatClient.stopClient();
        frame.getTextFieldServerAddr().setEnabled(true);
        frame.getTextFieldServerPort().setEnabled(true);
        frame.getBtnConnectionStart().setEnabled(true);
        frame.getBtnConnectionStop().setEnabled(false);
        frame.getBtnMessageSend().setEnabled(false);
        frame.getComboBoxYaffFormat().setEnabled(true);
    }

    @Override
    public void handleCloseConnection(ChatClient client) {
        frame.getTextFieldServerAddr().setEnabled(true);
        frame.getTextFieldServerPort().setEnabled(true);
        frame.getBtnConnectionStart().setEnabled(true);
        frame.getBtnConnectionStop().setEnabled(false);
        frame.getBtnMessageSend().setEnabled(false);
    }

    @Override
    public void handleNewMessage(Message message, ChatClient source) {
        chatLog.addMessage(message);
    }

    @Override
    public void handleSendMessage(Message message, ChatClient source) {
        chatLog.addMessage(message);
    }
}
