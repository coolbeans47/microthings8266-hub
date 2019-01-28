package coolbeans.microthings8266hub.repositories;

import coolbeans.microthings8266hub.model.Trigger;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TriggerRepository extends CrudRepository<Trigger, Long> {
}
