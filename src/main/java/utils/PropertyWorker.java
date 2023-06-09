package utils;

import com.mysql.cj.util.StringUtils;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

public class PropertyWorker implements Runnable {

    Properties properties = new Properties();
    String PROPERTY_FILE = "src/main/resources/config.properties";
    JFileChooser fileChooser;
    String extension;
    PathChooser pathChooser;

    PropertyWorker(PathChooser pathChooser, JFileChooser fileChooser, String extension) {
        this.pathChooser = pathChooser;
        this.extension = extension;
        this.fileChooser = fileChooser;
        try {
            properties.load(new FileReader(PROPERTY_FILE));
            File dir = new File(getLastDir());
            fileChooser.setCurrentDirectory(dir);
            pathChooser.setLastDir(dir);
        } catch (IOException e) {
            new File(PROPERTY_FILE);
        }
    }

    public String getLastDir() {
        if (!StringUtils.isEmptyOrWhitespaceOnly(properties.getProperty("last-dir"))) {
            return properties.getProperty("last-dir");
        }
        return null;
    }

    void storeLastDir(File lastDir) {
        properties.setProperty("last-dir", lastDir.getAbsolutePath());
        try {
            properties.store(new FileWriter(PROPERTY_FILE), null);
        } catch (IOException ignored) {}
        fileChooser.setCurrentDirectory(lastDir);
    }

    @Override
    public void run() {
        fileChooser.setFileFilter(new FileFilter() {
            @Override
            public boolean accept(File f) {
                return f.getName().endsWith(extension) || f.isDirectory();
            }

            @Override
            public String getDescription() {
                return "*" + extension;
            }
        });
        while (true) {
            try {
                if (!Thread.interrupted()) Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
