package coolbeans.microthings8266hub.events;

import coolbeans.microthings8266hub.model.Thing;

public class ThingActionCompleteEvent extends BaseThingEvent {

    private Object response;
    private String actionName;

    public ThingActionCompleteEvent(Object source, Thing thing, Object response, String actionName) {
        super(source, thing);
        this.response = response;
        this.actionName = actionName;
    }

    public Object getResponse() {
        return response;
    }

    public String getActionName() {
        return actionName;
    }
}
