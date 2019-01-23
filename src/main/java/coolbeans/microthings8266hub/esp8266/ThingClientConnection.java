package coolbeans.microthings8266hub.esp8266;

import coolbeans.microthings8266hub.model.Thing;

public interface ThingClientConnection {

    void connect(Thing thing);
    void close();
    void invokeAction(long actionId);
    boolean isConnected();
}
