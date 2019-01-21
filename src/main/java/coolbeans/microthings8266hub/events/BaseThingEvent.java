package coolbeans.microthings8266hub.events;

import coolbeans.microthings8266hub.model.Thing;
import org.springframework.context.ApplicationEvent;

public abstract class BaseThingEvent extends ApplicationEvent {

    private final Thing thing;

    public BaseThingEvent(Object source, Thing thing) {
        super(source);
        this.thing = thing;
    }

    public Thing getThing() {
        return thing;
    }
}
