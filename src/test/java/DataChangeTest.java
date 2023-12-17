import model.dao.CoachDaoImpl;
import model.dao.SectionDaoImpl;
import model.dao.SportsmanDaoImpl;
import model.entity.Coach;
import model.entity.Section;
import model.entity.Sportsman;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DataChangeTest {

    SportsmanDaoImpl sportsmanDao;
    SectionDaoImpl sectionDao;
    CoachDaoImpl coachDao;
    Connection connection;

    @Before
    public void start() throws SQLException {
        connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1/sportschool",
                "root", System.getenv("PASSW"));
        Statement statement = connection.createStatement();
        sportsmanDao = new SportsmanDaoImpl(statement);
        sectionDao = new SectionDaoImpl(statement);
        coachDao = new CoachDaoImpl(statement);
        connection.setAutoCommit(false);

        try {
            Coach coach = new Coach(333L, "Шкуратов", "Андрей", "Владимирович", "Баскетбол");
            coachDao.add(coach);

            String[] trainer = new String[] {"Шкуратов", "Андрей", "Владимирович"};
            Section section = new Section(444L, "Баскетбол", "Сб 20:00", 221,
                    "С собой водичку", true, "Баскетбол", trainer);
            sectionDao.add(section);

            Sportsman sportsman = new Sportsman(500L, "Ходяков", "Федор", "Андреевич", "Баскетбол", "Программист");
            sportsmanDao.add(sportsman);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testChangeSportsman() throws Exception {
        sportsmanDao.update(new Sportsman(500L, "Ходяков", "Фёдор", "Андреевич",
                "Баскетбол", "Кассир во вкусно и точка"));
        Sportsman sportsman = sportsmanDao.getById(500L);
        Assert.assertTrue(sportsman.getName().equals("Фёдор") && sportsman.getProfession().startsWith("Кассир"));
    }

    @Test
    public void testChangeCoach() throws Exception {
        coachDao.update(new Coach(333L, "Леброн", "Джеймс", "Батькович", "Баскет"));
        Coach coach = coachDao.getById(333L);
        Assert.assertTrue(coach.getFio().equals("Леброн Джеймс Батькович") && coach.getSport().equals("Баскет"));
    }

    @Test
    public void testChangeSection() throws Exception {
        String[] trainer = new String[] {"Шкуратов", "Андрей", "Владимирович"};
        sectionDao.update(new Section(444L, "Баскетбол ногой", "3 утра", 0, "",
                false, "Баскетбол", trainer));
        Section section = sectionDao.getById(444L);
        Assert.assertTrue(section.getName().endsWith("ногой") && section.getSchedule().equals("3 утра")
                && !section.getIsWorking() && section.getRoom() == 0 && section.getDescription().isEmpty());
    }

}
