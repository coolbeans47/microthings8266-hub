package coolbeans.microthings8266hub.service;

import coolbeans.microthings8266hub.model.ThingConnectionRequest;

import java.io.IOException;

public interface ThingManagerService {

    void disonnectAll();
    void connectAll();
    void connect(long id) throws IOException;
    void disconnect(long id);
    boolean isConnected(long id);
    int getConnectedCount();
    void addConnection(ThingConnectionRequest thingConnection);
}
