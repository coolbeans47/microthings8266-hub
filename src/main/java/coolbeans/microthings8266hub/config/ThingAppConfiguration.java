package coolbeans.microthings8266hub.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.logging.Logger;

@Configuration
public class ThingAppConfiguration {
    private static final Logger logger = Logger.getLogger(ThingAppConfiguration.class.getName());

    @Value("${thing.udp.port:7821}")
    private int udpPort;

    @Bean
    public DatagramSocket getDatagramSocket() {
        try {
            logger.info("Creating DatagramSOcket on port: " + udpPort);
            return new DatagramSocket(udpPort);
        } catch (SocketException e) {
            logger.severe("Unaable to create DatagramSocket (possibly port in use?)." + e.toString());
            throw new RuntimeException("Unable to create DatagramSocket: " + e.toString());
        }
    }
}
