package model.dao;

import java.util.List;

public interface Dao<E> {

    void add(E entity);

    E getById(long id);

    void update(E entity);

    void delete(E entity);

    List<E> getALl();

}
