package coolbeans.microthings8266hub.service;

import coolbeans.microthings8266hub.esp8266.Esp8266CommandExecutor;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
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
    public void scriptWithEcho() throws IOException {
        String ECHO_RESPONSE = "TEST123";
        when(esp8266CommandExecutor.echo(anyString())).thenReturn(ECHO_RESPONSE);
        Object response  = rhinoScriptRunner.execute("run(); function run() { return gpio.echo('abc')}; ",
                "simpleScript");
        assertEquals(ECHO_RESPONSE, response);
        verify(esp8266CommandExecutor).echo(anyString());
    }
}