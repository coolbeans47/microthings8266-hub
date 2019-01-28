package coolbeans.microthings8266hub.service;

import coolbeans.microthings8266hub.events.ThingConnectionRequestEvent;
import coolbeans.microthings8266hub.model.ThingConnectionRequest;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.logging.Logger;

@Service
public class ThingUdpConnectionService implements ThingConnectionService {

    private static final long MIN_REPEAT_REQUEST_MS = 5000;

    private static final Logger logger = Logger.getLogger(ThingUdpConnectionService.class.getName());

    private final DatagramService datagramService;
    private final ApplicationMessageService messageService;
    private ConcurrentMap<String, Long> connectRequestTime = new ConcurrentHashMap<>();

    private boolean running;

    public ThingUdpConnectionService(DatagramService datagramService, ApplicationMessageService messageService) {
        this.datagramService = datagramService;
        this.messageService = messageService;
    }


    @Override
    public ThingConnectionRequest waitForConnection() throws IOException {
        ThingConnectionRequest device = new ThingConnectionRequest();

        String name = datagramService.receiveString();
        device.setDeviceId(name);
        device.setIpAddress(datagramService.getPacket().getAddress().getHostAddress());
        return device;
    }

    @Async
    public void start() {
            logger.info("Waiting for UDP connections on port: " + datagramService.getPort() +
                    " Thread ID: " + Thread.currentThread().getId());
            running = true;
            while (running) {
                ThingConnectionRequest connection = null;
                try {
                    connection = waitForConnection();
                    if (canConnect(connection.getIpAddress())) {
                        logger.info("Publishing thing connection Request:" + connection.toString());
                        messageService.publish(new ThingConnectionRequestEvent(this, connection));
                    } else {
                        logger.info("Ignoring reoeated request:" + connection.toString());
                    }

                } catch (IOException e) {
                    logger.severe("Error waiting for connection" + e.toString());
                    e.printStackTrace();
                }
            }
            logger.info("Thing Connection Service Stopped");
    }

    //Ignore any repeat connection requests within 5secs
    private boolean canConnect(String ipAddress) {
        if (!connectRequestTime.containsKey(ipAddress)) {
            long time = System.currentTimeMillis();
            connectRequestTime.put(ipAddress, time);
            return true;
        }

        long delta = System.currentTimeMillis() - connectRequestTime.get(ipAddress);
        connectRequestTime.remove(ipAddress);
        long time = System.currentTimeMillis();
        connectRequestTime.put(ipAddress, System.currentTimeMillis());
        return delta > MIN_REPEAT_REQUEST_MS;
    }

    public void stop() {
        running = false;
    }
}
