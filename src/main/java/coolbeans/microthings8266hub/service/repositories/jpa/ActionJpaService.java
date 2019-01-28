package coolbeans.microthings8266hub.service.repositories.jpa;

import coolbeans.microthings8266hub.model.Action;
import coolbeans.microthings8266hub.repositories.ActionRepository;
import coolbeans.microthings8266hub.service.repositories.ActionService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Profile({"JPA"})
public class ActionJpaService implements ActionService {

    private final ActionRepository actionRepository;

    public ActionJpaService(ActionRepository actionRepository) {
        this.actionRepository = actionRepository;
    }

    @Override
    public List<Action> findAll() {
        List<Action> result = new ArrayList<>();
        actionRepository.findAll().forEach(result::add);
        return result;
    }

    @Override
    public Action findById(Long id) {
        return actionRepository.findById(id).orElse(null);
    }

    @Override
    public Action save(Action action) {
        return actionRepository.save(action);
    }

    @Override
    public void deleteById(Long id) {
        actionRepository.deleteById(id);
    }
}
