package model.repo;

import infra.IdGenerator;
import infra.JsonDb;

import java.util.*;
import java.util.function.Function;

public abstract class JsonRepository<T> implements Repository<T> {

    protected final String path;
    private final Class<T> clazz;
    private final Function<T, Long> idGetter;
    private final IdSetter<T> idSetter;

    protected interface IdSetter<T> { void set(T t, long id); }

    protected JsonRepository(String path, Class<T> clazz, Function<T, Long> idGetter, IdSetter<T> idSetter) {
        this.path = path;
        this.clazz = clazz;
        this.idGetter = idGetter;
        this.idSetter = idSetter;
    }

    @Override
    public List<T> findAll() {
        return new ArrayList<>(JsonDb.readList(path, clazz));
    }

    @Override
    public T findById(long id) {
        return findAll().stream().filter(e -> Objects.equals(idGetter.apply(e), id)).findFirst().orElse(null);
    }

    @Override
    public T save(T entity) {
        List<T> all = findAll();
        Long id = idGetter.apply(entity);
        if (id == null || id == 0) {
            long newId = IdGenerator.nextId();
            idSetter.set(entity, newId);
            all.add(entity);
        } else {
            for (int i = 0; i < all.size(); i++) {
                if (Objects.equals(idGetter.apply(all.get(i)), id)) {
                    all.set(i, entity);
                    break;
                }
            }
        }
        JsonDb.writeList(path, all);
        return entity;
    }

    @Override
    public boolean delete(long id) {
        List<T> all = findAll();
        boolean removed = all.removeIf(e -> Objects.equals(idGetter.apply(e), id));
        if (removed) JsonDb.writeList(path, all);
        return removed;
    }
}
