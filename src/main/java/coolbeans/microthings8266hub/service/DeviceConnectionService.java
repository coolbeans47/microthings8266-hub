package coolbeans.microthings8266hub.service;

import coolbeans.microthings8266hub.model.DeviceConnection;

import java.io.IOException;

public interface DeviceConnectionService {

    DeviceConnection waitForConnection() throws IOException;
}
