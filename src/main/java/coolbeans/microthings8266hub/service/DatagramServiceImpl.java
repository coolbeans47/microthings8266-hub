package coolbeans.microthings8266hub.service;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

@Service
public class DatagramServiceImpl implements DatagramService {

    private static final int MAX_NAME_SIZE = 100;

    private final SocketFactory socketFactory;
    private final DatagramSocket socket;
    private DatagramPacket packet;
    private byte[] buffer;

    public DatagramServiceImpl(SocketFactory socketFactory) {
        this.socketFactory = socketFactory;
        this.socket = socketFactory.createDatagramSocket();
        buffer = new byte[MAX_NAME_SIZE];
    }


    @Override
    public String receiveString() throws IOException {
        packet = new DatagramPacket(buffer, buffer.length);
        socket.receive(packet);
        return getBufferAsString();
    }

    private String getBufferAsString() {
        StringBuffer str = new StringBuffer(MAX_NAME_SIZE);
        str.append(new String(packet.getData()));
        int pos = str.indexOf("\0");
        if (pos > 0) {
            str.setLength(pos);
        }

        return str.toString();
    }

    public DatagramPacket getPacket() {
        return packet;
    }

    @Override
    public int getPort() {
        return socket.getLocalPort();
    }
}
