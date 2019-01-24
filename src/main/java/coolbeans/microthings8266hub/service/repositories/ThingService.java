package coolbeans.microthings8266hub.service.repositories;

import coolbeans.microthings8266hub.model.Thing;

import java.util.List;

public interface ThingService {

    List<Thing> findAll();
    Thing findById(Long id);
    Thing findByDeviceId(String name);
    Thing save(Thing thing);
    void deleteById(Long id);
}
