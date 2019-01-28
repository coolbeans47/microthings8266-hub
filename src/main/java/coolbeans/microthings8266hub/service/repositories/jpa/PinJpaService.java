package coolbeans.microthings8266hub.service.repositories.jpa;

import coolbeans.microthings8266hub.model.Pin;
import coolbeans.microthings8266hub.repositories.PinRepository;
import coolbeans.microthings8266hub.service.repositories.PinService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Profile({"JPA"})
public class PinJpaService implements PinService {
    private final PinRepository pinRepository;

    public PinJpaService(PinRepository pinRepository) {
        this.pinRepository = pinRepository;
    }

    @Override
    public List<Pin> findAll() {
        List<Pin> results = new ArrayList<>();
        pinRepository.findAll().forEach(results::add);
        return results;
    }

    @Override
    public Pin findById(Long id) {
        return  pinRepository.findById(id).orElse(null);
    }

    @Override
    public Pin save(Pin pin) {
        return pinRepository.save(pin);
    }

    @Override
    public void deleteById(Long id) {
        pinRepository.deleteById(id);
    }
}
