package coolbeans.microthings8266hub.events;

import coolbeans.microthings8266hub.model.Thing;

public class ThingConnectedEvent extends BaseThingEvent {
    public ThingConnectedEvent(Object source, Thing thing) {
        super(source, thing);
    }
}
