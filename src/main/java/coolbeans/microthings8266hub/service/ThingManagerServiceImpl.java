package coolbeans.microthings8266hub.service;

import coolbeans.microthings8266hub.esp8266.ThingClientConnection;
import coolbeans.microthings8266hub.events.ThingConnectionEvent;
import coolbeans.microthings8266hub.model.Thing;
import coolbeans.microthings8266hub.model.ThingConnectionRequest;
import coolbeans.microthings8266hub.service.repositories.ThingService;
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
    private final ThingConnectionFactory connectionFactory;

    private ConcurrentMap<Long, ThingClientConnection> connected = new ConcurrentHashMap<>();

    public ThingManagerServiceImpl(ThingService thingService, ThingConnectionFactory connectionFactory) {
        this.thingService = thingService;
        this.connectionFactory = connectionFactory;
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
    public void addConnection(ThingConnectionRequest thingConnection) {
        //If it already exists check UP adresss in database and updated if required
        Thing thing = thingService.findByName(thingConnection.getName());
        if (thing == null) {
            thing = new Thing();
            thing.setName(thingConnection.getName());
            thing.setIpAddress(thingConnection.getIpAddress());
            thing = thingService.save(thing);
        } else {
            logger.info("Thing: " + thing.getName() + " already exists");
            if (!thing.getIpAddress().equals(thingConnection.getIpAddress())) {
                logger.info("Thing: " + thing.getName() + "- Updating IP Address from: " +
                        thing.getIpAddress() + " to: " + thingConnection.getIpAddress());
                thing.setIpAddress(thingConnection.getIpAddress());
                thingService.save(thing);
            }
        }
        logger.info("Connecting THing: " + thing.toString());
        connectThing(thing);
    }

    @EventListener
    public void newConnectioEvent(ThingConnectionEvent event) {
        logger.info("EVentListener ThingConnectionEvent: " + event.getThingConnectionRequest().toString());
        addConnection(event.getThingConnectionRequest());
    }

    private ThingClientConnection connectThing(Thing thing) {
        ThingClientConnection connection = connectionFactory.createConnection(thing);
        connected.put(thing.getId(), connection);
        connection.connect(thing);
        return connection;
    }
}
