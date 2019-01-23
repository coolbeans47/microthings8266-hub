package coolbeans.microthings8266hub.service;

import coolbeans.microthings8266hub.esp8266.Esp8266CommandExecutor;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class RhinoScriptRunnerTest {

    @Mock
    Esp8266CommandExecutor esp8266CommandExecutor;

    RhinoScriptRunner rhinoScriptRunner;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        rhinoScriptRunner = new RhinoScriptRunner(esp8266CommandExecutor);
    }

    @Test
    public void simpleScript() {
        Object response  = rhinoScriptRunner.execute("run(); function run() { return 'Hello'}; ",
                "simpleScript");
        assertEquals("Hello", response);
    }

    @Test
    public void scriptCallingEcho() throws IOException {
        String ECHO_RESPONSE = "TEST123";
        when(esp8266CommandExecutor.echo(anyString())).thenReturn(ECHO_RESPONSE);
        Object response  = rhinoScriptRunner.execute("run(); function run() { return gpio.echo('abc')}; ",
                "simpleScript");
        assertEquals(String.class, response.getClass());
        assertEquals(ECHO_RESPONSE, response);
        verify(esp8266CommandExecutor).echo(anyString());
    }

    @Test
    public void scriptCallingPinMode() throws IOException {
        Object response  = rhinoScriptRunner.execute("run(); function run() { return gpio.pinMode(14, 1)}; ",
                "simpleScript");
        verify(esp8266CommandExecutor).pinMode(14, 1);
    }

    @Test
    public void scriptCallingDigitalWrite() throws IOException {
        Object response  = rhinoScriptRunner.execute("run(); function run() { gpio.digitalWrite(12, 0)}; ",
                "simpleScript");
        verify(esp8266CommandExecutor).digitalWrite(12, 0);
    }

    @Test
    public void scriptCallingDigitalRead() throws IOException {
        Integer READ_RESPONSE = 123;
        when(esp8266CommandExecutor.digitalRead(anyInt())).thenReturn(READ_RESPONSE);
        Object response  = rhinoScriptRunner.execute("run(); function run() { return gpio.digitalRead(12)}; ",
                "simpleScript");
        assertEquals(Integer.class, response.getClass());
        assertEquals(READ_RESPONSE, response);
        verify(esp8266CommandExecutor).digitalRead(12);
    }
}