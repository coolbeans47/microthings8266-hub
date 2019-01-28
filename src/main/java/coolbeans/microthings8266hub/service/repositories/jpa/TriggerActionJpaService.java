package coolbeans.microthings8266hub.service.repositories.jpa;

import coolbeans.microthings8266hub.model.TriggerAction;
import coolbeans.microthings8266hub.repositories.TriggerActionRepository;
import coolbeans.microthings8266hub.service.repositories.TriggerActionService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Profile({"JPA"})
public class TriggerActionJpaService implements TriggerActionService {

    private final TriggerActionRepository triggerActionRepository;

    public TriggerActionJpaService(TriggerActionRepository triggerActionRepository) {
        this.triggerActionRepository = triggerActionRepository;
    }

    @Override
    public List<TriggerAction> findAll() {
        List<TriggerAction> triggerActions = new ArrayList<>();
        triggerActionRepository.findAll().forEach(triggerActions::add);
        return triggerActions;
    }

    @Override
    public TriggerAction findById(Long id) {
        return triggerActionRepository.findById(id).orElse(null);
    }

    @Override
    public TriggerAction save(TriggerAction triggerAction) {
        return triggerActionRepository.save(triggerAction);
    }

    @Override
    public void deleteById(Long id) {

    }
}
