import model.dao.CoachDaoImpl;
import model.dao.SectionDaoImpl;
import model.dao.SportsmanDaoImpl;
import model.entity.Coach;
import model.entity.Section;
import model.entity.Sportsman;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public abstract class DataBaseTest {

    SportsmanDaoImpl sportsmanDao;
    SectionDaoImpl sectionDao;
    CoachDaoImpl coachDao;
    Connection connection;
    String[] tablesInDelOrder = {"sportsman", "section", "coach", "preference", "sport", "profession"};

    public void init() throws SQLException {
        connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1/sportschool",
                "root", System.getenv("PASSW"));
        Statement statement = connection.createStatement();
        sportsmanDao = new SportsmanDaoImpl(statement);
        sectionDao = new SectionDaoImpl(statement);
        coachDao = new CoachDaoImpl(statement);
        connection.setAutoCommit(false);

        for (String s : tablesInDelOrder) {
            statement.executeUpdate("DELETE FROM " + s);
        }

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
}
