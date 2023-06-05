package view.report;

import com.mysql.cj.util.StringUtils;
import model.entity.Section;
import presenter.Presenter;
import utils.pdf.PdfExporter;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.awt.*;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Properties;

public abstract class AbstractReport extends JPanel {

    Presenter presenter;
    String html;
    JFileChooser fileChooser = new JFileChooser();
    File lastDir = null;
    Properties properties = new Properties();
    String PROPERTY_FILE = "src/main/resources/config.properties";

    String reportName;

    public AbstractReport(Presenter presenter, String reportName) {
        this.reportName = reportName;
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
        add(new JLabel(reportName), BorderLayout.NORTH);
        add(htmlPanel(), BorderLayout.CENTER);
        JButton downloadButton = new JButton("СКАЧАТЬ БЕСПЛАТНО");
        downloadButton.setBackground(Color.GREEN);
        downloadButton.addActionListener(e -> {
            exportHtml();
        });
        add(downloadButton, BorderLayout.SOUTH);
    }

    abstract String toHtml();

    private void exportHtml() {
        if (lastDir != null) {
            fileChooser.setCurrentDirectory(lastDir);
        }
        fileChooser.setSelectedFile(new File(
                reportName.toLowerCase(Locale.ROOT).replace(" ", "-") + ".pdf"));
        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            try {
                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                PdfExporter.exportToPdf(html, file);
            } finally {
                this.setCursor(Cursor.getDefaultCursor());
            }
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
        html = toHtml();
        jEditorPane.setText(html);

        JScrollPane jScrollPane = new JScrollPane(jEditorPane);
        jScrollPane.setPreferredSize(new Dimension(540, 400));

        return jScrollPane;
    }


}
