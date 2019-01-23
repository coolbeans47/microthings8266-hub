package coolbeans.microthings8266hub.esp8266;

import java.io.IOException;
import java.net.Socket;

public interface Esp8266CommandExecutor {

    String echo(String msg) throws IOException;
    void pinMode(int pinId, int pinMode) throws IOException;
    int digitalRead(int pinId) throws IOException;
    void digitalWrite(int pinId, int value) throws IOException;
    Socket getSocket();
    void setSocket(Socket socket);
}
