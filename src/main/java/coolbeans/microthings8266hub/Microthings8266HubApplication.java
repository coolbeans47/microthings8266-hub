package coolbeans.microthings8266hub;

import coolbeans.microthings8266hub.service.ThingConnectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.logging.Logger;

@SpringBootApplication
@EnableAsync
public class Microthings8266HubApplication {

    private static final Logger logger = Logger.getLogger(Microthings8266HubApplication.class.getName());

    @Autowired
    private ThingConnectionService connectionService;

    public static void main(String[] args) {
        logger.info("**MAIN APPPLICATION TRHEAD ID: " + Thread.currentThread().getId());
        SpringApplication.run(Microthings8266HubApplication.class, args);
    }


    @PostConstruct
    public void startThingConnectionService() {
        connectionService.start();
    }


    @PreDestroy
    public void stopThingConnectionService() {
        connectionService.stop();
    }
}

