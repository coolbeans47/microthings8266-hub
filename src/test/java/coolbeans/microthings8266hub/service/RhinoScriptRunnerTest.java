package coolbeans.microthings8266hub.service;

import coolbeans.microthings8266hub.esp8266.Esp8266CommandExecutor;
import coolbeans.microthings8266hub.service.script.RhinoScriptRunner;
import coolbeans.microthings8266hub.service.script.ScriptContext;
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

    ScriptContext context;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        rhinoScriptRunner = new RhinoScriptRunner();
        context = new ScriptContext();
        context.getContext().put("gpio", esp8266CommandExecutor);
        context.getContext().put("STR1", "STRVAL1");
        context.getContext().put("NUM1", 123);
        rhinoScriptRunner.setContext(context);
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

    @Test
    public void scriptUsingStringContextValue() throws IOException {
        Object response  = rhinoScriptRunner.execute("run(); function run() { return STR1}; ",
                "simpleScript");
        assertEquals(String.class, response.getClass());
        assertEquals("STRVAL1", response);
    }

    @Test
    public void scriptUsingIntegerContextValue() throws IOException {
        Object response  = rhinoScriptRunner.execute("run(); function run() { return NUM1 }; ",
                "simpleScript");
        assertEquals(Integer.class, response.getClass());
        assertEquals(123, response);
    }


    @Test
    public void scriptUsingCalcResult() throws IOException {
        Object response  = rhinoScriptRunner.execute("run(); function run() { return NUM1 + 2 }; ",
                "simpleScript");

        assertEquals(Double.class, response.getClass());
        assertEquals(125.0, response);
    }
}