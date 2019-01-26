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
import static org.mockito.Mockito.*;

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
        assertTrue(thingManagerService.isConnected("THING1"));
        thingManagerService.disonnectAll();
        assertFalse(thingManagerService.isConnected("THING1"));
    }

    @Test
    public void connectAll() {
        thingManagerService.addConnection(new ThingConnectionRequest("THING2", "192.168.4.2"));
        thingManagerService.disconnect("THING2");
        thingManagerService.connectAll();
        assertEquals(2, thingManagerService.getConnectedCount());
    }

    @Test
    public void connect() throws IOException {
        thingManagerService.disconnect("THING1");
        assertFalse(thingManagerService.isConnected("THING1"));
        thingManagerService.connect("THING1");
        assertTrue(thingManagerService.isConnected("THING1"));
    }

    @Test(expected = IOException.class)
    public void connectWithInvalidID() throws IOException {
        thingManagerService.connect("THING123");
    }


    @Test
    public void disconnect() {
        thingManagerService.disconnect("THING1");
        assertFalse(thingManagerService.isConnected("THING1"));
    }


    @Test
    public void isConnected() {
        assertTrue(thingManagerService.isConnected("THING1"));
    }

    @Test
    public void addConnection() {
        thingManagerService.addConnection(new ThingConnectionRequest("THING2", "192.168.4.2"));
        assertTrue(thingManagerService.isConnected("THING2"));

        List<Thing> things = mapService.findAll();
        assertEquals(2, things.size());
    }

    @Test
    public void addConnectionWithExistngNameAndIpAddressAndConnected() {
        when(clientConnection.getThing()).thenReturn(mapService.findByDeviceId("THING1"));
        when(clientConnection.isConnected()).thenReturn(true);
        thingManagerService.addConnection(new ThingConnectionRequest("THING1", "192.168.4.1"));
        //Should not update
        assertTrue(thingManagerService.isConnected("THING1"));
        verify(clientConnection).connect(any(Thing.class));
    }

    @Test
    public void addConnectionWithExistngNameAndIpAddressAndNotConnected() {
        when(clientConnection.getThing()).thenReturn(mapService.findByDeviceId("THING1"));
        when(clientConnection.isConnected()).thenReturn(false);
        thingManagerService.addConnection(new ThingConnectionRequest("THING1", "192.168.4.1"));
        //Should not update
        assertTrue(thingManagerService.isConnected("THING1"));
        //If the device had disconnected then discconnect will be called 2 times, the second time the reconnection
        verify(clientConnection, times(2)).connect(any(Thing.class));
    }

    @Test
    public void addConnectionWithExistngNameAndDiffrentIp() {
        Thing existing = new Thing();
        existing.setIpAddress("192.168.4.1");
        existing.setId(1L);
        existing.setName("THING1");
        when(clientConnection.getThing()).thenReturn(existing);
        thingManagerService.addConnection(new ThingConnectionRequest("THING1", "192.168.4.3"));
        assertTrue(thingManagerService.isConnected("THING1"));

        Thing thing = mapService.findById(1L);
        assertNotNull(thing);
        assertEquals("192.168.4.3", thing.getIpAddress());
    }

    @Test(expected = IOException.class)
    public void connectWhenAlreadyRunning() throws IOException {
        thingManagerService.connect("THING1");
    }

}