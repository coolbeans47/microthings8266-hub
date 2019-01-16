package coolbeans.microthings8266hub.events;

import coolbeans.microthings8266hub.model.ThingConnection;
import org.springframework.context.ApplicationEvent;

public class ThingConnectionEvent extends ApplicationEvent {

    private final ThingConnection thingConnection;
    public ThingConnectionEvent(Object source, ThingConnection thingConnection) {
        super(source);
        this.thingConnection = thingConnection;
    }

    public ThingConnection getThingConnection() {
        return thingConnection;
    }
}
