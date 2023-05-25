package model.dao;

import com.mysql.cj.jdbc.result.ResultSetImpl;
import model.entity.Sportsman;

import java.sql.*;
import java.util.*;

public class SportsmanDaoImpl implements Dao<Sportsman> {

    /*private final HashMap<String, String> headers = new HashMap<>();

    {
        headers.put("id", "Ид");
        headers.put("surname", "Фамилия");
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
    public void update(Sportsman entity) throws Exception {
        Connection connection;
        Statement statement;
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/lab6", "root", System.getenv("PASSW"));
            statement = connection.createStatement();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try {
            ResultSet set;
            set = statement.executeQuery("select id from section where name like '"+entity.getSection()+"'");
            if(set==null)
                throw new Exception("Такой секции не существует");
            Long section_id = set.getLong("id");
            set = statement.executeQuery("select id from profession where name like '"+entity.getProfession()+"'");
            if(set==null)
                statement.executeUpdate("insert into profession values("+entity.getProfession()+")");
            set = statement.executeQuery("select id from profession where name like '"+entity.getProfession()+"'");
            Long profsession_id = set.getLong("id");
            statement.executeUpdate(String.format("update sportsman set " +
                    "surname=%s, " +
                    "name=%s, " +
                    "patronymic=%s," +
                    "section_id=%d," +
                    "profession_id=%d" +
                    "where id=%d", entity.getSurname(), entity.getName(), entity.getPatronymic(), section_id, profsession_id, entity.getId()));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void deleteById(long id) {
        Connection connection;
        Statement statement;
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/lab6", "root", System.getenv("PASSW"));
            statement = connection.createStatement();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try {
            statement.executeUpdate("Delete from sportsman where id="+id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
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
        return (Object[][]) sportsmanList.toArray();
    }

    public void add(Sportsman sportsman) {

    }
}
