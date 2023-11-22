package model.dao;

import model.entity.Coach;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;

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
    public void add(Coach entity) throws Exception {
        Long sport_id = getSportId(entity);
        boolean hasId = entity.getId() != 0;
        statement.executeUpdate(String.format(
                "Insert into coach(" + (hasId ? "id, " : "") + "surname, name, patronymic, sport_id) " +
                        "values(" + (hasId ? entity.getId() + ", " : "") + "'%s', '%s', '%s', %d)",
                entity.getSurname(),
                entity.getName(),
                entity.getPatronymic(),
                sport_id));
    }

    private long getSportId(Coach coach) throws SQLException {
        ResultSet set = statement.executeQuery("select id from sport where name like '" + coach.getSport() + "'");
        if (!set.isBeforeFirst()) {
            statement.executeUpdate(String.format("Insert into sport(name) values(%s)", coach.getSport()));
            set = statement.executeQuery("select id from sport where name like '" + coach.getSport() + "'");
        }
        set.next();
        return set.getLong("id");
    }

    @Override
    public void update(Coach entity) throws Exception {
        Long sport_id = getSportId(entity);
        statement.executeUpdate(String.format(
                "update coach set surname='%s', name='%s', patronymic='%s', sport_id=%d where id=%d",
                entity.getSurname(),
                entity.getName(),
                entity.getPatronymic(),
                sport_id,
                entity.getId()));
    }

    @Override
    public Coach getById(long id) throws Exception {
        String sport_name;
        ResultSet set = statement.executeQuery("select * from coach where id=" + id);
        set.next();
        Coach coach = new Coach();
        coach.setId(set.getLong("id"));
        coach.setSurname(set.getString("surname"));
        coach.setName(set.getString("name"));
        coach.setPatronymic(set.getString("patronymic"));
        set = statement.executeQuery("select name from sport where id=" + set.getString("sport_id"));
        set.next();
        sport_name = set.getString("name");
        coach.setSport(sport_name);
        return coach;
    }

    @Override
    public void deleteById(long id) throws Exception {
        ResultSet set = statement.executeQuery("Select section.id from section where coach_id=" + id);
        if (set.isBeforeFirst()) {
            throw new Exception("Нельзя удалить тренера, который ведёт секцию");
        }
        try {
            statement.executeUpdate("Delete from coach where id=" + id);
        } catch (SQLException e) {
            throw new Exception("Ошибка удаления" + e);
        }
    }

    @Override
    public Object[][] getALl() {
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
                while (set.next()) {
                    coachList.add(new ArrayList<>(Arrays.asList(
                            set.getLong("id"),
                            set.getString("surname"),
                            set.getString("name"),
                            set.getString("patronymic"),
                            set.getString("sport_name")))
                            .toArray());
                }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return coachList.toArray(Object[][]::new);
    }

    public String[] getFIO() {
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
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return FIO.toArray(String[]::new);
    }

    public ArrayList<Coach> getCoaches() {
        ArrayList<Coach> coaches = new ArrayList<>();
        try {
            ResultSet set = statement.executeQuery("""
                    Select c.id as coach_id, c.surname as coach_surname, c.name as coach_name, c.patronymic as coach_patronymic, sp.name as sport_name
                    from coach c
                    join sport sp on sp.id=sport_id
                    """);
            while (set.next()) {
                coaches.add(new Coach(
                        set.getLong("coach_id"),
                        set.getString("coach_surname"),
                        set.getString("coach_name"),
                        set.getString("coach_patronymic"),
                        set.getString("sport_name")
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return coaches;
    }
}
