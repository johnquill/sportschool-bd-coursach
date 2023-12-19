import model.Model;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import presenter.Presenter;
import view.report.ActiveSections;
import view.report.ByKindOfSport;
import view.report.SectionCompound;
import view.report.SectionsByCoach;

import java.sql.SQLException;

public class HtmlExportTest extends DataBaseTest {

    private static final String ACTIVE_SECTIONS_REPORT_HTML = """
            <html lang="ru"><head><style type='text/css'>* { font-family: Verdana; }</style></head>
            <h1>Спортивная школа. Работающие секции</h1><br /><p>Отчет создан: /d/d/d/d./d/d./d/d /d/d:/d/d:/d/d</p>
            <br /><table style="width:100%" border="1">
            <tr><th>Название</th><th>Расписание</th><th>Зал</th><th>Вид спорта</th><th>Тренер</th></tr>
            <tr><td>Баскетбол</td><td>Сб 20:00</td><td>221</td><td>Баскетбол</td><td>Шкуратов Андрей Владимирович</td></tr>
            </table></html>""";
    private static final String SECTION_COMPOUND_REPORT_HTML = """
            <htmllang="ru"><head><styletype='text/css'>*{font-family:Verdana;}</style></head>
            <h1>Спортивнаяшкола.Отчетпосоставусекций</h1><br/><p>Отчет</p>
            <br/></hr><h3>Баскетбол</h3><tablestyle="width:100%"border="1">
            <tr><th>Фамилия</th><th>Имя</th><th>Отчество</th></tr>
            <tr><td>Ходяков</td><td>Федор</td><td>Андреевич</td></tr>
            </table></html>""";
    private static final String BY_KIND_OF_SPORT_REPORT_HTML = """
            <htmllang="ru"><head><styletype='text/css'>*{font-family:Verdana;}</style></head>
            <h1>Спортивнаяшкола.Отчетповидамспорта</h1><br/><p>Отчет</p>
            <br/></hr><h3>Баскетбол</h3><tablestyle="width:100%"border="1">
            <tr><th>Секция</th><th>Спортсмены</th></tr>
            <tr><td>Баскетбол</td><td>ХодяковФедорАндреевич<br/></td></tr>
            </table></html>""";
    private static final String SECTIONS_BY_COACH_REPORT_HTML = """
            <htmllang="ru"><head><styletype='text/css'>*{font-family:Verdana;}</style></head>
            <h1>Спортивнаяшкола.Отчетпотренерамиихсекциям</h1><br/><p>Отчет</p>
            <br/></hr><h3>ШкуратовАндрейВладимирович</h3>Баскетбол<br/><br/><tablestyle="width:100%"border="1">
            <tr><th>Название</th><th>Расписание</th><th>Зал</th><th>Работает?</th></tr>
            <tr><td>Баскетбол</td><td>Сб20:00</td><td>221</td><td>Да</td></tr>
            </table></html>""";

    public static final String EXPORT_ERROR = "Результат преобразования данных в html файл не соответствует ожидаемому";

    @Before
    public void start() throws SQLException {
        init();
    }

    @After
    public void finish() throws SQLException {
        connection.rollback();
        connection.close();
    }

    @Test
    public void testExportReports() {
        ActiveSections activeSections = new ActiveSections(new Presenter(new Model(connection)));
        String text = normalize(activeSections.toHtml());
        Assert.assertEquals(EXPORT_ERROR, text, normalize(ACTIVE_SECTIONS_REPORT_HTML));

        SectionCompound sectionCompound = new SectionCompound(new Presenter(new Model(connection)));
        text = normalize(sectionCompound.toHtml());
        Assert.assertEquals(EXPORT_ERROR, text, normalize(SECTION_COMPOUND_REPORT_HTML));

        ByKindOfSport byKindOfSport = new ByKindOfSport(new Presenter(new Model(connection)));
        text = normalize(byKindOfSport.toHtml());
        Assert.assertEquals(EXPORT_ERROR, text, normalize(BY_KIND_OF_SPORT_REPORT_HTML));

        SectionsByCoach sectionsByCoach = new SectionsByCoach(new Presenter(new Model(connection)));
        text = normalize(sectionsByCoach.toHtml());
        Assert.assertEquals(EXPORT_ERROR, text, normalize(SECTIONS_BY_COACH_REPORT_HTML));
    }

    private static String normalize(String html) {
        html = html.replace("\n", "").replace(" ", "");
        int start = html.indexOf("создан");
        if (start != -1) {
            int end = html.indexOf("</p>", start);
            html = html.substring(0, start).concat(html.substring(end));
        }
        return html;
    }


}
