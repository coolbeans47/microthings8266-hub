package coolbeans.microthings8266hub.service;

import coolbeans.microthings8266hub.model.ThingConnectionRequest;

import java.io.IOException;

public interface ThingConnectionService {

    ThingConnectionRequest waitForConnection() throws IOException;
    void start();
    void stop();
}
