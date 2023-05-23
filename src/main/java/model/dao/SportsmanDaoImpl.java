package model.dao;

import model.entity.Sportsman;

import java.util.List;

public class SportsmanDaoImpl implements Dao<Sportsman> {

    @Override
    public Sportsman getById(long id) {
        return null;
    }

    @Override
    public void update(Sportsman entity) {

    }

    @Override
    public void deleteById(long id) {

    }

    @Override
    public List<Sportsman> getALl() {
        Sportsman sportsman = new Sportsman(123456L, "Ходяков", "Федор", "Андреевич", 233345L, 322221L);
        return List.of(sportsman);
    }

    public void add(Sportsman sportsman) {

    }
}
