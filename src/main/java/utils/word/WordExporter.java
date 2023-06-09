package utils.word;

import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.usermodel.CharacterRun;
import org.apache.poi.hwpf.usermodel.Paragraph;
import org.apache.poi.hwpf.usermodel.Range;
import org.apache.poi.hwpf.usermodel.Section;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class WordExporter {

    File destFile;
    File docPattern;
    HWPFDocument document = null;

    public WordExporter(File docPattern) {
        this.docPattern = docPattern;
    }

    public void setPath(File file) {
        destFile = file;
    }

    public void setPattern(File docPattern) {
        this.docPattern = docPattern;
    }

    public void open() {
        try (FileInputStream fis = new FileInputStream(docPattern)) {
            document = new HWPFDocument(fis);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void save() {
        try (FileOutputStream out = new FileOutputStream(destFile)) {
            document.write(out);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void replaceText(String findText, String replaceText) {
        Range range = document.getRange();
        for (int numSec = 0; numSec < range.numSections(); ++numSec) {
            Section sec = range.getSection(numSec);
            for (int numPara = 0; numPara < sec.numParagraphs(); numPara++) {
                Paragraph para = sec.getParagraph(numPara);
                for (int numCharRun = 0; numCharRun < para.numCharacterRuns(); numCharRun++) {
                    CharacterRun charRun = para.getCharacterRun(numCharRun);
                    String text = charRun.text();
                    if (text.contains(findText)) {
                        charRun.replaceText(findText, replaceText);
                    }
                }
            }
        }
    }
}
