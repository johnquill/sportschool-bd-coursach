package model.dao;

import model.entity.Section;

import java.sql.SQLException;
import java.util.List;

//TODO: Может быть, написать ДАО для профессий
public interface Dao<E> {

    void add(E entity) throws Exception;

    E getById(long id) throws SQLException;

    void update(E entity) throws Exception;

    void deleteById(long id);

    Object[][] getAll();

}
