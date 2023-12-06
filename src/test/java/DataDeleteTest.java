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

public class DataDeleteTest {

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

            String[] trainer = new String[]{"Шкуратов", "Андрей", "Владимирович"};
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
    public void testDeleteInRightOrder() throws Exception {
        sportsmanDao.deleteById(500L);
        String msg = "";
        try {
            sportsmanDao.getById(500L);
        } catch (SQLException e) {
            msg = e.getMessage();
        }
        Assert.assertNotEquals(msg, "");

        sectionDao.deleteById(444L);
        msg = "";
        try {
            sectionDao.getById(444L);
        } catch (SQLException e) {
            msg = e.getMessage();
        }
        Assert.assertNotEquals(msg, "");

        coachDao.deleteById(333L);
        msg = "";
        try {
            coachDao.getById(333L);
        } catch (SQLException e) {
            msg = e.getMessage();
        }
        Assert.assertNotEquals(msg, "");
    }

    @Test
    public void testDeleteInWrongOrder() {
        String msg = "";
        try {
            coachDao.deleteById(333L);
        } catch (Exception e) {
            msg = e.getMessage();
        }
        Assert.assertNotEquals(msg, "");

        msg = "";
        try {
            sectionDao.deleteById(444L);
        } catch (Exception e) {
            msg = e.getMessage();
        }
        Assert.assertNotEquals(msg, "");
    }

}
