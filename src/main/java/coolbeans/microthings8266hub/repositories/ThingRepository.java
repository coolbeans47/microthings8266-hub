package coolbeans.microthings8266hub.repositories;

import coolbeans.microthings8266hub.model.Thing;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ThingRepository extends CrudRepository<Thing, Long> {
    Optional<Thing> findByName(String name);
}
