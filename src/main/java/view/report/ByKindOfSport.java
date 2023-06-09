package view.report;

import model.entity.Section;
import model.entity.Sport;
import model.entity.Sportsman;
import presenter.Presenter;
import utils.date.DateUtils;

import javax.swing.*;
import java.util.ArrayList;

public class ByKindOfSport extends AbstractReport {


    public ByKindOfSport(Presenter presenter) {
        super(presenter, "Секции и спортсмены по видам спорта");
    }

    @Override
    String toHtml() {
        StringBuilder sb = new StringBuilder("<html>");
        sb.append("<h1>Спортивная школа. Отчет по видам спорта</h1>");
        sb.append("<br><p>Отчет создан: ").append(DateUtils.getCurrentDateTime()).append("</p><br>");
        ArrayList<Sport> sports = null;
        try {
            sports = presenter.getSports();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Ошибка", JOptionPane.ERROR_MESSAGE);
        }
        sports.forEach(sport -> {
            sb.append("</hr>");
            sb.append("<h3>").append(sport.getName()).append("</h3>");
            ArrayList<Section> sections = null;
            try {
                sections = presenter.getSections(sport);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, e.getMessage(), "Ошибка", JOptionPane.ERROR_MESSAGE);
            }
            if (sections.isEmpty()) {
                sb.append("Секций нет");
            } else {
                sb.append("<table style=\"width:100%\" border=\"1\"><tr><th>Секция</th><th>Спортсмены</th></tr>");
                sections.forEach(sec -> {
                    ArrayList<Sportsman> sportsmen = null;
                    try {
                        sportsmen = presenter.getSportsman(sec);
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(this, e.getMessage(), "Ошибка", JOptionPane.ERROR_MESSAGE);
                    }
                    StringBuilder sportsmenText = new StringBuilder();
                    sportsmen.forEach(el -> sportsmenText.append(
                            String.join(" ", el.getSurname(), el.getName(), el.getPatronymic())).append("\n"));
                    sb.append("<tr>");
                    sb.append(String.format("<td>%s</td><td>%s</td>", sec.getName(), sportsmenText));
                    sb.append("</tr>");
                });
                sb.append("</table>");
            }
        });

        sb.append("</html>");
        return sb.toString();
    }
}
