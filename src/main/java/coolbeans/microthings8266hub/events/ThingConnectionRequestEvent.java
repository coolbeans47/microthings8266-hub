package coolbeans.microthings8266hub.events;

import coolbeans.microthings8266hub.model.ThingConnectionRequest;
import org.springframework.context.ApplicationEvent;

public class ThingConnectionRequestEvent extends ApplicationEvent {

    private final ThingConnectionRequest thingConnectionRequest;
    public ThingConnectionRequestEvent(Object source, ThingConnectionRequest thingConnectionRequest) {
        super(source);
        this.thingConnectionRequest = thingConnectionRequest;
    }

    public ThingConnectionRequest getThingConnectionRequest() {
        return thingConnectionRequest;
    }
}
