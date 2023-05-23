package model;

import model.dao.SportsmanDaoImpl;
import model.entity.Sportsman;

import java.util.ArrayList;
import java.util.List;

public class Model {

    public String[] getSportsmanHeader() {
        return new String[] {"Ид", "Фамилия",  "Имя", "Отчество", "Секция", "Профессия"};
    }

    public Object[][] getSportsmanData() {
        SportsmanDaoImpl sportsmanDao = new SportsmanDaoImpl();
        List<Sportsman> sportsmen = sportsmanDao.getALl();
        Object[][] data = new Object[sportsmen.size()][getSportsmanHeader().length];
        for (int i = 0; i < sportsmen.size(); i++) {
                data[i][0] = sportsmen.get(i).getId();
                data[i][1] = sportsmen.get(i).getFamily();
                data[i][2] = sportsmen.get(i).getName();
                data[i][3] = sportsmen.get(i).getPatronymic();
                data[i][4] = sportsmen.get(i).getSectionId();
                data[i][5] = sportsmen.get(i).getProfessionId();
        }
        return data;
    }


}
