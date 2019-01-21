package coolbeans.microthings8266hub.events;

import coolbeans.microthings8266hub.model.Thing;

public class ThingActionCompleteEvent extends BaseThingEvent {
    public ThingActionCompleteEvent(Object source, Thing thing) {
        super(source, thing);
    }
}
