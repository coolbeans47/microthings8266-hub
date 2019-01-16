package coolbeans.microthings8266hub.service;

import coolbeans.microthings8266hub.model.ThingConnection;

import java.io.IOException;

public interface ThingConnectionService {

    ThingConnection waitForConnection() throws IOException;
    void start();
    void stop();
}
