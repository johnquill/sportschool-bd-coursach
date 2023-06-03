package presenter;

import model.Model;
import model.entity.Coach;
import model.entity.Section;
import model.entity.Sportsman;

import java.awt.*;
import java.util.ArrayList;

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

    public void deleteEntityById(Class entity, long id) {
        if (entity == Sportsman.class) {
            model.deleteSportsmanById(id);
        } else if (entity == Section.class) {
            model.deleteSectionById(id);
        } else if (entity == Coach.class) {
            //model.deleteCoachById();
        }
    }

    public String[] getTrainers() {
        return model.getTrainers();
    }

    public void updateSection(Section section) {
        model.updateSection(section);
    }

    public void addCoach(Coach coach) {
        model.addCoach(coach);
    }

    public void updateCoach(Coach coach) {
        model.updateCoach(coach);
    }

    public ArrayList<Section> getActiveSections() {
        return model.getActiveSections();
    }
}
