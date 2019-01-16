package coolbeans.microthings8266hub.service.repositories.map;

import coolbeans.microthings8266hub.model.Thing;
import coolbeans.microthings8266hub.service.repositories.ThingService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Profile({"default","map"})
public class ThingMapService implements ThingService {

    private Map<Long, Thing> thingMap = new HashMap<>();

    @Override
    public List<Thing> findAll() {
        List<Thing> things = new ArrayList<>();
        thingMap.values().forEach(things::add);
        return things;
    }

    @Override
    public Thing findById(Long id) {
        return thingMap.get(id);
    }

    @Override
    public Thing findByName(String name) {
        Optional<Thing> found = thingMap.values().stream()
                .filter(thing -> thing.getName().equals(name))
                .findFirst();
        return found.orElse(null);
    }

    @Override
    public Thing save(Thing thing) {
        if (thing.getId() == null) {
            thing.setId(getNextId());
        }
        thingMap.put(thing.getId(), thing);
        return thing;
    }

    @Override
    public void deleteById(Long id) {
        thingMap.remove(id);
    }

    private long getNextId() {
        Long max = thingMap.keySet().stream()
                .max(Comparator.comparing(Long::valueOf))
                .orElse(0L);
        return max + 1;
    }
}
