package presenter;

import model.Model;

public class Presenter {

    Model model;

    public Presenter(Model model) {
        this.model = model;
    }

    public String[] getSportsmanHeaders() {
        return model.getSportsmanHeader();
    }

    public Object[][] getSportsmanData() {
        return model.getSportsmanData();
    }
}
