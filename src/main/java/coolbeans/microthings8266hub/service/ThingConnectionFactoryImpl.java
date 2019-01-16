package coolbeans.microthings8266hub.service;

import coolbeans.microthings8266hub.esp8266.ThingClientConnection;
import coolbeans.microthings8266hub.esp8266.ThingClientConnectionImpl;
import coolbeans.microthings8266hub.model.Thing;
import org.springframework.stereotype.Service;

@Service
public class ThingConnectionFactoryImpl implements ThingConnectionFactory {
    @Override
    public ThingClientConnection createConnection(Thing thing) {
        ThingClientConnection connection = new ThingClientConnectionImpl();
        return connection;
    }
}
