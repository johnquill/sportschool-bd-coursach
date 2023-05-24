package presenter;

import model.Model;
import model.entity.Sportsman;

public class Presenter {

    Model model;

    public Presenter(Model model) {
        this.model = model;
    }

    public String[] getTableHeaders(Class entity) {
        return model.getTableHeaders(entity);
    }

    public Object[][] getTableData(Class entity) {
        return model.getTableData(entity);
    }

    public void addSportsman(Sportsman sportsman) {
        model.addSportsman(sportsman);
    }

    public void editSportsman(Sportsman sportsman) {
        model.editSportsman(sportsman);
    }

    public void deleteSportsmanById(long id) {
        model.deleteSportsmanById(id);
    }
}
