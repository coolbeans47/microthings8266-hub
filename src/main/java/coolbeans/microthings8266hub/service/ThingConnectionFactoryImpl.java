package coolbeans.microthings8266hub.service;

import coolbeans.microthings8266hub.esp8266.ThingClientConnection;
import coolbeans.microthings8266hub.esp8266.ThingClientConnectionImpl;
import coolbeans.microthings8266hub.model.Thing;
import org.springframework.stereotype.Service;

import java.net.Socket;

@Service
public class ThingConnectionFactoryImpl implements ThingConnectionFactory {

    private final SocketFactory socketFactory;

    public ThingConnectionFactoryImpl(SocketFactory socketFactory) {
        this.socketFactory = socketFactory;
    }


    @Override
    public ThingClientConnection createConnection(Thing thing) {
        Socket socket = socketFactory.createSocket(thing.getIpAddress());
        ThingClientConnection connection = new ThingClientConnectionImpl(socket, thing);
        return connection;
    }
}
