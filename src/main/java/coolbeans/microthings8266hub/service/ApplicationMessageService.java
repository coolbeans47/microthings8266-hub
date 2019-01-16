package coolbeans.microthings8266hub.service;

import org.springframework.context.ApplicationEvent;

public interface ApplicationMessageService {

    void publish(ApplicationEvent event);
}
