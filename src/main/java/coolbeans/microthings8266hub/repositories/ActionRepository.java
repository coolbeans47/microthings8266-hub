package coolbeans.microthings8266hub.repositories;

import coolbeans.microthings8266hub.model.Action;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ActionRepository extends CrudRepository<Action, Long> {
    Optional<Action> findByName(String name);
}
