package view.report;

import model.entity.Section;
import presenter.Presenter;
import utils.date.DateUtils;

import javax.swing.*;
import java.util.ArrayList;


public class ActiveSections extends AbstractReport {

    public ActiveSections(Presenter presenter) {
        super(presenter, "Работающие секции");
    }

    @Override
    String toHtml() {
        ArrayList<Section> sectionList = null;
        try {
            sectionList = presenter.getActiveSections();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Ошибка", JOptionPane.ERROR_MESSAGE);
        }
        StringBuilder sb = new StringBuilder("<html lang=\"ru\">");
        sb.append("<h1>Спортивная школа. Работающие секции</h1>");
        sb.append("<br><p>Отчет создан: ").append(DateUtils.getCurrentDateTime()).append("</p><br>");
        sb.append("<table style=\"width:100%\" border=\"1\"><tr><th>Название</th><th>Расписание</th><th>Зал</th><th>Вид спорта</th><th>Тренер</th></tr>");
        sectionList.forEach(el -> {
                    sb.append("<tr>");
                    sb.append(String.format("<td>%s</td><td>%s</td><td>%s</td><td>%s</td><td>%s</td>",
                            el.getName(), el.getSchedule(), el.getRoom(), el.getSport(), String.join(" ", el.getCoach())));
                    sb.append("</tr>");
                }
        );
        sb.append("</table>");
        sb.append("</html>");
        return sb.toString();
    }
}
