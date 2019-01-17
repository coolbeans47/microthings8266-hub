package coolbeans.microthings8266hub.repositories;

import coolbeans.microthings8266hub.model.Pin;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PinRepository extends CrudRepository<Pin, Long> {
}
