package client;

import message.Log;
import message.serializer.ContentType;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.Serial;

public class View extends JFrame {

    @Serial
    private static final long serialVersionUID = 1L;
    private Log chatLog;
    private JTextField textFieldServerPort;
    private JLabel lbMessages;
    private JTextField textFieldServerAddr;
    private JButton btnConnectionStart;
    private JPanel ChatLog;
    private JPanel ServerControl;
    private JButton btnConnectionStop;
    private JTextField textFieldMessage;
    private JButton btnMessageSend;
    private JPanel MessageSender;
    private JScrollPane scrollPane;
    private JComboBox<ContentType> comboBoxYaffFormat;

    public View() {
        initialize();
    }

    private void initialize() {
        this.setTitle("Client");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 300);
        JPanel contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        setContentPane(contentPane);
        contentPane.setLayout(null);
        contentPane.add(getServerControl());
        contentPane.add(getChatLog());
        contentPane.add(getMessageSender());
        contentPane.add(getComboBoxYaffFormat());
    }

    protected Log getLog() {
        if (chatLog == null) {
            chatLog = new Log();
        }
        return chatLog;
    }

    protected JTextField getTextFieldServerPort() {
        if (textFieldServerPort == null) {
            textFieldServerPort = new JTextField();
            textFieldServerPort.setHorizontalAlignment(SwingConstants.CENTER);
            textFieldServerPort.setBounds(76, 0, 52, 20);
            textFieldServerPort.setText("25566");
            textFieldServerPort.setColumns(10);
        }
        return textFieldServerPort;
    }

    private JLabel getLbMessages() {
        if (lbMessages == null) {
            lbMessages = new JLabel("Messages");
            lbMessages.setBounds(0, 0, 255, 19);
            lbMessages.setFont(new Font("Tahoma", Font.BOLD, 14));
        }
        return lbMessages;
    }

    protected JTextField getTextFieldServerAddr() {
        if (textFieldServerAddr == null) {
            textFieldServerAddr = new JTextField();
            textFieldServerAddr.setHorizontalAlignment(SwingConstants.CENTER);
            textFieldServerAddr.setBounds(0, 0, 78, 20);
            textFieldServerAddr.setText("127.0.0.1");
            textFieldServerAddr.setColumns(10);
        }
        return textFieldServerAddr;
    }

    protected JButton getBtnConnectionStart() {
        if (btnConnectionStart == null) {
            btnConnectionStart = new JButton("Connect");
            btnConnectionStart.setBounds(0, 31, 128, 23);
        }
        return btnConnectionStart;
    }

    private JPanel getChatLog() {
        if (ChatLog == null) {
            ChatLog = new JPanel();
            ChatLog.setBounds(10, 11, 255, 239);
            ChatLog.setLayout(null);
            ChatLog.add(getScrollPane());
            ChatLog.add(getLbMessages());
        }
        return ChatLog;
    }

    private JPanel getServerControl() {
        if (ServerControl == null) {
            ServerControl = new JPanel();
            ServerControl.setBounds(285, 41, 128, 89);
            ServerControl.setLayout(null);
            ServerControl.add(getTextFieldServerPort());
            ServerControl.add(getTextFieldServerAddr());
            ServerControl.add(getBtnConnectionStop());
            ServerControl.add(getBtnConnectionStart());
        }
        return ServerControl;
    }

    protected JButton getBtnConnectionStop() {
        if (btnConnectionStop == null) {
            btnConnectionStop = new JButton("Disconnect");
            btnConnectionStop.setEnabled(false);
            btnConnectionStop.setBounds(0, 65, 128, 23);
        }
        return btnConnectionStop;
    }

    protected JTextField getTextFieldMessage() {
        if (textFieldMessage == null) {
            textFieldMessage = new JTextField();
            textFieldMessage.setBounds(0, 0, 128, 20);
            textFieldMessage.setColumns(10);
        }
        return textFieldMessage;
    }

    protected JButton getBtnMessageSend() {
        if (btnMessageSend == null) {
            btnMessageSend = new JButton("Send");
            btnMessageSend.setBounds(0, 31, 128, 23);
        }
        return btnMessageSend;
    }

    private JPanel getMessageSender() {
        if (MessageSender == null) {
            MessageSender = new JPanel();
            MessageSender.setBounds(285, 196, 128, 54);
            MessageSender.setLayout(null);
            MessageSender.add(getTextFieldMessage());
            MessageSender.add(getBtnMessageSend());
        }
        return MessageSender;
    }

    private JScrollPane getScrollPane() {
        if (scrollPane == null) {
            scrollPane = new JScrollPane();
            scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
            scrollPane.setBounds(0, 30, 255, 209);
            scrollPane.setViewportView(getLog());
        }
        return scrollPane;
    }

    protected JComboBox<ContentType> getComboBoxYaffFormat() {
        if (comboBoxYaffFormat == null) {
            comboBoxYaffFormat = new JComboBox<>();
            comboBoxYaffFormat.setBounds(285, 152, 128, 22);
        }
        return comboBoxYaffFormat;
    }
}
