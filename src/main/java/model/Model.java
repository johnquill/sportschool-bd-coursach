package model;

import model.dao.CoachDaoIlmpl;
import model.dao.SectionDaoImpl;
import model.dao.SportsmanDaoImpl;
import model.entity.Coach;
import model.entity.Section;
import model.entity.Sportsman;


public class Model {


    //TODO: допиливать и оптимизировать
    public Object[][] getTableData(Class entity) {
        Object[][] data = new Object[0][];
        if (Sportsman.class.equals(entity)) {
            SportsmanDaoImpl sportsmanDao = new SportsmanDaoImpl();
            data = sportsmanDao.getALl();
        } else if (Section.class.equals(entity)) {
            SectionDaoImpl sectionDao = new SectionDaoImpl();
            data = sectionDao.getALl();
        } else if (Coach.class.equals(entity)) {
            CoachDaoIlmpl coachDao = new CoachDaoIlmpl();
            data = coachDao.getALl();
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
        SportsmanDaoImpl sportsmanDao = new SportsmanDaoImpl();
        try {
            sportsmanDao.add(sportsman);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public void deleteSportsmanById(long id){
        SportsmanDaoImpl sportsmanDao = new SportsmanDaoImpl();
        sportsmanDao.deleteById(id);
    }
}
