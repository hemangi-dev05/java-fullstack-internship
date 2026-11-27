package generic;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

// T is the type of object stored, ID is the type of its id (Integer, String, ...)
public interface Repository<T, ID> {

    void save(T entity);

    Optional<T> findById(ID id);

    List<T> findAll();

    // returns every object that matches the given condition
    List<T> findWhere(Predicate<T> condition);

    // true if something was deleted, false if the id was not found
    boolean deleteById(ID id);

    int count();
}