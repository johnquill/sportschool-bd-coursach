package model.dao;

import model.entity.Section;

import java.sql.SQLException;
import java.util.List;

//TODO: Может быть, написать ДАО для профессий
public interface Dao<E> {

    //TODO: удалить у всех сущностей создание connection.

    //TODO: для исключений сделать передачу сообщений (сложить сообщение от программы со своим сообщением)
    //catch(SqlException e) {throw new Exception("Ошибка при получении профессий\n" + e.getMessage())}

    void add(E entity) throws Exception;

    E getById(long id) throws Exception;

    void update(E entity) throws Exception;

    void deleteById(long id) throws Exception;

    Object[][] getALl() throws Exception;

}
