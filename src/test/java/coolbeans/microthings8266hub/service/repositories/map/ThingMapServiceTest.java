package coolbeans.microthings8266hub.service.repositories.map;

import coolbeans.microthings8266hub.model.Pin;
import coolbeans.microthings8266hub.model.PinMode;
import coolbeans.microthings8266hub.model.Thing;
import coolbeans.microthings8266hub.service.repositories.*;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ThingMapServiceTest {

    ThingService thingService;

    PinService pinService;
    ActionService actionService;
    TriggerService triggerService;
    TriggerActionService triggerActionService;

    @Before
    public void setUp() throws Exception {
        pinService = new PinMapService();
        triggerActionService = new TriggerActionMapService();
        triggerService = new TriggerMapService(triggerActionService);
        thingService = new ThingMapService(pinService, actionService);
        actionService = new ActionMapService(triggerService);

        Thing t1 = new Thing();
        t1.setId(1L);
        t1.setIpAddress("192.168.1.1");
        t1.setDeviceId("THING1");
        thingService.save(t1);

        Thing t2 = new Thing();
        t2.setId(2L);
        t2.setIpAddress("192.168.1.2");
        t2.setDeviceId("THING2");
        thingService.save(t2);
    }

    @Test
    public void findAll() {
        assertEquals(2, thingService.findAll().size());
    }

    @Test
    public void findById() {
        Thing thing = thingService.findById(2L);
        assertNotNull(thing);
        assertEquals(2L, (long) thing.getId());
    }

    @Test
    public void findByName() {
        Thing found = thingService.findByDeviceId("THING2");
        assertNotNull(found);
        assertEquals("THING2", found.getDeviceId());
    }

    @Test
    public void findByInvalidName() {
        Thing found = thingService.findByDeviceId("THINGNOTFOUND");
        assertNull(found);
    }

    @Test
    public void save() {
        Thing thing = new Thing();
        thing.setId(3L);
        thing.setIpAddress("192.168.1.3");
        thing.setDeviceId("THING3");
        Thing saved = thingService.save(thing);
        assertEquals(thing, saved);
    }

    @Test
    public void deleteById() {
        thingService.deleteById(1L);
        assertEquals(1, thingService.findAll().size());
    }

    @Test
    public void saveWithoutId() {
        Thing thing = new Thing();
        thing.setIpAddress("192.168.1.3");
        thing.setDeviceId("THING3");

        Thing saved = thingService.save(thing);
        assertEquals(3L, (long) saved.getId());

        Thing found = thingService.findById(3L);
        assertNotNull(found);
        assertEquals("THING3", found.getDeviceId());
    }



    @Test
    public void saveWithoutIdAndEmptyList() {
        thingService = new ThingMapService(pinService, actionService);
        Thing thing = new Thing();
        thing.setIpAddress("192.168.1.3");
        thing.setDeviceId("THING1");

        Thing saved = thingService.save(thing);
        assertEquals(1L, (long) saved.getId());
   }

   @Test
    public void savingWithPins() {
       Thing thing = new Thing();
       thing.setIpAddress("192.168.1.5");
       thing.setDeviceId("THING5");
       thing.getPins().add(new Pin(1, "Pin-One", PinMode.OUTPUT));

       thingService.save(thing);
       assertEquals(1, pinService.findAll().size());

    }

    @Test
    public void deleteWithPins() {
        Thing thing = new Thing();
        thing.setIpAddress("192.168.1.5");
        thing.setDeviceId("THING5");
        thing.getPins().add(new Pin(1, "Pin-One", PinMode.OUTPUT));
        Thing saved = thingService.save(thing);
        assertEquals(1, pinService.findAll().size());
        thingService.deleteById(saved.getId());
        assertEquals(0, pinService.findAll().size());
    }
}
