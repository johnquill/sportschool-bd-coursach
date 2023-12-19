package model;

import model.dao.CoachDaoImpl;
import model.dao.SectionDaoImpl;
import model.dao.SportDaoImpl;
import model.dao.SportsmanDaoImpl;
import model.entity.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;


public class Model {
    
    Statement statement;
    SportsmanDaoImpl sportsmanDao;
    SectionDaoImpl sectionDao;
    CoachDaoImpl coachDao;
    SportDaoImpl sportDao;
    
    public Model(Connection connection) {
        try {
            statement = connection.createStatement();
            sportsmanDao = new SportsmanDaoImpl(statement);
            sectionDao = new SectionDaoImpl(statement);
            coachDao = new CoachDaoImpl(statement);
            sportDao = new SportDaoImpl(statement);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // TODO: допиливать и оптимизировать
    public Object[][] getTableData(Class<Entity> entity) throws Exception {
        Object[][] data = new Object[0][];
        if (Sportsman.class.equals(entity)) {
            SportsmanDaoImpl sportsmanDao = new SportsmanDaoImpl(statement);
            data = sportsmanDao.getALl();
        } else if (Section.class.equals(entity)) {
            SectionDaoImpl sectionDao = new SectionDaoImpl(statement);
            data = sectionDao.getALl();
        } else if (Coach.class.equals(entity)) {
            CoachDaoImpl coachDao = new CoachDaoImpl(statement);
            data = coachDao.getALl();
        }
        return data;
    }

    // TODO: допиливать и оптимизировать
    public String[] getTableHeaders(Class<Entity> entity) {
        if (Sportsman.class.equals(entity)) {
            return new String[] {"Ид", "Фамилия", "Имя", "Отчество", "Секция", "Профессия"};
        } else if (Section.class.equals(entity)) {
            return new String[] {"Ид", "Название", "График", "Зал", "Описание", "Статус работы", "Спорт", "Тренер"};
        } else if (Coach.class.equals(entity)) {
            return new String[] {"Ид", "Фамилия", "Имя", "Отчество", "Вид спорта"};
        }
        return new String[] {};
    }

    public void addSportsman(Sportsman sportsman) throws Exception {
        sportsmanDao.add(sportsman);
    }

    public void deleteSportsmanById(long id){
        sportsmanDao.deleteById(id);
    }

    public void editSportsman(Sportsman sportsman) throws Exception {
        sportsmanDao.update(sportsman);
    }

    public String[] getSectionNames() throws Exception {
        return sectionDao.getNames();
    }

    public void addSection(Section section) throws Exception {
            sectionDao.add(section);
    }

    public void editSection(Section section) throws Exception {
        sectionDao.update(section);
    }

    public void deleteSectionById(long id) throws Exception {
        sectionDao.deleteById(id);
    }

    public String[] getTrainers() {
        return coachDao.getFIO();
    }

    public void updateSection(Section section) throws Exception {
        sectionDao.update(section);
    }

    public void addCoach(Coach coach) throws Exception {
        coachDao.add(coach);
    }

    public void updateCoach(Coach coach) throws Exception {
        coachDao.update(coach);
    }

    public ArrayList<Section> getActiveSections() throws Exception {
        return sectionDao.getActive();
    }

    public ArrayList<Coach> getCoaches() {
        return coachDao.getCoaches();
    }

    public ArrayList<Section> getSectionByTrainerId(long id) throws Exception {
        return sectionDao.getSectionByTrainerId(id);
    }

    public ArrayList<Section> getSections() throws Exception {
        return sectionDao.getSectionsAsList();
    }

    public ArrayList<Sportsman> getSportsmenBySectionId(long id) throws Exception {
        return sportsmanDao.getSportsmenBySectionId(id);
    }

    public ArrayList<Sport> getSports() throws Exception {
        return sportDao.getSportsAsList();
    }

    public ArrayList<Section> getSectionsBySport(String sportName) throws Exception {
        return sectionDao.getSectionBySportName(sportName);
    }

    public HashMap<String, Integer> getSportsmenOfSportsCount() throws Exception {
        return sportsmanDao.getSportsmenOfSportsCount();
    }

    public void deleteCoachById(long id) throws Exception {
        coachDao.deleteById(id);
    }
}
