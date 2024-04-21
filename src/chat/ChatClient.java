package chat;

import message.Message;
import yaff.YaffException;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;

import static message.YaffMessage.YAFF_MESSAGE;

public class ChatClient implements Runnable {
    private final Socket endpoint;
    private final OutputStream out;
    private final InputStream in;
    private final IChatCallback callback;
    private final Thread thread;

    public ChatClient(Socket endpoint, IChatCallback callback) throws IOException {
        this.callback = callback;
        this.endpoint = endpoint;
        this.thread = new Thread(this);
        this.out = endpoint.getOutputStream();
        this.in = endpoint.getInputStream();

        thread.start();
    }

    public ChatClient(InetSocketAddress address, IChatCallback callback) throws IOException {
        this(new Socket(address.getAddress(), address.getPort()), callback);
    }

    public void run() {
        try {
            while (!thread.isInterrupted() && !endpoint.isClosed()) {
                var message = (Message) YAFF_MESSAGE.deserialize(in);
                callback.handleNewMessage(message, this);
            }
        } catch (YaffException | SocketException e) {
            // Client could be sending invalid data or has disconnected (stopClient called)
            System.out.println("Client disconnected");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
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
        try {
            YAFF_MESSAGE.serialize(message, out);
        } catch (IOException | YaffException e) {
            e.printStackTrace();
        }
        callback.handleSendMessage(message, this);
    }
}
