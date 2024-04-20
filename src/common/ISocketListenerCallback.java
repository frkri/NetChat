package common;

import java.net.Socket;

public interface ISocketListenerCallback {
    void handleNewSocket(Socket clientEndpoint);
}
