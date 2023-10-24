package utils.pdf;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.BaseFont;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.*;

public class PdfExporter {

    public static void exportToPdf(String html, File pdf) throws IOException, DocumentException {
        OutputStream os = new FileOutputStream(pdf);
        ITextRenderer renderer = new ITextRenderer();
        renderer.getFontResolver().addFont("src/main/resources/Verdana.ttf",
                BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
        renderer.setDocumentFromString(html);
        renderer.layout();
        renderer.createPDF(os);
        os.close();
    }
}