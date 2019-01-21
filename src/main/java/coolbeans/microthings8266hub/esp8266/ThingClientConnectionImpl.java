package coolbeans.microthings8266hub.esp8266;

import coolbeans.microthings8266hub.model.Thing;
import org.springframework.scheduling.annotation.Async;

import java.net.Socket;

public class ThingClientConnectionImpl implements ThingClientConnection{

    private final Socket socket;
    private final Thing thing;

    public ThingClientConnectionImpl(Socket socket, Thing thing) {
        this.socket = socket;
        this.thing = thing;
    }


    @Override
    @Async
    public void connect() {
        System.out.println("Connectinhg to thing: " + thing.getName() + " Thread ID:" +
                Thread.currentThread().getId());
    }

    @Override
    @Async
    public void close() {
        System.out.println("Closing thing: " + thing.getName() + " Thread ID:" +
                Thread.currentThread().getId());
    }

    @Override
    @Async
    public ActionResponse invokeAction(ActionRequest req) {
        System.out.println("Invoking Action: " + thing.getName() + " Thread ID:" +
                Thread.currentThread().getId());

        return null;
    }
}
