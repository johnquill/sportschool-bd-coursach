package app;

import model.Model;
import presenter.Presenter;
import view.View;

public class Application {

    public static void main(String[] args) {
        Model model = new Model();
        Presenter presenter = new Presenter(model);
        View view = new View(presenter);
    }
}
