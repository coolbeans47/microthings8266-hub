package coolbeans.microthings8266hub.service;

import coolbeans.microthings8266hub.model.ThingConnection;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetSocketAddress;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

public class DeviceConnectionServiceImplTest {

    @Mock
    DatagramService datagramService;

    @InjectMocks
    ThingConnectionServiceImpl deviceConnectionService;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        deviceConnectionService = new ThingConnectionServiceImpl(datagramService, null);
    }

    @Test
    public void waitForConnection() throws IOException {
        String deviceName = "DEVICE_1111_222_333";
        int devicePort = 1234;
        String deviceIp = "192.168.2.1";
        when(datagramService.receiveString()).thenReturn(deviceName);

        byte[] buffer = new byte[100];
        DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
        packet.setSocketAddress(new InetSocketAddress(deviceIp, devicePort));

        when(datagramService.getPacket()).thenReturn(packet);

        ThingConnection devCon = deviceConnectionService.waitForConnection();

        assertEquals(deviceName, devCon.getName());
        assertEquals(deviceIp, devCon.getIpAddress());
    }
}