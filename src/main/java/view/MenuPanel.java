package view;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.List;

public class MenuPanel extends JPanel {

    // TODO: 20.05.2023 Задуматься о связке этого набора меню с файлом, в котором они будут перечислены (меню и их панели)
    private static final List<String> menus = Arrays.asList(
            "Спортсмены",
            "Тренера",
            "Секции");
    private MainPanel mainPanel;

    public MenuPanel(MainPanel mainPanel) {
        this.mainPanel = mainPanel;
        GridLayout gridLayout = new GridLayout(menus.size(), 1);
        setLayout(gridLayout);
        menus.forEach(el -> {
            JButton item = new JButton(el);
            item.addActionListener(e -> {
                mainPanel.open(el);
            });
            add(item);
        });
    }

}
