import model.entity.Coach;
import model.entity.Section;
import model.entity.Sportsman;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;

public class DataChangeTest extends DataBaseTest {
    @Before
    public void start() throws SQLException {
        init();
    }

    @After
    public void finish() throws SQLException {
        connection.rollback();
        connection.close();
    }

    @Test
    public void testChangeSportsman() throws Exception {
        sportsmanDao.update(new Sportsman(500L, "Ходяков", "Фёдор", "Андреевич",
                "Баскетбол", "Кассир в маке"));
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
