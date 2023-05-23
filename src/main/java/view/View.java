package view;

import presenter.Presenter;
import view.menu.MenuPanel;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class View {

    private JFrame frame;
    private MainPanel mainPanel;
    private MenuPanel menuPanel;
    private final Presenter presenter;

    public View(Presenter presenter) {
        this.presenter = presenter;
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
        try {
            menuPanel = new MenuPanel(mainPanel, presenter);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        JScrollPane scrollMenu = new JScrollPane(menuPanel);
        splitPane.setLeftComponent(scrollMenu);
        splitPane.setRightComponent(mainPanel);
        splitPane.setResizeWeight(0.05);
        return splitPane;
    }
}
