package view.form;

import model.entity.Coach;
import model.entity.Section;
import model.entity.Sportsman;
import presenter.Presenter;

public class InputDialogBuilder {

    public static void buildInputEntity(Presenter presenter, EntityPanel panel, boolean isAdd) throws Exception {
        if (panel.entity == Sportsman.class) {
            new InputSportsmanDialog(presenter, panel, isAdd);
        } else if (panel.entity == Section.class) {
            new InputSectionDialog(presenter, panel, isAdd);
        } else if (panel.entity == Coach.class) {
            new InputCoachDialog(presenter, panel, isAdd);
        }
    }

}
