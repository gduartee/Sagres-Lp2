package model.repo;

import java.util.List;

public interface Repository<T> {
    List<T> findAll();
    T findById(long id);
    T save(T entity);       // cria/atualiza
    boolean delete(long id);
}
