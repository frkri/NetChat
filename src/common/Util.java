package common;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;

public class Util {

    public record Connection(InetAddress address, int port) {
        public Connection(String addr, String port) throws NumberFormatException, UnknownHostException {
            this(InetAddress.getByName(addr), Integer.parseInt(port));
        }

        public InetSocketAddress toSocketAddress() {
            return new InetSocketAddress(address, port);
        }

        @Override
        public String toString() {
            return this.address.getHostAddress() + ":" + this.port;
        }
    }
}
