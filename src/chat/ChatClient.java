package chat;

import message.Message;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;

public class ChatClient implements Runnable {
    private final Socket endpoint;
    private final PrintWriter writer;
    private final BufferedReader reader;
    private final IChatCallback callback;
    private final Thread thread;

    public ChatClient(Socket endpoint, IChatCallback callback) throws IOException {
        this.callback = callback;
        this.endpoint = endpoint;
        this.thread = new Thread(this);
        writer = new PrintWriter(this.endpoint.getOutputStream(), true);
        reader = new BufferedReader(new InputStreamReader(this.endpoint.getInputStream()));

        thread.start();
    }

    public ChatClient(InetSocketAddress address, IChatCallback callback) throws IOException {
        this(new Socket(address.getAddress(), address.getPort()), callback);
    }

    public void run() {
        try {
            while (!thread.isInterrupted()) {
                var content = reader.readLine();
                if (content == null) {
                    callback.handleCloseConnection(this);
                    stopClient();
                    break;
                }

                callback.handleNewMessage(new Message(content), this);
            }
        } catch (SocketException e) {
            // Expected result caused by stopClient()
            System.out.println("Closed socket");
        } catch (IOException e) {
            e.printStackTrace();
            callback.handleCloseConnection(this);
            stopClient();
        }
    }

    public void stopClient() {
        try {
            thread.interrupt();
            endpoint.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(Message message) {
        writer.println(message.getContent());
        callback.handleSendMessage(message, this);
    }
}
