package coolbeans.microthings8266hub.service.repositories.map;

import coolbeans.microthings8266hub.model.Thing;
import coolbeans.microthings8266hub.service.repositories.ThingService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Profile({"default","map"})
public class ThingMapService implements ThingService {
    @Override
    public List<Thing> findAll() {
        return null;
    }

    @Override
    public Thing findById(long id) {
        return null;
    }

    @Override
    public Thing findByName(String name) {
        return null;
    }

    @Override
    public Thing save(Thing thing) {
        return null;
    }

    @Override
    public void deleteById(long id) {

    }
}
