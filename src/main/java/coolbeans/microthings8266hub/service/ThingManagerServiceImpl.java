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

    private ConcurrentMap<Long, ThingClientConnection> connected = new ConcurrentHashMap<>();

    public ThingManagerServiceImpl(ThingService thingService,
                                   ApplicationContext applicationContext) {
        this.thingService = thingService;
        this.applicationContext = applicationContext;
    }

    @Override
    public void disonnectAll() {
        connected.values().forEach(connection -> {
            connection.close();
        });
        connected.clear();
    }

    @Override
    public void connectAll() {
        List<Thing> things = thingService.findAll();
        things.forEach(thing -> {
            if (!isConnected(thing.getId())) {
                connectThing(thing);
            }
        });
    }

    @Override
    public void connect(long id) throws IOException {
        if (isConnected(id)) {
            throw new IOException("Connect Failed: Thing #" + id + " already connected");
        }
        Thing thing = thingService.findById(id);
        if (thing == null) {
            throw new IOException("No database entry for thing#" + id);
        }

        connectThing(thing);
    }

    @Override
    public void disconnect(long id) {
        if (isConnected(id)) {
            connected.get(id).close();
            connected.remove(id);
        }
    }

    @Override
    public boolean isConnected(long id) {
        return connected.containsKey(id);
    }

    @Override
    public int getConnectedCount() {
        return connected.size();
    }

    @Override
    public void addConnection(ThingConnectionRequest connectionRequest) {
        //If it already exists check UP adresss in database and updated if required
        Thing thing = thingService.findByName(connectionRequest.getName());
        if (thing == null) {
            thing = new Thing();
            thing.setName(connectionRequest.getName());
            thing.setIpAddress(connectionRequest.getIpAddress());
            thing = thingService.save(thing);
        } else {
            logger.info("Thing: " + thing.getName() + " already exists");
            if (!thing.getIpAddress().equals(connectionRequest.getIpAddress())) {
                logger.info("Thing: " + thing.getName() + "- Updating IP Address from: " +
                        thing.getIpAddress() + " to: " + connectionRequest.getIpAddress());
                thing.setIpAddress(connectionRequest.getIpAddress());
                thingService.save(thing);
            }
        }
        logger.info("Connecting THing: " + thing.toString());
        connectThing(thing);
    }

    @EventListener
    public void newConnectioEvent(ThingConnectionRequestEvent event) {
        logger.info("EVentListener ThingConnectionRequestEvent: " + event.getThingConnectionRequest().toString() +
                " Thread ID: " + Thread.currentThread().getId());

        addConnection(event.getThingConnectionRequest());
    }

    @EventListener
    public void thingConnectdEvent(ThingConnectedEvent event) {
        logger.info("EVentListener ThingConnectedEvent: " + event.getThing() +
                " Thread: " + Thread.currentThread().getId());
    }

    @EventListener
    public void thingDisconectedEvent(ThingDisconnectedEvent event) {
        logger.info("EVentListener ThingDisconnectedEvent: " + event.getThing() +
                " Thread: " + Thread.currentThread().getId());
    }

    @EventListener
    public void thingActionCompleteEvent(ThingActionCompleteEvent event) {
        logger.info("EVentListener ThingActionCompleteEvent: " + event.getThing() +
                " Thread: " + Thread.currentThread().getId());
    }

    private ThingClientConnection connectThing(Thing thing) {
        //ThingClientConnection connection = connectionFactory.createConnection(thing);
        ThingClientConnection connection = (ThingClientConnection) applicationContext.getBean("thingClientConnection");
        connected.put(thing.getId(), connection);
        connection.connect(thing);
        return connection;
    }
}
