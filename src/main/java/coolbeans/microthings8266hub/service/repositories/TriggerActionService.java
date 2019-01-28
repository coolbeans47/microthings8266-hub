package coolbeans.microthings8266hub.service.repositories;

import coolbeans.microthings8266hub.model.TriggerAction;

import java.util.List;

public interface TriggerActionService {

    List<TriggerAction> findAll();
    TriggerAction findById(Long id);
    TriggerAction save(TriggerAction triggerAction);
    void deleteById(Long id);
}

