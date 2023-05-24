package model;

import model.dao.SportsmanDaoImpl;
import model.entity.Coach;
import model.entity.Section;
import model.entity.Sportsman;

import java.util.List;

public class Model {


    //TODO: допиливать и оптимизировать
    public Object[][] getTableData(Class entity) {
        Object[][] data = new Object[0][];
        if (Sportsman.class.equals(entity)) {
            SportsmanDaoImpl sportsmanDao = new SportsmanDaoImpl();
            data = sportsmanDao.getALl();
        } else if (Section.class.equals(entity)) {

        } else if (Coach.class.equals(entity)) {

        }
        return data;
    }

    //TODO: допиливать и оптимизировать
    public String[] getTableHeaders(Class entity) {
        if (Sportsman.class.equals(entity)) {
            return new String[] {"Ид", "Фамилия", "Имя", "Отчество", "Секция", "Профессия"};
        } else if (Section.class.equals(entity)) {
            return new String[] {"Ид"};
        } else if (Coach.class.equals(entity)) {
            return new String[] {"Ид"};
        }
        return new String[] {};
    }

    public void addSportsman(Sportsman sportsman) {

    }
}
