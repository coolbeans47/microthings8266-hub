package coolbeans.microthings8266hub.service.repositories;


import coolbeans.microthings8266hub.model.Action;

import java.util.List;

public interface ActionService {
        List<Action> findAll();
        Action findById(Long id);
        Action save(Action action);
        void deleteById(Long id);
        Action findByName(String name);
}