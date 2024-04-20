package common;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.SocketException;

public class SocketListener implements Runnable {
    private final ServerSocket serverSocket;
    private final ISocketListenerCallback callback;
    private final Thread thread;

    public SocketListener(InetSocketAddress address, ISocketListenerCallback callback) throws IOException {
        this.callback = callback;
        this.thread = new Thread(this);
        serverSocket = new ServerSocket();
        serverSocket.bind(address);
    }

    public void start() {
        thread.start();
    }

    public void run() {
        try {
            while (!thread.isInterrupted()) callback.handleNewSocket(serverSocket.accept());
        } catch (SocketException e) {
            // Expected result caused by close()
            System.out.println("Force stopped socket accept method");
        } catch (IOException e) {
            thread.interrupt();
            e.printStackTrace();
        }
    }

    public void stopServer() throws IOException {
        thread.interrupt();
        serverSocket.close();
    }

    public boolean isRunning() {
        return thread != null && thread.isAlive();
    }
}
