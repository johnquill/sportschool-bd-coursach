package utils.pdf;

import com.groupdocs.conversion.filetypes.PdfFileType;
import com.groupdocs.conversion.options.convert.ConvertOptions;
import com.groupdocs.conversion.Converter;
import com.groupdocs.conversion.options.convert.PdfConvertOptions;

import java.io.*;

public class PdfExporter {

    public static void exportToPdf(String html, File pdf) {
        InputStream inputStream = new ByteArrayInputStream(html.getBytes());
        Converter converter = new Converter(inputStream);
        ConvertOptions<PdfFileType> convertOptions = new PdfConvertOptions();
        converter.convert(pdf.getAbsolutePath(), convertOptions);
    }
}