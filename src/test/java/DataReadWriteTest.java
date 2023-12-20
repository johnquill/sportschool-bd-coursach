import org.junit.*;

import java.sql.SQLException;

public class DataReadWriteTest extends DataBaseTest {

    @Test
    public void testAddCoach() throws Exception {
        Assert.assertEquals("Шкуратов", coachDao.getById(333L).getSurname());
    }

    @Test
    public void testAddSection() throws Exception {
        Assert.assertEquals("Баскетбол", sectionDao.getById(444L).getName());
    }

    @Test
    public void testAddSportsman() throws Exception {
        Assert.assertEquals("Ходяков", sportsmanDao.getById(500L).getSurname());
    }
}
