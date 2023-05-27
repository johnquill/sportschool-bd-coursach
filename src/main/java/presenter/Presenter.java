package presenter;

import model.Model;
import model.entity.Section;
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

    public void addSportsman(Sportsman sportsman) throws Exception {
        model.addSportsman(sportsman);
    }

    public void editSportsman(Sportsman sportsman) {
        model.editSportsman(sportsman);
    }

    public void deleteSportsmanById(long id) {
        model.deleteSportsmanById(id);
    }

    public String[] getSectionNames() {
        return model.getSectionNames();
    }

    public void addSection(Section section){
        model.addSection(section);
    }

    public void editSection(Section section){
        model.editSection(section);
    }

    public void deleteSectionById(long id){
        model.deleteSectionById(id);
    }

    public void updateSportsman(Sportsman sportsman) {
        model.editSportsman(sportsman);
    }
}
