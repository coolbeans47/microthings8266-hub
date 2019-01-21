package coolbeans.microthings8266hub.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ThingAppConfiguration {
    @Value("${thing.udp.port:7821}")
    private int udpPort;

    @Value("${thing.tcp.port:7822}")
    private int tcpPort;

    public int getUdpPort() {
        return udpPort;
    }

    public int getTcpPort() {
        return tcpPort;
    }
}
