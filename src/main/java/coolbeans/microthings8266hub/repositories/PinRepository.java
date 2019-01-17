package coolbeans.microthings8266hub.repositories;

import coolbeans.microthings8266hub.model.Pin;
import org.springframework.data.repository.CrudRepository;

public interface PinRepository extends CrudRepository<Long, Pin> {
}
