package coolbeans.microthings8266hub.esp8266;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

@Component
@Scope("prototype")
public class Esp8266CommandExecutorImpl implements Esp8266CommandExecutor{

    private Socket socket;

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    @Override
    public String echo(String msg) throws IOException {
        if (socket == null) {
            throw new IOException("socket property not set");
        }

        byte[] header = new byte[3];
        header[0] = Esp8266Commands.ECHO.getValue();
        header[1] = 0;
        header[2] = 0;
        writeHeader(header);
        socket.getOutputStream().write((msg + "\0").getBytes());
        socket.getOutputStream().flush();
        //read back response
        BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        String response = reader.readLine();
        return response;
    }

    @Override
    public void pinMode(int pinId, int pinMode) throws IOException {
        byte[] header = new byte[3];
        header[0] = Esp8266Commands.PINMODE.getValue();
        header[1] = (byte) pinId;
        header[2] = (byte) pinMode;
        writeHeader(header);
        socket.getOutputStream().flush();
    }

    @Override
    public int digitalRead(int pinId) throws IOException {
        byte[] header = new byte[3];
        header[0] = Esp8266Commands.DIGITAL_READ.getValue();
        header[1] = (byte) pinId;
        header[2] = (byte) 0;
        writeHeader(header);
        socket.getOutputStream().flush();
        int response = socket.getInputStream().read();
        return response;
    }

    @Override
    public void digitalWrite(int pinId, int value) throws IOException {
        byte[] header = new byte[3];
        header[0] = Esp8266Commands.DIGITAL_WRITE.getValue();
        header[1] = (byte) pinId;
        header[2] = (byte) value;
        writeHeader(header);
        socket.getOutputStream().flush();
    }


    private void writeHeader(byte[] header) throws IOException {
        socket.getOutputStream().write(header);
    }
}
