package model.dao;

import model.entity.Sport;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SportDaoImpl implements Dao<Sport>{
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
    public void deleteById(long id) {

    }

    @Override
    public Object[][] getALl() {
        Connection connection;
        Statement statement;
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/lab6", "root", System.getenv("PASSW"));
            statement = connection.createStatement();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        ResultSet set;
        try {
            set = statement.executeQuery("Select id, family, name, patronymic, section_id, sport_id from sportsman");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        ArrayList<Object[]> sportList = new ArrayList<>();
        try {
            int ctr=0;
            if (set != null)
                while (set.next()){
                    sportList.add(new ArrayList<>(Arrays.asList(
                            set.getLong("id"),
                            set.getString("name"),
                            set.getString("inventory")))
                            .toArray());
                }
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return (Object[][]) sportList.toArray();
    }
}
