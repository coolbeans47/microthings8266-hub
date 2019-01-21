package coolbeans.microthings8266hub.esp8266;

import java.io.IOException;
import java.net.Socket;

public interface Esp8266CommandExecutor {

    String echo(Socket socket, String msg) throws IOException;
    void pinMode(Socket socket, int pinId, int pinMode) throws IOException;
    int digitalRead(Socket socket, int pinId) throws IOException;
    void digitalWrite(Socket socket, int pinId, int value) throws IOException;
}
