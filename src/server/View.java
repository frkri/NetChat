package server;

import message.Log;

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
    private JButton btnServerStart;
    private JLabel lbActiveClients;
    private JPanel ChatLog;
    private JPanel ServerControl;
    private JButton btnServerStop;
    private JPanel ServerClients;
    private JLabel lbActiveClientsCount;
    private JScrollPane scrollPane;

    public View() {
        initialize();
    }

    private void initialize() {
        this.setTitle("Server");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 600, 300);
        JPanel contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        setContentPane(contentPane);
        contentPane.setLayout(null);
        contentPane.add(getServerControl());
        contentPane.add(getChatLog());
        contentPane.add(getServerClients());
    }

    protected Log getLog() {
        if (chatLog == null) {
            chatLog = new Log();
            chatLog.setVisibleRowCount(8);
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
            textFieldServerAddr.setBounds(0, 0, 77, 20);
            textFieldServerAddr.setText("127.0.0.1");
            textFieldServerAddr.setColumns(10);
        }
        return textFieldServerAddr;
    }

    protected JButton getBtnServerStart() {
        if (btnServerStart == null) {
            btnServerStart = new JButton("Start");
            btnServerStart.setBounds(0, 31, 128, 23);
        }
        return btnServerStart;
    }

    private JLabel getLbActiveClients() {
        if (lbActiveClients == null) {
            lbActiveClients = new JLabel("Active Clients");
            lbActiveClients.setBounds(21, 0, 110, 14);
            lbActiveClients.setHorizontalAlignment(SwingConstants.CENTER);
        }
        return lbActiveClients;
    }

    private JPanel getChatLog() {
        if (ChatLog == null) {
            ChatLog = new JPanel();
            ChatLog.setBounds(10, 11, 394, 239);
            ChatLog.setLayout(null);
            ChatLog.add(getScrollPane());
            ChatLog.add(getLbMessages());
        }
        return ChatLog;
    }

    private JPanel getServerControl() {
        if (ServerControl == null) {
            ServerControl = new JPanel();
            ServerControl.setBounds(424, 41, 128, 89);
            ServerControl.setLayout(null);
            ServerControl.add(getTextFieldServerPort());
            ServerControl.add(getTextFieldServerAddr());
            ServerControl.add(getBtnServerStop());
            ServerControl.add(getBtnServerStart());
        }
        return ServerControl;
    }

    protected JButton getBtnServerStop() {
        if (btnServerStop == null) {
            btnServerStop = new JButton("Stop");
            btnServerStop.setEnabled(false);
            btnServerStop.setBounds(0, 65, 128, 23);
        }
        return btnServerStop;
    }

    private JPanel getServerClients() {
        if (ServerClients == null) {
            ServerClients = new JPanel();
            ServerClients.setBounds(414, 160, 149, 90);
            ServerClients.setLayout(null);
            ServerClients.add(getLbActiveClients());
            ServerClients.add(getLbActiveClientsCount());
        }
        return ServerClients;
    }

    protected JLabel getLbActiveClientsCount() {
        if (lbActiveClientsCount == null) {
            lbActiveClientsCount = new JLabel("0");
            lbActiveClientsCount.setFont(new Font("Tahoma", Font.PLAIN, 30));
            lbActiveClientsCount.setHorizontalAlignment(SwingConstants.CENTER);
            lbActiveClientsCount.setBounds(10, 37, 129, 42);
        }
        return lbActiveClientsCount;
    }

    private JScrollPane getScrollPane() {
        if (scrollPane == null) {
            scrollPane = new JScrollPane();
            scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
            scrollPane.setBounds(0, 30, 390, 209);
            scrollPane.setViewportView(getLog());
        }
        return scrollPane;
    }
}
