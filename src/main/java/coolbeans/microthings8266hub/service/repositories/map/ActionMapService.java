package coolbeans.microthings8266hub.service.repositories.map;

import coolbeans.microthings8266hub.model.Action;
import coolbeans.microthings8266hub.service.repositories.ActionService;
import coolbeans.microthings8266hub.service.repositories.TriggerService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile({"default","map"})
public class ActionMapService extends AbstractMapService<Action> implements ActionService {

    private final TriggerService triggerService;

    public ActionMapService(TriggerService triggerService) {
        this.triggerService = triggerService;
    }


    @Override
    public Action save(Action action) {
        action.setId(generateId(action.getId()));
        super.save(action.getId(), action);
        return action;
    }

    @Override
    public void deleteById(Long id) {
        Action action = findById(id);
        if (action != null) {
            super.deleteById(id);
            if (action.getActionCompleteTrigger() != null) {
                triggerService.deleteById(action.getActionCompleteTrigger().getId());
            }
        }
    }

    @Override
    public Action findByName(String name) {
        return this.map.values().stream()
                .filter(a -> a.getName().equals(name))
                .findFirst()
                .orElse(null);
    }

    private void saveTriggers(Action action) {
        if (action.getActionCompleteTrigger() != null) {
            triggerService.save(action.getActionCompleteTrigger());
        }
    }
}
