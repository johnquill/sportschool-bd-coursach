package model.service;

import model.dao.SportsmanDaoImpl;
import model.entity.Sportsman;

import java.sql.SQLException;

public class SportsmanService {

    SportsmanDaoImpl sportsmanDao = new SportsmanDaoImpl();

    public Sportsman getById(long id) {
        try {
            return sportsmanDao.getById(id);//TODO:здесь должно ловиться исключение или как-то иначе будет?
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void add(Sportsman sportsman) {
        //sportsmanDao.add(sportsman);
    }

}
