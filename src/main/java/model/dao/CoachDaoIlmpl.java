package model.dao;

import model.entity.Coach;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class CoachDaoIlmpl implements Dao<Coach> {

    private final HashMap<String, String> headers = new HashMap<>();
    {
        headers.put("id", "Ид");
        headers.put("family", "Фамилия");
        headers.put("name", "Имя");
        headers.put("patronymic", "Отчество");
        headers.put("sport_id", "Ид спорта");
    }
    @Override
    public void add(Coach entity) {

    }

    @Override
    public Coach getById(long id) {
        return null;
    }

    @Override
    public void update(Coach entity) {

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
        ArrayList<Object[]> coachList = new ArrayList<>();
        try {
            int ctr=0;
            if (set != null)
                while (set.next()){
                    coachList.add(new ArrayList<>(Arrays.asList(
                            set.getLong("id"),
                            set.getString("family"),
                            set.getString("name"),
                            set.getBoolean("patronymic"),
                            set.getLong("sport_id")))
                            .toArray());
                }
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return (Object[][]) coachList.toArray();
    }
}
