package view.menu;

import jakarta.xml.bind.JAXBException;
import utils.xml.Item;
import utils.xml.Menu;
import utils.xml.MenuParser;
import view.MainPanel;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class MenuPanel extends JPanel {

    private MainPanel mainPanel;
    private File file = new File("src/main/resources/Menu.xml");
    private Menu menu;
    private ArrayList<MenuItem> buttons;

    public MenuPanel(MainPanel mainPanel) throws IOException {
        this.mainPanel = mainPanel;
        setBackground(MenuItem.background);
        menu = parseMenu();
        buildMenu(menu);
    }

    private void buildMenu(Menu menu) {
        buttons = new ArrayList<>();
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        menu.getItems().forEach(el -> new MenuItem(el, buttons, 0, -1, this, mainPanel));
        buttons.forEach(this::add);
    }

    public void updateMenu() {
        removeAll();
        buttons.forEach(this::add);
        revalidate();
        repaint();
    }

    private Menu parseMenu() {
        try {
            return MenuParser.parseToObject(file);
        } catch (JAXBException e) {
            JOptionPane.showMessageDialog(this, "Ошибка построения меню. Не удалось распарсить " +
                    "файл " + file.getName(), "Ошибка", JOptionPane.ERROR_MESSAGE);
        }
        return null;
    }

    public void closeOpened() {
        buttons.forEach(button -> {
            if (button.isOpen && button.type == 1) {
                button.setBackground(MenuItem.background);
                updateMenu();
            }
        });
    }
}
