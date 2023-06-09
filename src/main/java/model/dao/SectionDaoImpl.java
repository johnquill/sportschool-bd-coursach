package model.dao;

import model.entity.Section;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;

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
    public void add(Section entity) throws Exception {
        Connection connection;
        Statement statement;
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/sportschool", "admin", System.getenv("PASSW"));
            statement = connection.createStatement();
            ResultSet set;
            set = statement.executeQuery("select id from sport where name like '" + entity.getSport() + "'");
            if (!set.next()) {
                throw new Exception("Такого вида спорта в спортивной школе нет");
            }
            long sport_id = set.getLong("id");

            set = statement.executeQuery(String.format("select id from coach where surname like '%s' and name like '%s' and patronymic like '%s'",
                    entity.getCoach()[0], entity.getCoach()[1], entity.getCoach()[2]));
            set.next();
            long coach_id = set.getLong("id");
            statement.executeUpdate(String.format("insert into section(schedule, room, description, is_working, sport_id, coach_id) values('%s', %d, '%s', %b, %d, %d)",
                    entity.getSchedule(),
                    entity.getRoom(),
                    entity.getDescription(),
                    entity.getIs_working(),
                    sport_id,
                    coach_id));
        } catch (SQLException e) {
            throw new Exception("Ошибка добавления секции:\n" + e);
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
            set = statement.executeQuery("select id from sport where name like '" + entity.getSport() + "'");
            if (!set.next()) {
                throw new Exception("Такого вида спорта в спортивной школе нет");
            }
            long sport_id = set.getLong("id");

            set = statement.executeQuery(String.format("select id from coach where surname like '%s' and name like '%s' and patronymic like '%s'", entity.getCoach()[0], entity.getCoach()[1], entity.getCoach()[2]));
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
            throw new Exception("Ошибка изменения секции:\n" + e);
        }
    }

    @Override
    public void deleteById(long id) throws Exception {
        Connection connection;
        Statement statement;
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/sportschool", "admin", System.getenv("PASSW"));
            statement = connection.createStatement();
            statement.executeUpdate("delete from section where id=" + id);
        } catch (SQLException e) {
            throw new Exception("Ошибка удаления секции:\n" + e);
        }
    }

    @Override
    public Object[][] getALl() throws Exception {
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
                            set.getString("coach_surname") + " " + set.getString("coach_name") + " " + set.getString("coach_patronymic")))
                            .toArray());
                } while (set.next());
        } catch (SQLException e) {
            throw new Exception("Ошибка получения спика секций:\n" + e);
        }
        return sectionList.toArray(Object[][]::new);
    }

    public String[] getNames() throws Exception {
        try {
            ResultSet set;

            set = statement.executeQuery("select distinct name from section");
            ArrayList<String> sectionNames = new ArrayList<>();
            if (set.next()) {
                sectionNames.add(set.getString("name"));
                while (set.next())
                    sectionNames.add(set.getString("name"));
            }

            sectionNames.add(0, "");
            return sectionNames.toArray(String[]::new);
        } catch (SQLException e) {
            throw new Exception("Ошибка получения имен секций:\n"+e);
        }

    }

    public ArrayList<Section> getActive() throws Exception {
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
            while (set.next()) {
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
            throw new Exception("Ошибка получения активных секций:\n"+e);
        }
        return sectionList;
    }

    public ArrayList<Section> getSectionByTrainerId(Long id) throws Exception {
        try {
            ArrayList<Section> sections = new ArrayList<>();
            ResultSet set = statement.executeQuery("""
                    Select s.id as section_id, s.name as section_name, s.schedule, s.room, s.description as section_description, s.is_working, sp.name as sport_name
                    from section s 
                    join coach c on c.id=coach_id 
                    join sport sp on sp.id=s.sport_id
                    where coach_id=""" + id);
            if (set.isBeforeFirst())
                while (set.next()) {
                    sections.add(new Section(
                            set.getLong("section_id"),
                            set.getString("section_name"),
                            set.getString("schedule"),
                            set.getInt("room"),
                            set.getString("section_description"),
                            set.getBoolean("is_working"),
                            set.getString("sport_name"),
                            new String[]{"", "", ""}));
                }
            return sections;
        } catch (SQLException e) {
            throw new Exception("Ошибка при выборе тренера по id" + id + ":\n" + e);
        }
    }

    public ArrayList<Section> getSectionsAsList() throws Exception {
        try {
            ArrayList<Section> sectionsList = new ArrayList<>();
            ResultSet set = statement.executeQuery("""
                    Select s.id, s.name, schedule, room, description, is_working, sp.name as sport_name,
                    c.surname as coach_surname, c.name as coach_name, c.patronymic as coach_patronymic
                    from section s
                    join sport sp on sport_id=sp.id
                    join coach c on coach_id=c.id
                    """);
            while (set.next()) {
                sectionsList.add(new Section(
                        set.getLong("id"),
                        set.getString("name"),
                        set.getString("schedule"),
                        set.getInt("room"),
                        set.getString("description"),
                        set.getBoolean("is_working"),
                        set.getString("sport_name"),
                        new String[]{set.getString("coach_surname"), set.getString("coach_name"), set.getString("coach_patronymic")}
                ));
            }
            return sectionsList;
        } catch (SQLException e) {
            throw new Exception("Ошибка при получении списка секций: \n" + e);
        }
    }

    public ArrayList<Section> getSectionBySportName(String sportName) throws Exception {
        ArrayList<Section> sectionsList = new ArrayList<>();
        try {
            ResultSet set = statement.executeQuery(String.format("""
                    Select s.id, s.name, schedule, room, description, is_working, sp.name as sport_name,
                    c.surname as coach_surname, c.name as coach_name, c.patronymic as coach_patronymic
                    from section s
                    join sport sp on sport_id=sp.id
                    join coach c on coach_id=c.id
                    where sp.name like '%s'""", sportName));
            while (set.next()) {
                sectionsList.add(new Section(
                        set.getLong("id"),
                        set.getString("name"),
                        set.getString("schedule"),
                        set.getInt("room"),
                        set.getString("description"),
                        set.getBoolean("is_working"),
                        set.getString("sport_name"),
                        new String[]{set.getString("coach_surname"), set.getString("coach_name"), set.getString("coach_patronymic")}
                ));
            }
        } catch (SQLException e) {
            throw new Exception("Ошибка получения списка секций по виду спорта:\n" + e);
        }
        return sectionsList;
    }
}
