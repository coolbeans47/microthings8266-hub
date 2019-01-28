package coolbeans.microthings8266hub.service.repositories.map;

import coolbeans.microthings8266hub.model.TriggerAction;
import coolbeans.microthings8266hub.service.repositories.TriggerActionService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile({"default", "map"})
public class TriggerActionMapService extends AbstractMapService<TriggerAction> implements TriggerActionService {
    @Override
    public TriggerAction save(TriggerAction triggerAction) {
        triggerAction.setId(generateId(triggerAction.getId()));
        super.save(triggerAction.getId(), triggerAction);
        return triggerAction;
    }
}
