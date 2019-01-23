package coolbeans.microthings8266hub.esp8266;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.Socket;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;


public class Esp8266CommandExecutorImplTest {

    Esp8266CommandExecutorImpl commandExecutor;

    @Mock
    Socket socket;

    ByteArrayOutputStream outputStream;

    @Before
    public void setUp() throws IOException {
        MockitoAnnotations.initMocks(this);
        commandExecutor = new Esp8266CommandExecutorImpl();
        commandExecutor.setSocket(socket);
        outputStream = new ByteArrayOutputStream();
        when(socket.getOutputStream()).thenReturn(outputStream);
    }

    @Test
    public void echoCommand() throws IOException {
        String msg = "TEST";
        //inputStream = new TestInputStream(msg);
        ByteArrayInputStream is = new ByteArrayInputStream((msg + "\n").getBytes());

        when(socket.getInputStream()).thenReturn(is);
        String response = commandExecutor.echo(msg);
        assertEquals(msg, response);
        assertEquals("\0\0\0" + msg + "\0", outputStream.toString());
    }

    @Test
    public void pinModeCommand() throws IOException {
        byte pin = 14;
        byte mode = 1;
        commandExecutor.pinMode(pin, mode);
        byte[] expectedOutput = new byte[] {Esp8266Commands.PINMODE.getValue(), pin, mode};
        assertArrayEquals(expectedOutput, outputStream.toByteArray());
    }


    @Test
    public void digitalWriteCommand() throws IOException {
        byte pin = 12;
        byte mode = 1;
        commandExecutor.digitalWrite(pin, mode);
        byte[] expectedOutput = new byte[] {Esp8266Commands.DIGITAL_WRITE.getValue(), pin, mode};
        assertArrayEquals(expectedOutput, outputStream.toByteArray());
    }

    @Test
    public void digitalReadCommand() throws IOException {
        byte pin = 12;
        byte state = 1;
        byte[] buf = new byte[] {state};
        ByteArrayInputStream is = new ByteArrayInputStream(buf);
        when(socket.getInputStream()).thenReturn(is);
        int response = commandExecutor.digitalRead(pin);
        assertEquals(state, response);
        byte[] expectedOutput = new byte[] {Esp8266Commands.DIGITAL_READ.getValue(), pin, 0};
        assertArrayEquals(expectedOutput, outputStream.toByteArray());
    }

}