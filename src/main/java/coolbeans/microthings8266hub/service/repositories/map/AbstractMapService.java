package coolbeans.microthings8266hub.service.repositories.map;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public abstract class AbstractMapService<T> {

    protected ConcurrentMap<Long, T> map= new ConcurrentHashMap<>();

    public List<T> findAll() {
        List<T> things = new ArrayList<>();
        map.values().forEach(things::add);
        return things;
    }

    public T findById(Long id) {
        return map.get(id);
    }

    public void deleteById(Long id) {
        map.remove(id);
    }

    protected Long generateId(Long id) {
        if (id != null) return id;
        Long max = map.keySet().stream()
                .max(Comparator.comparing(Long::valueOf))
                .orElse(0L);
        return max + 1;
    }

    protected void save(Long id, T object) {
        map.put(id, object);
    }

}
