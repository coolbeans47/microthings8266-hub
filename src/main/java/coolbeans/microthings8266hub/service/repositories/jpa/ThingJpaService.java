package coolbeans.microthings8266hub.service.repositories.jpa;

import coolbeans.microthings8266hub.model.Thing;
import coolbeans.microthings8266hub.repositories.ThingRepository;
import coolbeans.microthings8266hub.service.repositories.ThingService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Profile({"JPA"})
public class ThingJpaService implements ThingService {

    private final ThingRepository thingRepository;

    public ThingJpaService(ThingRepository thingRepository) {
        this.thingRepository = thingRepository;
    }

    @Override
    public List<Thing> findAll() {
        List<Thing> result = new ArrayList<>();
        thingRepository.findAll().forEach(result::add);
        return result;
    }

    @Override
    public Thing findById(Long id) {
        return thingRepository.findById(id).orElse(null);
    }

    @Override
    public Thing findByDeviceId(String id) {
        return thingRepository.findByDeviceId(id).orElse(null);
    }

    @Override
    public Thing save(Thing thing) {
        return  thingRepository.save(thing);
    }

    @Override
    public void deleteById(Long id) {
        thingRepository.deleteById(id);

    }
}
