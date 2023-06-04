package view.report;

import com.mysql.cj.util.StringUtils;
import model.entity.Section;
import presenter.Presenter;
import utils.date.DateUtils;
import utils.pdf.PdfExporter;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Properties;

public class ActiveSections extends JPanel {

    Presenter presenter;
    String html;
    JFileChooser fileChooser = new JFileChooser();
    File lastDir = null;
    Properties properties = new Properties();
    String PROPERTY_FILE = "src/main/resources/config.properties";

    public ActiveSections(Presenter presenter) {
        this.presenter = presenter;
        try {
            properties.load(new FileReader(PROPERTY_FILE));
            if (!StringUtils.isEmptyOrWhitespaceOnly(properties.getProperty("last-dir"))) {
                lastDir = new File(properties.getProperty("last-dir"));
            }
        } catch (IOException e) {
            lastDir = null;
        }
        fileChooser.setFileFilter(new FileFilter() {
            @Override
            public boolean accept(File f) {
                return f.getName().endsWith(".pdf") || f.isDirectory();
            }

            @Override
            public String getDescription() {
                return "*.pdf";
            }
        });
        setLayout(new BorderLayout());
        add(new JLabel("Работающие секции"), BorderLayout.NORTH);
        add(htmlPanel(), BorderLayout.CENTER);
        JButton downloadButton = new JButton("Скачать БЕСПЛАТНО");
        downloadButton.setBackground(Color.GREEN);
        downloadButton.addActionListener(e -> {
            exportHtml();
        });
        add(downloadButton, BorderLayout.SOUTH);
    }

    private void exportHtml() {
        if (lastDir != null) {
            fileChooser.setCurrentDirectory(lastDir);
        }
        fileChooser.setSelectedFile(new File(
                "Работающие секции".toLowerCase(Locale.ROOT).replace(" ", "-") + ".pdf"));
        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            PdfExporter.exportToPdf(html, file);
            JOptionPane.showMessageDialog(this,"Документ загружен по пути "
                    + file.getAbsolutePath(), "Сообщение", JOptionPane.PLAIN_MESSAGE);
            lastDir = fileChooser.getCurrentDirectory();
            properties.setProperty("last-dir", lastDir.getAbsolutePath());
            try {
                properties.store(new FileWriter(PROPERTY_FILE), null);
            } catch (IOException ignored) {}
        }
    }

    private JScrollPane htmlPanel() {
        JEditorPane jEditorPane = new JEditorPane();
        jEditorPane.setEditable(false);
        jEditorPane.setContentType("text/html");
        html = toHtml(presenter.getActiveSections());
        jEditorPane.setText(html);

        JScrollPane jScrollPane = new JScrollPane(jEditorPane);
        jScrollPane.setPreferredSize(new Dimension(540, 400));

        return jScrollPane;
    }

    private String toHtml(ArrayList<Section> sectionList) {
        StringBuilder sb = new StringBuilder("<html lang=\"ru\">");
        sb.append("<h1>Спортивная школа. Работающие секции</h1>");
        sb.append("<br><p>Отчет создан: ").append(DateUtils.getCurrentDate()).append("</p><br>");
        sb.append("<table><tr><th>Название</th><th>Расписание</th><th>Зал</th><th>Вид спорта</th><th>Тренер</th></tr>");
        sectionList.forEach(el -> {
                    sb.append("<tr>");
                    sb.append(String.format("<td>%s</td><td>%s</td><td>%s</td><td>%s</td><td>%s</td>",
                            el.getName(), el.getSchedule(), el.getRoom(), el.getSport(), el.getCoach()));
                    sb.append("</tr>");
                }
        );
        sb.append("</table>");
        sb.append("</html>");
        return sb.toString();
    }
}
