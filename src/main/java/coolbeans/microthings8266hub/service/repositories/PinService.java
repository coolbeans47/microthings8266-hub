package coolbeans.microthings8266hub.service.repositories;

import coolbeans.microthings8266hub.model.Pin;

import java.util.List;

public interface PinService {
    List<Pin> findAll();
    Pin findById(Long id);
    Pin save(Pin pin);
    void deleteById(Long id);
}
