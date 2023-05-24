package model.dao;

import model.entity.Section;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class SectionDaoImpl implements Dao<Section>{

    private final HashMap<String, String> headers = new HashMap<>();
    {
        headers.put("id", "Ид");
        headers.put("schedule", "График");
        headers.put("room", "Зал");
        headers.put("description", "Описание");
        headers.put("sport_id", "Ид спорта");
        headers.put("coach_id", "Ид тренера");
    }

    @Override
    public void add(Section entity) {

    }

    @Override
    public Section getById(long id) {
        return null;
    }

    @Override
    public void update(Section entity) {

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
        ArrayList<Object[]> sectionList = new ArrayList<>();
        try {
            int ctr=0;
            if (set != null)
                while (set.next()){
                    sectionList.add(new ArrayList<>(Arrays.asList(
                            set.getLong("id"),
                            set.getString("schedule"),
                            set.getInt("room"),
                            set.getString("description"),
                            set.getBoolean("is_working"),
                            set.getLong("sport_id"),
                            set.getLong("coach_id")))
                            .toArray());
                }
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return (Object[][]) sectionList.toArray();
    }
}
