package app;

import model.Model;
import presenter.Presenter;
import view.Frame;

public class Application {

    public static void main(String[] args) {
        Frame frame = new Frame();
        Model model = new Model();
        new Presenter(frame, model);
    }
}
