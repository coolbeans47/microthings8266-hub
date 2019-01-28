package coolbeans.microthings8266hub.service.repositories.map;


import coolbeans.microthings8266hub.model.Trigger;
import coolbeans.microthings8266hub.service.repositories.TriggerActionService;
import coolbeans.microthings8266hub.service.repositories.TriggerService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile({"default","map"})
public class TriggerMapService extends AbstractMapService<Trigger> implements TriggerService {

    private final TriggerActionService triggerActionService;

    public TriggerMapService(TriggerActionService triggerActionService) {
        this.triggerActionService = triggerActionService;
    }

    @Override
    public Trigger save(Trigger trigger) {
        trigger.setId(generateId(trigger.getId()));
        super.save(trigger.getId(), trigger);
        return trigger;
    }

    @Override
    public void deleteById(Long id) {
        Trigger trigger = findById(id);
        super.deleteById(id);
        if (trigger != null) {
            trigger.getTriggerActions().forEach(triggerAction -> triggerActionService.deleteById(triggerAction.getId()));
        }
    }
}
