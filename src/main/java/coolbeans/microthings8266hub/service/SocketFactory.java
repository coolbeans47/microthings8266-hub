package coolbeans.microthings8266hub.service;

import java.net.DatagramSocket;
import java.net.Socket;

public interface SocketFactory {
    Socket createSocket(String ipAddress);
    DatagramSocket createDatagramSocket();

}
