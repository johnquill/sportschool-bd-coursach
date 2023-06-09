package view.report;

import model.entity.Section;
import model.entity.Sportsman;
import presenter.Presenter;
import utils.date.DateUtils;

import javax.swing.*;
import java.util.ArrayList;

public class SectionCompound extends AbstractReport {


    public SectionCompound(Presenter presenter) {
        super(presenter, "Состав секций");
    }

    @Override
    String toHtml() {
        StringBuilder sb = new StringBuilder("<html>");
        sb.append("<h1>Спортивная школа. Отчет по составу секций</h1>");
        sb.append("<br><p>Отчет создан: ").append(DateUtils.getCurrentDateTime()).append("</p><br>");
        ArrayList<Section> sections = null;
        try {
            sections = presenter.getSections();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Ошибка", JOptionPane.ERROR_MESSAGE);
        }
        sections.forEach(el -> {
            sb.append("</hr>");
            sb.append("<h3>").append(el.getName()).append("</h3>");
            ArrayList<Sportsman> sportsmen = null;
            try {
                sportsmen = presenter.getSportsman(el);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, e.getMessage(), "Ошибка", JOptionPane.ERROR_MESSAGE);
            }
            sb.append("<table style=\"width:100%\" border=\"1\"><tr><th>Фамилия</th><th>Имя</th><th>Отчество</th></tr>");
            sportsmen.forEach(sp -> {
                        sb.append("<tr>");
                        sb.append(String.format("<td>%s</td><td>%s</td><td>%s</td>",
                                sp.getSurname(), sp.getName(), sp.getPatronymic()));
                        sb.append("</tr>");
                    }
            );
            sb.append("</table>");
        });

        sb.append("</html>");
        return sb.toString();
    }
}
