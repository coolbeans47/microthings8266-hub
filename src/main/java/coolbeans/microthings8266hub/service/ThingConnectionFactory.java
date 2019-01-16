package coolbeans.microthings8266hub.service;

import coolbeans.microthings8266hub.esp8266.ThingClientConnection;
import coolbeans.microthings8266hub.model.Thing;

public interface ThingConnectionFactory {

    ThingClientConnection createConnection(Thing thing);
}
