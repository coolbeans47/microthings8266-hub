package coolbeans.microthings8266hub.service;

import coolbeans.microthings8266hub.esp8266.ThingClientConnection;
import coolbeans.microthings8266hub.model.Thing;
import coolbeans.microthings8266hub.model.ThingConnectionRequest;
import coolbeans.microthings8266hub.service.repositories.ActionService;
import coolbeans.microthings8266hub.service.repositories.PinService;
import coolbeans.microthings8266hub.service.repositories.ThingService;
import coolbeans.microthings8266hub.service.repositories.map.ThingMapService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.context.ApplicationContext;

import java.io.IOException;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class ThingManagerServiceImplTest {

    ThingManagerServiceImpl thingManagerService;
    ThingService mapService;

    @Mock
    ApplicationContext context;

    @Mock
    PinService pinService;

    @Mock
    ActionService actionService;

    ThingClientConnection clientConnection;


    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        clientConnection = Mockito.mock(ThingClientConnection.class);
        when(context.getBean(any(String.class))).thenReturn(clientConnection);

        mapService = new ThingMapService(pinService, actionService);
        thingManagerService = new ThingManagerServiceImpl(mapService, context);
        thingManagerService.addConnection(new ThingConnectionRequest("THING1", "192.168.4.1"));

    }

    @Test
    public void disonnectAll() {
        assertTrue(thingManagerService.isConnected(1L));
        thingManagerService.disonnectAll();
        assertFalse(thingManagerService.isConnected(0L));
    }

    @Test
    public void connectAll() {
        thingManagerService.addConnection(new ThingConnectionRequest("THING2", "192.168.4.2"));
        thingManagerService.disconnect(2L);
        thingManagerService.connectAll();
        assertEquals(2, thingManagerService.getConnectedCount());
    }

    @Test
    public void connect() throws IOException {
        thingManagerService.disconnect(1L);
        assertFalse(thingManagerService.isConnected(1L));
        thingManagerService.connect(1L);
        assertTrue(thingManagerService.isConnected(1L));
    }

    @Test(expected = IOException.class)
    public void connectWithInvalidID() throws IOException {
        thingManagerService.connect(123L);
    }


    @Test
    public void disconnect() {
        thingManagerService.disconnect(1L);
        assertFalse(thingManagerService.isConnected(1L));
    }


    @Test
    public void isConnected() {
        assertTrue(thingManagerService.isConnected(1L));
    }

    @Test
    public void addConnection() {
        thingManagerService.addConnection(new ThingConnectionRequest("THING2", "192.168.4.2"));
        assertTrue(thingManagerService.isConnected(2L));

        List<Thing> things = mapService.findAll();
        assertEquals(2, things.size());
    }

    @Test
    public void addConnectionWithExistngNameAndIpAddress() {
        thingManagerService.addConnection(new ThingConnectionRequest("THING1", "192.168.4.1"));
        //Should not update
        assertTrue(thingManagerService.isConnected(1L));
    }

    @Test
    public void addConnectionWithExistngNameAndDiffrentIp() {
        thingManagerService.addConnection(new ThingConnectionRequest("THING1", "192.168.4.2"));
        //Should not update
        assertTrue(thingManagerService.isConnected(1L));

        Thing thing = mapService.findById(1L);
        assertNotNull(thing);
        assertEquals("192.168.4.2", thing.getIpAddress());
    }

    @Test(expected = IOException.class)
    public void connectWhenAlreadyRunning() throws IOException {
        thingManagerService.connect(1L);
    }

}