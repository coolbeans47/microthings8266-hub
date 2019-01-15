package coolbeans.microthings8266hub.service;

import coolbeans.microthings8266hub.model.DeviceConnection;

import java.io.IOException;

public class DeviceConnectionServiceImpl implements DeviceConnectionService {

    private final DatagramService datagramService;

    public DeviceConnectionServiceImpl(DatagramService datagramService) {
        this.datagramService = datagramService;
    }


    @Override
    public DeviceConnection waitForConnection() throws IOException {
        DeviceConnection device = new DeviceConnection();

        String name = datagramService.receiveString();
        device.setName(name);
        device.setIpAddress(datagramService.getPacket().getAddress().getHostAddress());
        return device;
    }
}
