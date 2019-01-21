package coolbeans.microthings8266hub.esp8266;

import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

@Component
public class Esp8266CommandExecutorImpl implements Esp8266CommandExecutor{

    @Override
    public String echo(Socket socket, String msg) throws IOException {
        byte[] header = new byte[3];
        header[0] = Esp8266Commands.ECHO.getValue();
        header[1] = 0;
        header[2] = 0;
        writeHeader(socket, header);
        socket.getOutputStream().write((msg + "\0").getBytes());
        socket.getOutputStream().flush();
        //read back response
        BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        String response = reader.readLine();
        return response;

    }

    @Override
    public void pinMode(Socket socket, int pinId, int pinMode) throws IOException {

    }

    @Override
    public int digitalRead(Socket socket, int pinId) throws IOException {
        return 0;
    }

    @Override
    public void digitalWrite(Socket socket, int pinId, int value) throws IOException {

    }


    private void writeHeader(Socket socket, byte[] header) throws IOException {
        socket.getOutputStream().write(header);
    }
}
