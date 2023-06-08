package model.dao;

import model.entity.Sport;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SportDaoImpl implements Dao<Sport> {
    Statement statement;

    public SportDaoImpl(Statement statement) {
        this.statement = statement;
    }

    @Override
    public void add(Sport entity) {
    }

    @Override
    public Sport getById(long id) {
        return null;
    }

    @Override
    public void update(Sport entity) {
    }

    @Override
    public void deleteById(long id) throws Exception {
        try {
            statement.executeUpdate("Delete from sport where id="+id);
        } catch (SQLException e) {
            throw new Exception("Ошибка при удалении вида спорта:\n"+e);
        }
    }

    @Override
    public Object[][] getALl() throws Exception {
        ResultSet set;
        try {
            try {
                set = statement.executeQuery("Select id, family, name, patronymic, section_id, sport_id from sportsman");
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            ArrayList<Object[]> sportList = new ArrayList<>();

            int ctr = 0;
            if (set != null) {
                while (set.next()) {

                    sportList.add(new ArrayList<>(Arrays.asList(
                            set.getLong("id"),
                            set.getString("name"),
                            set.getString("inventory")))
                            .toArray());
                }
            }
            return sportList.toArray(Object[][]::new);
        } catch (SQLException e) {
            throw new Exception("Ошибка получения таблицы видов спорта:\n" + e);
        }
    }

    public ArrayList<Sport> getSportsAsList() throws Exception { //TODO:Допилить
        ArrayList<Sport> sportsList = new ArrayList<>();
        try {
            ResultSet set = statement.executeQuery("""
                    Select id, name
                    from sport 
                    """);
            while(set.next()){
                sportsList.add(new Sport(set.getLong("id"), set.getString("name")));
            }
        } catch (SQLException e) {
            throw new Exception("Ошибка получения списка спортсменов: " + e);
        }
        return sportsList;
    }
}
