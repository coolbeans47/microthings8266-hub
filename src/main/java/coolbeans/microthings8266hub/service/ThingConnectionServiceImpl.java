package coolbeans.microthings8266hub.service;

import coolbeans.microthings8266hub.events.ThingConnectionRequestEvent;
import coolbeans.microthings8266hub.model.ThingConnectionRequest;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.logging.Logger;

@Service
public class ThingConnectionServiceImpl implements ThingConnectionService {

    private static final Logger logger = Logger.getLogger(ThingConnectionServiceImpl.class.getName());

    private final DatagramService datagramService;
    private final ApplicationMessageService messageService;

    private boolean running;

    public ThingConnectionServiceImpl(DatagramService datagramService, ApplicationMessageService messageService) {
        this.datagramService = datagramService;
        this.messageService = messageService;
    }


    @Override
    public ThingConnectionRequest waitForConnection() throws IOException {
        ThingConnectionRequest device = new ThingConnectionRequest();

        String name = datagramService.receiveString();
        device.setName(name);
        device.setIpAddress(datagramService.getPacket().getAddress().getHostAddress());
        return device;
    }

    @Async
    public void start() {
            logger.info("Waiting for UDP connections on port: " + datagramService.getPort());
            running = true;
            while (running) {
                ThingConnectionRequest connection = null;
                try {
                    connection = waitForConnection();
                    messageService.publish(new ThingConnectionRequestEvent(this, connection));
                    logger.info("Thing Connection: " + connection.toString());
                } catch (IOException e) {
                    logger.severe("Error waiting for connection" + e.toString());
                    e.printStackTrace();
                }
            }
            logger.info("Thing Connection Service Stopped");
    }

    public void stop() {
        running = false;
    }
}
