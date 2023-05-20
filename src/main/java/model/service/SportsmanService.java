package model.service;

import model.dao.SportsmanDaoImpl;
import model.entity.Sportsman;

public class SportsmanService {

    SportsmanDaoImpl sportsmanDao = new SportsmanDaoImpl();

    public Sportsman getById(long id) {
        return sportsmanDao.getById(id);
    }

    public void add(Sportsman sportsman) {
        sportsmanDao.add(sportsman);
    }

}
