package presenter;

import model.Model;
import model.entity.Coach;
import model.entity.Section;
import model.entity.Sport;
import model.entity.Sportsman;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

public class Presenter {

    Model model;

    public Presenter(Model model) {
        this.model = model;
    }

    public String[] getTableHeaders(Class entity) {
        return model.getTableHeaders(entity);
    }

    public Object[][] getTableData(Class entity) throws Exception {
        return model.getTableData(entity);
    }

    public void addSportsman(Sportsman sportsman) throws Exception {
        model.addSportsman(sportsman);
    }

    public void editSportsman(Sportsman sportsman) throws Exception {
        model.editSportsman(sportsman);
    }

    public void deleteSportsmanById(long id) {
        model.deleteSportsmanById(id);
    }

    public String[] getSectionNames() throws Exception {
        return model.getSectionNames();
    }

    public void addSection(Section section) throws Exception {
        model.addSection(section);
    }

    public void editSection(Section section) throws Exception {
        model.editSection(section);
    }

    public void deleteSectionById(long id) throws Exception {
        model.deleteSectionById(id);
    }

    public void updateSportsman(Sportsman sportsman) throws Exception {
        model.editSportsman(sportsman);
    }

    public void deleteEntityById(Class entity, long id) throws Exception {
        if (entity == Sportsman.class) {
            model.deleteSportsmanById(id);
        } else if (entity == Section.class) {
            model.deleteSectionById(id);
        } else if (entity == Coach.class) {
            //model.deleteCoachById();
        }
    }

    public String[] getTrainers(){
        return model.getTrainers();
    }

    public void updateSection(Section section) throws Exception {
        model.updateSection(section);
    }

    public void addCoach(Coach coach) throws Exception {
        model.addCoach(coach);
    }

    public void updateCoach(Coach coach) throws Exception {
        model.updateCoach(coach);
    }

    public ArrayList<Section> getActiveSections() throws Exception {
        return model.getActiveSections();
    }

    public ArrayList<Coach> getCoaches() {
        return model.getCoaches();
    }

    public ArrayList<Section> getSections(Coach coach) throws Exception {
        return model.getSectionByTrainerId(coach.getId());
    }

    public ArrayList<Section> getSections() throws Exception {
        return model.getSections();
    }

    public ArrayList<Sportsman> getSportsman(Section el) throws Exception {
        return model.getSportsmenBySectionId(el.getId());
    }

    public ArrayList<Sport> getSports() throws Exception {
        return model.getSports();
    }

    public ArrayList<Section> getSections(Sport sport) throws Exception {
        return model.getSectionsBySport(sport.getName());
    }

    public HashMap<String, Integer> getSportsmenOfSportsCount() throws Exception {
        return model.getSportsmenOfSportsCount();
    }
}
