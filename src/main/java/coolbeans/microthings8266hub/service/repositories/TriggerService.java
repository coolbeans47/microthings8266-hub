package coolbeans.microthings8266hub.service.repositories;

import coolbeans.microthings8266hub.model.Trigger;

import java.util.List;

public interface TriggerService {

    List<Trigger> findAll();
    Trigger findById(Long id);
    Trigger save(Trigger trigger);
    void deleteById(Long id);
}
