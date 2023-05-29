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
            return new String[] {"Ид", "График", "Зал", "Описание", "Статус работы", "Спорт", "Тренер"};
        } else if (Coach.class.equals(entity)) {
            return new String[] {"Ид", "Фамилия", "Имя", "Отчество", "Вид спорта"};
        }
        return new String[] {};
    }
    //TODO:Исключения вставки(такой секции нет и т.д.) выкидвать максимально выского, где-то в презентере или типа того, где уже просто выводится сообщение
    public void addSportsman(Sportsman sportsman) throws Exception {
        SportsmanDaoImpl sportsmanDao = new SportsmanDaoImpl();
        sportsmanDao.add(sportsman);
    }

    public void deleteSportsmanById(long id){
        SportsmanDaoImpl sportsmanDao = new SportsmanDaoImpl();
        sportsmanDao.deleteById(id);
    }

    public void editSportsman(Sportsman sportsman){
        SportsmanDaoImpl sportsmanDao = new SportsmanDaoImpl();
        try {
            sportsmanDao.update(sportsman);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String[] getSectionNames() {
        SectionDaoImpl sectionDao = new SectionDaoImpl();
        return sectionDao.getNames();
    }

    public void addSection(Section section){
        SectionDaoImpl sectionDao = new SectionDaoImpl();
        try {
            sectionDao.add(section);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void editSection(Section section) {
        SectionDaoImpl sectionDao = new SectionDaoImpl();
        try {
            sectionDao.update(section);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteSectionById(long id){
        SectionDaoImpl sectionDao = new SectionDaoImpl();
        sectionDao.deleteById(id);
    }
}
