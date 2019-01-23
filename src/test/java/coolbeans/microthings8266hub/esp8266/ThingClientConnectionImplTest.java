package coolbeans.microthings8266hub.esp8266;

import coolbeans.microthings8266hub.model.Action;
import coolbeans.microthings8266hub.model.Thing;
import coolbeans.microthings8266hub.service.ApplicationMessageService;
import coolbeans.microthings8266hub.service.ScriptRunner;
import coolbeans.microthings8266hub.service.SocketFactory;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.net.Socket;
import java.net.SocketTimeoutException;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class ThingClientConnectionImplTest {


    ThingClientConnectionImpl thingConnection;

    @Mock
    ApplicationMessageService messageService;

    @Mock
    SocketFactory socketFactory;


    @Mock
    Esp8266CommandExecutor esp8266CommandExecutor;

    @Mock
    Socket socket;

    @Mock
    ScriptRunner scriptRunner;

    Thing thing;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        thingConnection = new ThingClientConnectionImpl(messageService, socketFactory, esp8266CommandExecutor, scriptRunner);
        when(socketFactory.createSocket(anyString())).thenReturn(socket);
        when(socket.isConnected()).thenReturn(true);

        thing = new Thing();
        thing.setId(1L);
        thing.setIpAddress("192.168.2.1");
        thing.setName("TEST-NAME");

        Action action = new Action();
        action.setId(1L);
        action.setName("RET123");
        action.setScript("function run() { return 123; }");
        thing.addAction(action);

        /*
         The connect method will connect to socket and then perform an echo command using the
         thing name. This is mocked.
         */
        when(esp8266CommandExecutor.echo(anyString())).thenReturn(thing.getName());
    }

    @Test
    public void connect() throws IOException {

        thingConnection.connect(thing);
    }

    @Test
    public void close() throws IOException {
        thingConnection.connect(thing);
        thingConnection.close();
        verify(socket, times(1)).close();
    }

    @Test
    public void invokeAction() {
        when(scriptRunner.execute(anyString(), anyString())).thenReturn("123");
        thingConnection.connect(thing);
        Object response = thingConnection.invokeAction("RET123");
        assertNotNull(response);
        assertEquals("123", response);
    }

    @Test()
    public void isConnected() {
        assertFalse(thingConnection.isConnected());
        thingConnection.connect(thing);
        assertTrue(thingConnection.isConnected());
    }

    @Test
    public void isConnectedTimeout() throws IOException {
        thingConnection.connect(thing);
        when(esp8266CommandExecutor.echo(anyString())).thenThrow(new SocketTimeoutException());
        assertFalse(thingConnection.isConnected());
    }
}