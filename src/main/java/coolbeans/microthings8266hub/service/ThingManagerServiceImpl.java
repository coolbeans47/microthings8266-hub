package coolbeans.microthings8266hub.service;

import coolbeans.microthings8266hub.esp8266.ThingClientConnection;
import coolbeans.microthings8266hub.events.ThingActionCompleteEvent;
import coolbeans.microthings8266hub.events.ThingConnectedEvent;
import coolbeans.microthings8266hub.events.ThingConnectionRequestEvent;
import coolbeans.microthings8266hub.events.ThingDisconnectedEvent;
import coolbeans.microthings8266hub.model.Thing;
import coolbeans.microthings8266hub.model.ThingConnectionRequest;
import coolbeans.microthings8266hub.service.repositories.ThingService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.logging.Logger;


@Service
public class ThingManagerServiceImpl implements ThingManagerService {

    private Logger logger = Logger.getLogger(ThingManagerServiceImpl.class.getName());

    private final ThingService thingService;
    private final ApplicationContext applicationContext;

    private ConcurrentMap<String, ThingClientConnection> connected = new ConcurrentHashMap<>();

    public ThingManagerServiceImpl(ThingService thingService,
                                   ApplicationContext applicationContext) {
        this.thingService = thingService;
        this.applicationContext = applicationContext;
    }

    @Override
    public void disonnectAll() {
        connected.values().forEach(ThingClientConnection::close);
        connected.clear();
    }

    @Override
    public void connectAll() {
        List<Thing> things = thingService.findAll();
        things.forEach(thing -> {
            if (!isConnected(thing.getName())) {
                connectThing(thing);
            }
        });
    }

    @Override
    public void connect(String deviceId) throws IOException {
        if (isConnected(deviceId)) {
            throw new IOException("Connect Failed: Thing #" + deviceId + " already connected");
        }
        Thing thing = thingService.findByDeviceId(deviceId);
        if (thing == null) {
            throw new IOException("No database entry for thing#" + deviceId);
        }

        connectThing(thing);
    }

    @Override
    public void disconnect(String deviceId) {
        if (isConnected(deviceId)) {
            connected.get(deviceId).close();
            connected.remove(deviceId);
        }
    }

    @Override
    public boolean isConnected(String deviceId) {
        return connected.containsKey(deviceId);
    }

    @Override
    public int getConnectedCount() {
        return connected.size();
    }

    @Override
    public void addConnection(ThingConnectionRequest connectionRequest) {
        //If it already exists check UP adresss in database and updated if required
        Thing thing = thingService.findByDeviceId(connectionRequest.getDeviceId());
        if (thing == null) {
            thing = new Thing();
            thing.setDeviceId(connectionRequest.getDeviceId());
            thing.setName(connectionRequest.getDeviceId());
            thing.setIpAddress(connectionRequest.getIpAddress());
            thing = thingService.save(thing);
        } else {
            logger.info("Thing: " + thing.getDeviceId() + " already exists");

            //If IP address changed update Thing model and save
            if (!thing.getIpAddress().equals(connectionRequest.getIpAddress())) {
                logger.info("Thing: " + thing.getDeviceId() + "- Updating IP Address from: " +
                        thing.getIpAddress() + " to: " + connectionRequest.getIpAddress());
                thing.setIpAddress(connectionRequest.getIpAddress());
                thingService.save(thing);
            }
            ThingClientConnection existing = connected.get(thing.getDeviceId());
            if (existing != null) {
                if (existing.isConnected()) {
                    logger.info("Closing existing device: " + thing.getDeviceId() + " - " + thing.getIpAddress());
                    connected.remove(thing.getDeviceId());
                    existing.close();
                }
                logger.info("Removing device from connected list: " + thing.getDeviceId() + " - " + thing.getIpAddress());
                connected.remove(thing.getDeviceId());
            }

        }
        logger.info("Connecting THing: " + thing.toString());
        connectThing(thing);
    }

    @EventListener
    public void newConnectioEvent(ThingConnectionRequestEvent event) {
        logger.info("*EventListener ThingConnectionRequestEvent: " + event.getThingConnectionRequest().toString() +
                " Thread ID: " + Thread.currentThread().getId());

        addConnection(event.getThingConnectionRequest());
    }

    @EventListener
    public void thingConnectdEvent(ThingConnectedEvent event) {
        logger.info("*EventListener ThingConnectedEvent: " + event.getThing() +
                " Thread: " + Thread.currentThread().getId());
        Thing thing = event.getThing();
        if (thing.getStartupActionName() != null) {
            ThingClientConnection connection = connected.get(thing.getDeviceId());
            if (connection != null) {
                connection.invokeAction(thing.getStartupActionName());
            }
        }
    }

    @EventListener
    public void thingDisconectedEvent(ThingDisconnectedEvent event) {
        logger.info("*EventListener ThingDisconnectedEvent: " + event.getThing() +
                " Thread: " + Thread.currentThread().getId());
    }

    @EventListener
    public void thingActionCompleteEvent(ThingActionCompleteEvent event) {
        logger.info("*EventListener ThingActionCompleteEvent: " + event.getThing() +
                " Response: " + event.getResponse() +
                " Action: " + event.getActionName() +
                " Thread: " + Thread.currentThread().getId());
        if (event.getActionName().equals("LEDON")) {
            logger.info("DEBUG**** INVOKING LED OFF ACTION");
            ThingClientConnection connection = connected.get(event.getThing().getDeviceId());
            connection.invokeAction("LEDOFF");
        }
    }

    private void  connectThing(Thing thing) {
        ThingClientConnection connection = (ThingClientConnection) applicationContext.getBean("thingClientConnection");
        connected.put(thing.getDeviceId(), connection);
        connection.connect(thing);
    }
}
