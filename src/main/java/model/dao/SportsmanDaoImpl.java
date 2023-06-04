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
    public Sportsman getById(long id) throws SQLException {
        Connection connection;
        Statement statement;
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/sportschool", "admin", System.getenv("PASSW"));
            statement = connection.createStatement();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        String section_name, profession_name;
        ResultSet set = statement.executeQuery("select * from sportsman where id="+id);
        set.next();
        ResultSet set1;
        set1 = statement.executeQuery("select name from section where name like '"+set.getString("section_id")+"'");
        set1.next(); section_name = set1.getString("name");
        set1 = statement.executeQuery("select name from profession where name like '"+set.getString("profession_id")+"'");
        set1.next(); profession_name = set1.getString("name");
        return new Sportsman(
                set.getLong("id"),
                set.getString("surname"),
                set.getString("name"),
                set.getString("patronymic"),
                section_name, profession_name);
    }

    @Override
    public void update(Sportsman entity) throws Exception {
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
            set = statement.executeQuery("select id from section where name like '" + entity.getSection() + "'");
            if (!set.isBeforeFirst())
                throw new Exception("Такой секции не существует");
            set.next();
            Long section_id = set.getLong("id");
            set = statement.executeQuery("select id from profession where name like '" + entity.getProfession() + "'");
            if (!set.isBeforeFirst()) {
                statement.executeUpdate("insert into profession values(" + entity.getProfession() + ")");
                set = statement.executeQuery("select id from profession where name like '" + entity.getProfession() + "'");
            }
            set.next();
            Long profsession_id = set.getLong("id");
            statement.executeUpdate(String.format("update sportsman set " +
                    "surname=%s, " +
                    "name=%s, " +
                    "patronymic=%s," +
                    "section_id=%d," +
                    "profession_id=%d" +
                    "where id=%d", entity.getSurname(), entity.getName(), entity.getPatronymic(), section_id, profsession_id, entity.getId()));
        } catch (Exception e) {
            throw new Exception("Ошибка при обновлении: " + e);
        }

    }

    @Override
    public void deleteById(long id) {
        Connection connection;
        Statement statement;
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/sportschool", "admin", System.getenv("PASSW"));
            statement = connection.createStatement();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try {
            statement.executeUpdate("Delete from sportsman where id=" + id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
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
            set = statement.executeQuery("Select sp.id, surname, sp.name, patronymic, s.name as section_name, p.name as profession_name from sportsman sp" +
                    " left join section s on section_id=s.id" +
                    " left join profession p on profession_id=p.id");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        ArrayList<Object[]> sportsmanList = new ArrayList<>();
        try {
            if (set.isBeforeFirst())
                while (set.next()) {
                    sportsmanList.add(new ArrayList<>(Arrays.asList(
                            set.getLong("id"),
                            set.getString("surname"),
                            set.getString("name"),
                            set.getString("patronymic"),
                            set.getString("section_name"),
                            set.getString("profession_name")))
                            .toArray());
                }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return sportsmanList.toArray(Object[][]::new);
    }

    public void add(Sportsman sportsman) throws Exception {
        Connection connection;
        Statement statement;
        PreparedStatement selectStatement;
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/sportschool", "admin", System.getenv("PASSW"));
            statement = connection.createStatement();
            selectStatement = connection.prepareStatement("select id from profession where name like '" + sportsman.getProfession() + "'");
        } catch (SQLException e) {
            throw new Exception("Такой порофессии нет епта");
        }
        try {
            ResultSet set;

            set = statement.executeQuery("select id from section where name like '" + sportsman.getSection() + "'");
            if (!set.isBeforeFirst())
                throw new Exception("Такой секции не существует");

            set.next();
            Long section_id = set.getLong("id");
            set = selectStatement.executeQuery();
            if (!set.isBeforeFirst()) {
                statement.executeUpdate("insert into profession (name) values('" + sportsman.getProfession() + "')");
                set = selectStatement.executeQuery();
            }
            set.next();
            Long profession_id = set.getLong("id");
            statement.executeUpdate(String.format("insert into sportsman(surname, name, patronymic, section_id, profession_id) values('%s', '%s', '%s', %d, %d)",
                    sportsman.getSurname(),
                    sportsman.getName(),
                    sportsman.getPatronymic(),
                    section_id,
                    profession_id));
        } catch (SQLException e) {
            throw new Exception("Ошибка добавления спортсмена:\n" + e);
        }

    }
}
