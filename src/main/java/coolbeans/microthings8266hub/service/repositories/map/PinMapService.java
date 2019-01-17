package coolbeans.microthings8266hub.service.repositories.map;

import coolbeans.microthings8266hub.model.Pin;
import coolbeans.microthings8266hub.service.repositories.PinService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile({"default","map"})
public class PinMapService extends AbstractMapService<Pin> implements PinService {

    @Override
    public Pin save(Pin pin) {
        pin.setId(generateId(pin.getId()));
        super.save(pin.getId(), pin);
        return pin;
    }
}
