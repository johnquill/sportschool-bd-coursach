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
            List<Sportsman> sportsmen = sportsmanDao.getALl();
            data = new Object[sportsmen.size()][getTableHeaders(entity).length];
            for (int i = 0; i < sportsmen.size(); i++) {
                data[i][0] = sportsmen.get(i).getId();
                data[i][1] = sportsmen.get(i).getFamily();
                data[i][2] = sportsmen.get(i).getName();
                data[i][3] = sportsmen.get(i).getPatronymic();
                data[i][4] = sportsmen.get(i).getSectionId();
                data[i][5] = sportsmen.get(i).getProfessionId();
            }
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
}
