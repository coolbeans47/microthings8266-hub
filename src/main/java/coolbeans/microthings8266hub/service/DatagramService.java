package coolbeans.microthings8266hub.service;

import java.io.IOException;
import java.net.DatagramPacket;

public interface DatagramService {

    String receiveString() throws IOException;
    DatagramPacket getPacket();
}
