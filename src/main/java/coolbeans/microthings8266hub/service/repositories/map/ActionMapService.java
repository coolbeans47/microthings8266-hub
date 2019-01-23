package coolbeans.microthings8266hub.service.repositories.map;

import coolbeans.microthings8266hub.model.Action;
import coolbeans.microthings8266hub.service.repositories.ActionService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile({"default","map"})
public class ActionMapService extends AbstractMapService<Action> implements ActionService {
    @Override
    public Action save(Action action) {
        action.setId(generateId(action.getId()));
        super.save(action.getId(), action);
        return action;
    }
}
