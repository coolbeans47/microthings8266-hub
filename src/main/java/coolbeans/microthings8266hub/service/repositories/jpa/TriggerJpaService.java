package coolbeans.microthings8266hub.service.repositories.jpa;

import coolbeans.microthings8266hub.model.Trigger;
import coolbeans.microthings8266hub.repositories.TriggerRepository;
import coolbeans.microthings8266hub.service.repositories.TriggerService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Profile({"JPA"})
public class TriggerJpaService implements TriggerService {


    private final TriggerRepository triggerRepository;

    public TriggerJpaService(TriggerRepository triggerRepository) {
        this.triggerRepository = triggerRepository;
    }

    @Override
    public List<Trigger> findAll() {
        List<Trigger> triggers = new ArrayList<>();
        triggerRepository.findAll().forEach(triggers::add);
        return triggers;
    }

    @Override
    public Trigger findById(Long id) {
        return triggerRepository.findById(id).orElse(null);
    }

    @Override
    public Trigger save(Trigger trigger) {
        return triggerRepository.save(trigger);
    }

    @Override
    public void deleteById(Long id) {
        triggerRepository.deleteById(id);
    }
}
