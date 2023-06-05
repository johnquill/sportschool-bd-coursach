package model.dao;

import model.entity.Section;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SectionDaoImpl implements Dao<Section> {

    Statement statement;

    public SectionDaoImpl(Statement statement) {
        this.statement = statement;
    }

    /*private final HashMap<String, String> headers = new HashMap<>();
    {
        headers.put("id", "Ид");
        headers.put("schedule", "График");
        headers.put("room", "Зал");
        headers.put("description", "Описание");
        headers.put("sport_id", "Ид спорта");
        headers.put("coach_id", "Ид тренера");
    }*/

    @Override
    public void add(Section entity) throws Exception{
        Connection connection;
        Statement statement;
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/sportschool", "admin", System.getenv("PASSW"));
            statement = connection.createStatement();
            ResultSet set;
            set = statement.executeQuery("select id from sport where name like '"+entity.getSport()+"'");
            if (!set.next()){
                throw new Exception("Такого вида спорта в спортивной школе нет");
            }
            long sport_id = set.getLong("id");

            set = statement.executeQuery(String.format("select id from coach where surname like '%s' and name like '%s' and patronymic like '%s'",
                    entity.getCoach()[0], entity.getCoach()[1], entity.getCoach()[2]));
            if (!set.next())
                throw new Exception("Такого тренера в спортивной школе нет");
            long coach_id = set.getLong("id");
            statement.executeUpdate(String.format("insert into section(schedule, room, description, is_working, sport_id, coach_id) values(%s, %d, %s, %b, %d, %d)",
                    entity.getSchedule(),
                    entity.getRoom(),
                    entity.getDescription(),
                    entity.getIs_working(),
                    sport_id,
                    coach_id));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public Section getById(long id) {
        return null;
    }

    @Override
    public void update(Section entity) throws Exception {
        Connection connection;
        Statement statement;
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/sportschool", "admin", System.getenv("PASSW"));
            statement = connection.createStatement();
            ResultSet set;
            set = statement.executeQuery("select id from sport where name like '"+entity.getSport()+"'");
            if (!set.next()){
                throw new Exception("Такого вида спорта в спортивной школе нет");
            }
            long sport_id = set.getLong("id");

            set = statement.executeQuery(String.format("select id from coach where surname like '%s' and name like '%s' and patronymic like '%s'",entity.getCoach()[0], entity.getCoach()[1], entity.getCoach()[2]));
            if (!set.next())
                throw new Exception("Такого тренера в спортивной школе нет");
            long coach_id = set.getLong("id");
            statement.executeUpdate(String.format("update section set schedule='%s', room=%d, description='%s', is_working=%b, sport_id=%d, coach_id=%d where id=%d",
                    entity.getSchedule(),
                    entity.getRoom(),
                    entity.getDescription(),
                    entity.getIs_working(),
                    sport_id,
                    coach_id,
                    entity.getId()));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteById(long id) {
        Connection connection;
        Statement statement;
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/sportschool", "admin", System.getenv("PASSW"));
            statement = connection.createStatement();
            statement.executeUpdate("delete from section where id=" + id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Object[][] getALl() {
        Connection connection;
        Statement statement;
        ArrayList<Object[]> sectionList = new ArrayList<>();
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/sportschool", "admin", System.getenv("PASSW"));
            statement = connection.createStatement();
            ResultSet set;
            set = statement.executeQuery("Select section.id, section.name, schedule, room, description, is_working, sp.name as sport_name, " +
                    "c.surname as coach_surname, c.name as coach_name, c.patronymic as coach_patronymic from section" +
                    " join sport sp on sport_id=sp.id" +
                    " join coach c on coach_id=c.id");
            if (set.next())
                do {
                    sectionList.add(new ArrayList<>(Arrays.asList(
                            set.getLong("id"),
                            set.getString("name"),
                            set.getString("schedule"),
                            set.getInt("room"),
                            set.getString("description"),
                            set.getBoolean("is_working") ? "Работает" : "Не работает",
                            set.getString("sport_name"),
                            set.getString("coach_surname")+" "+set.getString("coach_name")+" "+set.getString("coach_patronymic")))
                            .toArray());
                } while (set.next());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return sectionList.toArray(Object[][]::new);
    }

    public String[] getNames() {
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
            set = statement.executeQuery("select distinct name from section");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        ArrayList<String> sectionNames = new ArrayList<>();
        try {
            if (set.next()) {
                sectionNames.add(set.getString("name"));
                while (set.next())
                    sectionNames.add(set.getString("name"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        sectionNames.add(0, "");
        return sectionNames.toArray(String[]::new);
    }

    public ArrayList<Section> getActive() {
        Connection connection;
        Statement statement;
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/sportschool", "admin", System.getenv("PASSW"));
            statement = connection.createStatement();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        ArrayList<Section> sectionList = new ArrayList<>();
        try {
            ResultSet set = statement.executeQuery("Select s.id, s.name, schedule, room, description, sp.name as sport_name, c.surname as coach_surname, c.name as coach_name, c.patronymic as coach_patronymic" +
                    " from section s" +
                    " join sport sp on sp.id=sport_id" +
                    " join coach c on c.id=coach_id" +
                    " where is_working = true");
            while (set.next()){
                String[] arrCoach = new String[]{set.getString("coach_surname"), set.getString("coach_name"), set.getString("coach_patronymic")};
                sectionList.add(new Section(
                        set.getLong("id"),
                        set.getString("name"),
                        set.getString("schedule"),
                        set.getInt("room"),
                        set.getString("description"),
                        true,
                        set.getString("sport_name"),
                        arrCoach));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return sectionList;
    }
}
