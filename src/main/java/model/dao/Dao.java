package model.dao;

public interface Dao<E> {
    void add(E entity) throws Exception;

    E getById(long id) throws Exception;

    void update(E entity) throws Exception;

    void deleteById(long id) throws Exception;

    Object[][] getALl() throws Exception;

}
