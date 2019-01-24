package coolbeans.microthings8266hub.esp8266;

import coolbeans.microthings8266hub.events.ThingActionCompleteEvent;
import coolbeans.microthings8266hub.events.ThingConnectedEvent;
import coolbeans.microthings8266hub.model.Action;
import coolbeans.microthings8266hub.model.Thing;
import coolbeans.microthings8266hub.service.ApplicationMessageService;
import coolbeans.microthings8266hub.service.SocketFactory;
import coolbeans.microthings8266hub.service.script.ScriptContext;
import coolbeans.microthings8266hub.service.script.ScriptRunner;
import org.springframework.context.annotation.Scope;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.logging.Logger;

@Component("thingClientConnection")
@Scope("prototype")
public class ThingClientConnectionImpl implements ThingClientConnection{

    private static final Logger logger = Logger.getLogger(ThingClientConnectionImpl.class.getName());

    private static final String GPIO_LIB_NAME = "gpio";

    private Socket socket;
    private Thing thing;

    private final ApplicationMessageService messageService;
    private final SocketFactory socketFactory;
    private final Esp8266CommandExecutor commandExecutor;
    private final ScriptRunner scriptRunner;

    private ScriptContext scriptContext;

    public ThingClientConnectionImpl(ApplicationMessageService messageService,
                                     SocketFactory socketFactory,
                                     Esp8266CommandExecutor commandExecutor,
                                     ScriptRunner scriptRunner) {
        this.messageService = messageService;
        this.socketFactory = socketFactory;
        this.commandExecutor = commandExecutor;
        this.scriptRunner = scriptRunner;
    }


    @Override
    @Async
    public void connect(Thing thing) {
        this.thing = thing;
        logger.info("Connectinhg to thing: " + thing.getName() + " Thread ID:" +
                Thread.currentThread().getId());
        socket = socketFactory.createSocket(thing.getIpAddress());
        commandExecutor.setSocket(socket);
        initScriptContext();
        if (socket.isConnected()) {

            //send echo
            try {
                String response = commandExecutor.echo(thing.getName());

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
    public void invokeAction(String name) {
        Action action = thing.findActionByName(name);
        if (action == null) {
            logger.warning("Action not found: " + name);
            return;
        }

        System.out.println("Invoking Action(" + name + ") : " + thing.getName() + " Thread ID:" +
                Thread.currentThread().getId());
        logger.info(action.toString());

        Object response = scriptRunner.execute(action.getScript(), action.getName());
        logger.info("Action Response: " + response);
        messageService.publish(new ThingActionCompleteEvent(this, thing, response));
    }

    /**
     * Set the timeout to 1 second and send an echo command to the connected Thing
     * and see if there is a response within that time. If not report back that we
     * are no longer connected to the device,
     * @return true if socket is still connected to a Thing
     */
    @Override
    public boolean isConnected() {
        if (socket == null || ! socket.isConnected()) return false;

        int saveTimeout = 0;
        try {
            saveTimeout = socket.getSoTimeout();
            socket.setSoTimeout(1000);
            try {
                commandExecutor.echo("Test");
                return true;
            } catch(SocketTimeoutException e) {
                return false;
            }
        } catch (IOException e) {
            logger.warning("Error while attempting an echo in isConnected(): " + e.toString());
           return false;
        } finally {
            try {
                socket.setSoTimeout(saveTimeout);
            } catch (SocketException e) {
                logger.warning("Failed to reset timeout: " + e.toString());
            }
        }
    }

    public Thing getThing() {
        return thing;
    }

    private void initScriptContext() {
        scriptContext= new ScriptContext();
        scriptContext.getContext().put(GPIO_LIB_NAME, commandExecutor);
        scriptRunner.setContext(scriptContext);

        thing.getPins().forEach(pin -> scriptContext.getContext().put(pin.getName(), pin.getPinNbr()));
    }
}
