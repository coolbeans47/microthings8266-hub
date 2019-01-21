package coolbeans.microthings8266hub.service;

import coolbeans.microthings8266hub.config.ThingAppConfiguration;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.logging.Logger;

@Service
public class SocketFactoryImpl implements SocketFactory {
    private static final Logger logger = Logger.getLogger(SocketFactoryImpl.class.getName());


    private final ThingAppConfiguration appConfiguration;

    public SocketFactoryImpl(ThingAppConfiguration appConfiguration) {
        this.appConfiguration = appConfiguration;
    }

    @Override
    public Socket createSocket(String ipAddress) {
        logger.info("Creating Socket on port: " + appConfiguration.getTcpPort());
        try {
            return new Socket(ipAddress, appConfiguration.getTcpPort());
        } catch (IOException e) {
            logger.severe("Unaable to create Socket?)." + e.toString());
            throw new RuntimeException("Unable to create Socket: " + e.toString());
        }
    }

    @Override
    public DatagramSocket createDatagramSocket() {
        try {
            logger.info("Creating DatagramSOcket on port: " + appConfiguration.getUdpPort());
            return new DatagramSocket(appConfiguration.getUdpPort());
        } catch (SocketException e) {
            logger.severe("Unaable to create DatagramSocket (possibly port in use?)." + e.toString());
            throw new RuntimeException("Unable to create DatagramSocket: " + e.toString());
        }
    }
}
