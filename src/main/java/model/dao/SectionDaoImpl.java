package model.dao;

import model.entity.Coach;
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
        if (entity.getId() == 0) {
            createOrUpdate(entity, "insert into section (name, schedule, room, description, is_working, " +
                    "sport_id, coach_id) values ('%s', '%s', %d, '%s', %b, %d, %d)");
        } else {
            createOrUpdate(entity, "insert into section (id, name, schedule, room, description, is_working, " +
                    "sport_id, coach_id) values ("+ entity.getId() + ", '%s', '%s', %d, '%s', %b, %d, %d)");
        }
    }

    private void createOrUpdate(Section entity, String sqlCommand) throws Exception {
        ResultSet set = statement.executeQuery("select id from sport where name like '" + entity.getSport() + "'");
        if (!set.isBeforeFirst()) {
            statement.executeUpdate(String.format("Insert into sport (name) values('%s')", entity.getSport()));
            set = statement.executeQuery("select id from sport where name like '" + entity.getSport() + "'");
        }
        set.next();
        long sport_id = set.getLong("id");

        set = statement.executeQuery(String.format("select id from coach where surname like '%s' and name like '%s' and patronymic like '%s'",
                entity.getCoach()[0], entity.getCoach()[1], entity.getCoach()[2]));
        if (set.next()) {
            long coach_id = set.getLong("id");
            statement.executeUpdate(String.format(sqlCommand,
                    entity.getName(),
                    entity.getSchedule(),
                    entity.getRoom(),
                    entity.getDescription(),
                    entity.getIsWorking(),
                    sport_id,
                    coach_id,
                    entity.getId()));
        } else {
            throw new Exception("Заданный тренер не найден");
        }
    }

    @Override
    public Section getById(long id) throws Exception {
        ResultSet set = statement.executeQuery("select * from section where id=" + id);
        set.next();
        Section section = new Section();
        section.setId(set.getLong("id"));
        section.setName(set.getString("name"));
        section.setRoom(set.getInt("room"));
        section.setSchedule(set.getString("schedule"));
        section.setIsWorking(set.getBoolean("is_working"));
        section.setDescription(set.getString("description"));
        long coachId = set.getLong("coach_id");
        long sportId = set.getLong("sport_id");

        set = statement.executeQuery("select name from sport where id=" + set.getString("sport_id"));
        set.next();
        CoachDaoImpl coachDao = new CoachDaoImpl(statement);

        Coach coach = coachDao.getById(coachId);
        section.setCoach(new String[] {coach.getSurname(), coach.getName(), coach.getPatronymic()});
        set = statement.executeQuery("select name from sport where id=" + sportId);
        set.next();
        section.setSport(set.getString("name"));
        return section;
    }

    @Override
    public void update(Section entity) throws Exception {
        createOrUpdate(entity, "update section set name='%s', schedule='%s', room=%d, description='%s', " +
                "is_working=%b, sport_id=%d, coach_id=%d where id=%d");
    }

    @Override
    public void deleteById(long id) throws Exception {
        ResultSet set = statement.executeQuery("Select surname from sportsman where section_id=" + id);
        if (set.isBeforeFirst())
            throw new Exception("Нельзя удалить непустую секцию");
        try {
            statement.executeUpdate("delete from section where id=" + id);
        } catch (SQLException e) {
            throw new Exception("Ошибка удаления секции:\n" + e);
        }
    }

    @Override
    public Object[][] getALl() throws Exception {
        ArrayList<Object[]> sectionList = new ArrayList<>();
        try {
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

            set = statement.executeQuery("select name from section");
            ArrayList<String> sectionNames = new ArrayList<>();
            if (set.next()) {
                sectionNames.add(set.getString("name"));
                while (set.next())
                    sectionNames.add(set.getString("name"));
            }

            sectionNames.add(0, "");
            return sectionNames.toArray(String[]::new);
        } catch (SQLException e) {
            throw new Exception("Ошибка получения имен секций:\n" + e);
        }

    }

    public ArrayList<Section> getActive() throws Exception {
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
            throw new Exception("Ошибка получения активных секций:\n" + e);
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
