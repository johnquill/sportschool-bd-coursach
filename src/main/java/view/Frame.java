package view;

import com.sun.tools.javac.Main;

import javax.swing.*;
import java.awt.*;

public class Frame {

    private JFrame frame;
    private MainPanel mainPanel;
    private MenuPanel menuPanel;

    public Frame() {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.setSize(Toolkit.getDefaultToolkit().getScreenSize());
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setLocationRelativeTo(null);
        frame.setTitle("Спортивная школа");

        frame.add(buildMenuSplit());

        frame.setVisible(true);
    }

    private Component buildMenuSplit() {
        JSplitPane splitPane = new JSplitPane();
        mainPanel = new MainPanel();
        menuPanel = new MenuPanel(mainPanel);
        splitPane.setLeftComponent(menuPanel);
        splitPane.setRightComponent(mainPanel);
        splitPane.setResizeWeight(0.1);
        return splitPane;
    }
}
