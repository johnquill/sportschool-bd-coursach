package model.dao;

import model.entity.Sportsman;

import java.sql.*;
import java.util.*;

public class SportsmanDaoImpl implements Dao<Sportsman> {

    /*private final HashMap<String, String> headers = new HashMap<>();

    {
        headers.put("id", "Ид");
        headers.put("family", "Фамилия");
        headers.put("name", "Имя");
        headers.put("patronymic", "Отчество");
        headers.put("section_id", "Ид секции");
        headers.put("profession_id", "Ид профессии");
    }*/
    @Override
    public Sportsman getById(long id) {
        return null;
    }

    @Override
    public void update(Sportsman entity) {

    }

    @Override
    public void deleteById(long id) {

    }

    @Override
    public Object[][] getALl() {
        Connection connection;
        Statement statement;
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/sportschool", "admin", System.getenv("PASSW"));
            statement = connection.createStatement();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        ResultSet set;
        try {
            set = statement.executeQuery("Select id, surname, name, patronymic, section_id, profession_id from sportsman");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        ArrayList<Object[]> sportsmanList = new ArrayList<>();
        try {
            if (set != null)
                while (set.next()){
                    sportsmanList.add(new ArrayList<>(Arrays.asList(
                            set.getLong("id"),
                            set.getString("surname"),
                            set.getString("name"),
                            set.getString("patronymic"),
                            set.getLong("section_id"),
                            set.getLong("profession_id")))
                            .toArray());
                }
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return sportsmanList.toArray(Object[][]::new);
    }

    public void add(Sportsman sportsman) {

    }
}
