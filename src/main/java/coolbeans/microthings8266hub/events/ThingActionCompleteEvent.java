package coolbeans.microthings8266hub.events;

import coolbeans.microthings8266hub.model.Thing;

public class ThingActionCompleteEvent extends BaseThingEvent {

    private Object response;
    public ThingActionCompleteEvent(Object source, Thing thing, Object response) {
        super(source, thing);
        this.response = response;
    }

    public Object getResponse() {
        return response;
    }
}
