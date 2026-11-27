package generic;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;

// One class that works for any type, because it uses generics.
public class GenericRepository<T, ID> implements Repository<T, ID> {

    // LinkedHashMap keeps the objects in the order they were added
    private final Map<ID, T> storage = new LinkedHashMap<>();

    // tells the repository how to get the id from an object (example: Product::getId)
    private final Function<T, ID> idGetter;

    public GenericRepository(Function<T, ID> idGetter) {
        this.idGetter = idGetter;
    }

    @Override
    public void save(T entity) {
        // same id again simply replaces the old object (update)
        storage.put(idGetter.apply(entity), entity);
    }

    @Override
    public Optional<T> findById(ID id) {
        return Optional.ofNullable(storage.get(id));
    }

    @Override
    public List<T> findAll() {
        return new ArrayList<>(storage.values());
    }

    @Override
    public List<T> findWhere(Predicate<T> condition) {
        List<T> result = new ArrayList<>();
        for (T item : storage.values()) {
            if (condition.test(item)) {
                result.add(item);
            }
        }
        return result;
    }

    @Override
    public boolean deleteById(ID id) {
        return storage.remove(id) != null;
    }

    @Override
    public int count() {
        return storage.size();
    }
}