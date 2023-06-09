package utils;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.Locale;

public class PathChooser {
    String extension = null;

    JFileChooser fileChooser = new JFileChooser();
    File lastDir = null;

    Component parent;
    PropertyWorker propWorker;

    public PathChooser(Component parent, String extension) {
        this.extension = extension;
        this.parent = parent;
        propWorker = new PropertyWorker(this, fileChooser, extension);
        Thread propThread = new Thread(propWorker);
        propThread.setDaemon(true);
        propThread.start();
        propThread.interrupt();
    }

    public File choosePath(String destFile) {
        //if (lastDir != null) {
        //    fileChooser.setCurrentDirectory(lastDir);
        //}
        fileChooser.setSelectedFile(new File(
                destFile.toLowerCase(Locale.ROOT).replace(" ", "-") + extension));
        int result = fileChooser.showOpenDialog(parent);

        if (result == JFileChooser.APPROVE_OPTION) {
            lastDir = fileChooser.getCurrentDirectory();
            propWorker.storeLastDir(lastDir);
            return fileChooser.getSelectedFile();
        }
        return null;
    }

    public void setLastDir(File lastDir) {
        this.lastDir = lastDir;
    }
}
