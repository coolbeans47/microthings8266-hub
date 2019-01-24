package coolbeans.microthings8266hub.service;

import coolbeans.microthings8266hub.model.ThingConnectionRequest;

import java.io.IOException;

public interface ThingManagerService {

    void disonnectAll();
    void connectAll();
    void connect(String deviceId) throws IOException;
    void disconnect(String deviceId);
    boolean isConnected(String deviceId);
    int getConnectedCount();
    void addConnection(ThingConnectionRequest thingConnection);
}
