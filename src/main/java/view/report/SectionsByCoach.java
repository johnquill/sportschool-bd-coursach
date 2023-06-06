package view.report;

import model.entity.Coach;
import model.entity.Section;
import presenter.Presenter;
import utils.date.DateUtils;

import javax.swing.*;
import java.util.ArrayList;

public class SectionsByCoach extends AbstractReport {

    public SectionsByCoach(Presenter presenter) {
        super(presenter, "Секции по тренерам");
    }

    @Override
    String toHtml(){
        StringBuilder sb = new StringBuilder("<html>");
        sb.append("<h1>Спортивная школа. Отчет по тренерам и их секциям</h1>");
        sb.append("<br><p>Отчет создан: ").append(DateUtils.getCurrentDate()).append("</p><br>");
        ArrayList<Coach> trainers = presenter.getCoaches();
        trainers.forEach(tr -> {
            sb.append("</hr>");
            sb.append("<h3>").append(tr.getSurname())
                    .append(" ").append(tr.getName())
                    .append(" ").append(tr.getPatronymic())
                    .append(" ").append("</h3>");
            sb.append(tr.getSport()).append("<br><br>");
            ArrayList<Section> sections = null;
            try {
                sections = presenter.getSections(tr);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, e.getMessage(), "Ошибка", JOptionPane.ERROR_MESSAGE);
            }
            sb.append("<table style=\"width:100%\" border=\"1\"><tr><th>Название</th><th>Расписание</th><th>Зал</th><th>Работает?</th></tr>");
            sections.forEach(el -> {
                        sb.append("<tr>");
                        sb.append(String.format("<td>%s</td><td>%s</td><td>%s</td><td>%s</td>",
                                el.getName(), el.getSchedule(), el.getRoom(), el.getIs_working() ? "Да":"Нет"));
                        sb.append("</tr>");
                    }
            );
            sb.append("</table>");
        });

        sb.append("</html>");
        return sb.toString();
    }
}
