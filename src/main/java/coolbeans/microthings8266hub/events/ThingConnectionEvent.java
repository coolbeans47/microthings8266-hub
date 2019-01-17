package coolbeans.microthings8266hub.events;

import coolbeans.microthings8266hub.model.ThingConnectionRequest;
import org.springframework.context.ApplicationEvent;

public class ThingConnectionEvent extends ApplicationEvent {

    private final ThingConnectionRequest thingConnectionRequest;
    public ThingConnectionEvent(Object source, ThingConnectionRequest thingConnectionRequest) {
        super(source);
        this.thingConnectionRequest = thingConnectionRequest;
    }

    public ThingConnectionRequest getThingConnectionRequest() {
        return thingConnectionRequest;
    }
}
