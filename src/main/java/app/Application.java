package app;

import model.Model;
import presenter.Presenter;
import view.View;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Application {

    public static void main(String[] args) throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1/sportschool",
                "root", System.getenv("PASSW"));
        Model model = new Model(connection);
        Presenter presenter = new Presenter(model);
        View view = new View(presenter);
    }
}
