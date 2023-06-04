package utils.pdf;

import com.groupdocs.conversion.filetypes.FileType;
import com.groupdocs.conversion.options.convert.ConvertOptions;
import com.groupdocs.conversion.Converter;

import java.io.*;
import java.nio.file.Path;

public class PdfExporter {

    static Path htmlPath = Path.of("src/main/resources/temp/html.html");

    public static void exportToPdf(String html, File pdf) {
        InputStream inputStream = new ByteArrayInputStream(html.getBytes());
        Converter converter = new Converter(inputStream);
        ConvertOptions convertOptions = new FileType().fromExtension("pdf").getConvertOptions();
        converter.convert(pdf.getAbsolutePath(), convertOptions);
    }
}