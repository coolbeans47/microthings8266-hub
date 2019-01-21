package coolbeans.microthings8266hub.events;

import coolbeans.microthings8266hub.model.Thing;

public class ThingDisconnectedEvent extends BaseThingEvent {
    public ThingDisconnectedEvent(Object source, Thing thing) {
        super(source, thing);
    }
}
