package coolbeans.microthings8266hub.service;

import coolbeans.microthings8266hub.esp8266.ThingClientConnection;
import coolbeans.microthings8266hub.model.Thing;
import coolbeans.microthings8266hub.model.ThingConnection;
import coolbeans.microthings8266hub.service.repositories.map.ThingMapService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class ThingManagerServiceImplTest {

    ThingManagerServiceImpl thingManagerService;
    ThingMapService mapService;

    @Mock
    ThingConnectionFactory connectionFactory;

    ThingClientConnection clientConnection;


    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        clientConnection = Mockito.mock(ThingClientConnection.class);
        when(connectionFactory.createConnection(any(Thing.class))).thenReturn(clientConnection);

        mapService = new ThingMapService();
        thingManagerService = new ThingManagerServiceImpl(mapService, connectionFactory);
        thingManagerService.addConnection(new ThingConnection("THING1", "192.168.4.1"));


    }

    @Test
    public void disonnectAll() {
        assertTrue(thingManagerService.isConnected(1L));
        thingManagerService.disonnectAll();
        assertFalse(thingManagerService.isConnected(0L));
    }

    @Test
    public void connectAll() {
        thingManagerService.addConnection(new ThingConnection("THING2", "192.168.4.2"));
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
        thingManagerService.addConnection(new ThingConnection("THING2", "192.168.4.2"));
        assertTrue(thingManagerService.isConnected(2L));

        List<Thing> things = mapService.findAll();
        assertEquals(2, things.size());
    }

}