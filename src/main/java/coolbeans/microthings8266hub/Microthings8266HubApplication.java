package coolbeans.microthings8266hub;

import coolbeans.microthings8266hub.service.ThingConnectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@SpringBootApplication
@EnableAsync
public class Microthings8266HubApplication {

    @Autowired
    private ThingConnectionService connectionService;

    public static void main(String[] args) {
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

