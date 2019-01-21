package coolbeans.microthings8266hub.esp8266;

import coolbeans.microthings8266hub.events.ThingConnectedEvent;
import coolbeans.microthings8266hub.model.Thing;
import coolbeans.microthings8266hub.service.ApplicationMessageService;
import coolbeans.microthings8266hub.service.SocketFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.Socket;
import java.util.logging.Logger;

@Component("thingClientConnection")
@Scope("prototype")
public class ThingClientConnectionImpl implements ThingClientConnection{

    private static final Logger logger = Logger.getLogger(ThingClientConnectionImpl.class.getName());

    private Socket socket;
    private Thing thing;

    private final ApplicationMessageService messageService;
    private final SocketFactory socketFactory;
    private final Esp8266CommandExecutor commandExecutor;

    public ThingClientConnectionImpl(ApplicationMessageService messageService,
                                     SocketFactory socketFactory,
                                     Esp8266CommandExecutor commandExecutor) {
        this.messageService = messageService;
        this.socketFactory = socketFactory;
        this.commandExecutor = commandExecutor;
    }


    @Override
    @Async
    public void connect(Thing thing) {
        this.thing = thing;
        System.out.println("Connectinhg to thing: " + thing.getName() + " Thread ID:" +
                Thread.currentThread().getId());
        socket = socketFactory.createSocket(thing.getIpAddress());
        if (socket.isConnected()) {

            //send echo
            try {
                String response = commandExecutor.echo(socket, thing.getName());

                if (!response.equals(thing.getName())) {
                    logger.warning("Echo Response did not match. Expected: " + thing.getName() +
                            " Received: [" + response + "]");
                }
                messageService.publish(new ThingConnectedEvent(this, thing));
            } catch (IOException e) {
                logger.warning("Failed to send echo commandL" + thing.toString() +
                        "- ERR: " + e.toString());
                e.printStackTrace();
            }
            return;
        }
        logger.severe("Could not connect to Thing: " + thing.toString());
    }

    @Override
    @Async
    public void close() {
        try {
            socket.close();
        } catch (IOException e) {
            logger.warning("Failed to close socket: " + thing.toString());
        }
        System.out.println("Closing thing: " + thing.getName() + " Thread ID:" +
                Thread.currentThread().getId());
    }

    @Override
    @Async
    public void invokeAction(ActionRequest req) {
        System.out.println("Invoking Action: " + thing.getName() + " Thread ID:" +
                Thread.currentThread().getId());
    }

    public Thing getThing() {
        return thing;
    }
}
