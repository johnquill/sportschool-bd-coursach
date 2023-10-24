package view.report;

import com.itextpdf.text.DocumentException;
import presenter.Presenter;
import utils.PathChooser;
import utils.pdf.PdfExporter;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public abstract class AbstractReport extends JPanel {

    Presenter presenter;
    String html;

    String reportName;
    PathChooser pathChooser = new PathChooser(this, ".pdf");

    public AbstractReport(Presenter presenter, String reportName) {
        this.reportName = reportName;
        this.presenter = presenter;
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
        File file = pathChooser.choosePath(reportName);
        if (file != null) {
            try {
                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                PdfExporter.exportToPdf(html, file);
            } catch (DocumentException | IOException e) {
                throw new RuntimeException(e);
            } finally {
                this.setCursor(Cursor.getDefaultCursor());
            }
            JOptionPane.showMessageDialog(this, "Документ загружен по пути "
                    + file.getAbsolutePath(), "Сообщение", JOptionPane.PLAIN_MESSAGE);
        }
    }

    private JScrollPane htmlPanel(){
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
