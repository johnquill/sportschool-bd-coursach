package model.dao;

import model.entity.Coach;
import model.entity.Sportsman;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class CoachDaoImpl implements Dao<Coach> {

    Statement statement;

    public CoachDaoImpl(Statement statement) {
        this.statement = statement;
    }

    /*private final HashMap<String, String> headers = new HashMap<>();
        {
            headers.put("id", "Ид");
            headers.put("family", "Фамилия");
            headers.put("name", "Имя");
            headers.put("patronymic", "Отчество");
            headers.put("sport_id", "Ид спорта");
        }*/
    @Override
    public void add(Coach entity) {

    }

    @Override
    public Coach getById(long id) throws Exception {
        Connection connection;
        Statement statement;
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/sportschool", "admin", System.getenv("PASSW"));
            statement = connection.createStatement();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try {
        String sport_name;
        ResultSet set = statement.executeQuery("select * from coach where id=" + id);
        set.next();
        ResultSet set1;
        set1 = statement.executeQuery("select name from sport where name like '" + set.getString("sport_id") + "'");
        set1.next();
        sport_name = set1.getString("name");
        return new Coach(
                set.getLong("id"),
                set.getString("surname"),
                set.getString("name"),
                set.getString("patronymic"),
                sport_name);
        } catch (SQLException e){
            throw new Exception("Ошибка получения по ИД");
        }
    }

    @Override
    public void update(Coach entity) throws Exception {
        Connection connection;
        Statement statement;
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/sportschool", "admin", System.getenv("PASSW"));
            statement = connection.createStatement();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try {
            ResultSet set;
            set = statement.executeQuery("select id from sport where name like '" + entity.getSport() + "'");
            if (!set.isBeforeFirst()) {
                throw new Exception("Такого вида спорта нет в спортивной школе");
            }
            set.next();
            Long sport_id = set.getLong("id");
            statement.executeUpdate(String.format("update coach set " +
                    "surname=%s, " +
                    "name=%s, " +
                    "patronymic=%s," +
                    "sport_id=%d", entity.getSurname(), entity.getName(), entity.getPatronymic(), sport_id));
        } catch (Exception e) {
            throw new Exception("Ошибка при обновлении: " + e);
        }
    }

    @Override
    public void deleteById(long id) throws Exception {
        Connection connection;
        Statement statement;
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/sportschool", "admin", System.getenv("PASSW"));
            statement = connection.createStatement();
        } catch (SQLException e) {
            throw new Exception("Ошибка подключения к бд");
        }
        try {
            statement.executeUpdate("Delete from coach where id=" + id);
        } catch (SQLException e) {
            throw new Exception("Ошибка удаления");
        }
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
            set = statement.executeQuery("Select c.id, surname, c.name, patronymic, sp.name as sport_name from Coach c" +
                    " left join sport sp on sp.id=sport_id");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        ArrayList<Object[]> coachList = new ArrayList<>();
        try {
            if (set.isBeforeFirst())
                while (set.next()){
                    coachList.add(new ArrayList<>(Arrays.asList(
                            set.getLong("id"),
                            set.getString("surname"),
                            set.getString("name"),
                            set.getString("patronymic"),
                            set.getString("sport_name")))
                            .toArray());
                }
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return coachList.toArray(Object[][]::new);
    }

    public String[] getFIO() {
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
            set = statement.executeQuery("Select surname, name, patronymic from coach");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        ArrayList<String> FIO = new ArrayList<>();
        try {
            while (set.next())
                FIO.add(String.format("%s %s %s", set.getString("surname"), set.getString("name"), set.getString("patronymic")));
        } catch (SQLException e){
            throw new RuntimeException(e);
        }
        return FIO.toArray(String[]::new);
    }
}
