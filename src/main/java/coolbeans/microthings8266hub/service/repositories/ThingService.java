package coolbeans.microthings8266hub.service.repositories;

import coolbeans.microthings8266hub.model.Thing;

import java.util.List;

public interface ThingService {

    List<Thing> findAll();
    Thing findById(long id);
    Thing findByName(String name);
    Thing save(Thing thing);
    void deleteById(long id);
}