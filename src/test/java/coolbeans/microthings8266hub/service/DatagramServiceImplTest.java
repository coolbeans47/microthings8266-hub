package coolbeans.microthings8266hub.service;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketException;

import static org.junit.Assert.assertEquals;



class MockDatagramSocket extends DatagramSocket {

    byte[] data;
    String ipAddress;
    int port;

    public MockDatagramSocket(int port) throws SocketException {
        super(port);
        this.port = port;
    }

    @Override
    public synchronized void receive(DatagramPacket p) throws IOException {
        p.setData(data);
        p.setSocketAddress(new InetSocketAddress(ipAddress, port));
    }
}

public class DatagramServiceImplTest {
    static final int PORT = 1234;
    static final String IP_ADDRESS = "127.0.0.1";

    MockDatagramSocket socket;
    DatagramServiceImpl datagramService;

    @Before
    public void setup() throws SocketException {
        socket = new MockDatagramSocket(PORT);
        socket.ipAddress = IP_ADDRESS;
        datagramService = new DatagramServiceImpl(socket);
    }

    @After
    public void cleanup() {
        socket.close();
    }

    @Test
    public void receiveStringWithNullTerminatedString() throws IOException {
        String name = "DEVICE_1111_222_333";
        socket.data = (name + "\0").getBytes();

        String result = datagramService.receiveString();
        assertEquals(name, result);
    }

    @Test
    public void receiveStringWithTwoNullTerminatedString() throws IOException {
        String name = "DEVICE_1111_222_333";
        socket.data = (name + "\0SHOULDNOTSEE\0").getBytes();

        String result = datagramService.receiveString();
        assertEquals(name, result);
    }

}