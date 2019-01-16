package coolbeans.microthings8266hub.service;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
public class ApplicationMessageServiceImpl implements ApplicationMessageService {


    private final ApplicationEventPublisher eventPublisher;

    public ApplicationMessageServiceImpl(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    @Override
    public void publish(ApplicationEvent event) {
        eventPublisher.publishEvent(event);
    }
}
