package coolbeans.microthings8266hub.service.repositories.map;

import coolbeans.microthings8266hub.model.Thing;
import coolbeans.microthings8266hub.service.repositories.ActionService;
import coolbeans.microthings8266hub.service.repositories.PinService;
import coolbeans.microthings8266hub.service.repositories.ThingService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Profile({"default","map"})
public class ThingMapService extends AbstractMapService<Thing>  implements ThingService {

    private final PinService pinService;
    private final ActionService actionService;

    public ThingMapService(PinService pinService, ActionService actionService) {
        this.pinService = pinService;
        this.actionService = actionService;
    }

    @Override
    public Thing findByName(String name) {
        Optional<Thing> found = map.values().stream()
                .filter(thing -> thing.getName().equals(name))
                .findFirst();
        return found.orElse(null);
    }


    @Override
    public Thing save(Thing thing) {
        thing.setId(generateId(thing.getId()));

        thing.getPins().forEach(pin -> {
            pin.setThing(thing);
            pinService.save(pin);
        });
        thing.getActions().forEach(action -> {
            action.setThing(thing);
            actionService.save(action);
        });
        super.save(thing.getId(), thing);
        return thing;
    }

    @Override
    public void deleteById(Long id) {
        Thing thing = findById(id);
        if (thing != null) {
            if (thing.getPins() != null) {
                thing.getPins().forEach(pin -> pinService.deleteById(pin.getId()));
            }
            if (thing.getActions() != null) {
                thing.getActions().forEach(action -> actionService.deleteById(action.getId()));
            }
        }

        super.deleteById(id);
    }
}
