import org.junit.Assert;
import org.junit.Test;

import java.sql.SQLException;

public class DataDeleteTest extends DataBaseTest {

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
