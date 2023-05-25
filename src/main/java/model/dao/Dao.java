package model.dao;

import model.entity.Section;

import java.util.List;

//TODO: Может быть, написать ДАО для профессий
public interface Dao<E> {

    void add(E entity);

    E getById(long id);

    void update(E entity) throws Exception;

    void deleteById(long id);

    Object[][] getALl();

}
