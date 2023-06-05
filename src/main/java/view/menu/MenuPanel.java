package view.menu;

import jakarta.xml.bind.JAXBException;
import presenter.Presenter;
import utils.xml.Menu;
import utils.xml.MenuParser;
import view.MainPanel;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

public class MenuPanel extends JPanel {

    private final MainPanel mainPanel;
    private final File file = new File("src/main/resources/Menu.xml");
    public static Color background = new Color(150, 150, 150);
    private final Menu menu;
    private final ArrayList<MenuItem> menuItems;
    private MenuItem selectedItem;
    Presenter presenter;

    public MenuPanel(MainPanel mainPanel, Presenter presenter) throws IOException {
        this.presenter = presenter;
        this.mainPanel = mainPanel;
        setBackground(background);
        menu = parseMenu();
        menuItems = buildMenu();
        updateMenu();
    }

    private ArrayList<MenuItem> buildMenu() {
        ArrayList<MenuItem> items = new ArrayList<>();
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        menu.getItems().forEach(el -> items.add(new MenuItem(el, 0, this)));
        return items;
    }

    public void open(MenuItem item) {
        if (item.isLeaf) {
            try {
                mainPanel.open(item.item.getPanelClass(), presenter);
                select(item);
                item.isOpen = true;
            } catch (ClassNotFoundException | NoSuchMethodException | InvocationTargetException |
                     InstantiationException | IllegalAccessException e) {
                JOptionPane.showMessageDialog(this,
                        "Ошибка при создании интерфейса из класса \"" + item.item.getPanelClass() + "\"",
                        "Ошибка", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            int index = menuItems.indexOf(item);
            menuItems.addAll(index + 1, item.buildChildren(this));
            item.isOpen = true;
            updateMenu();
        }
    }

    private void select(MenuItem item) {
        if (selectedItem != null) {
            selectedItem.setForeground(null);
        }
        item.setForeground(Color.YELLOW);
        selectedItem = item;
    }

    private void unselect(MenuItem item) {
        selectedItem = null;
        item.setForeground(null);
    }

    public void close(MenuItem item) {
        if (item.isLeaf) {
            mainPanel.close();
            unselect(item);
        } else {
            int index = menuItems.indexOf(item);
            for (int i = 0; i < item.item.getChildren().size(); i++) {
                menuItems.remove(index + 1);
            }
            updateMenu();
        }
        item.isOpen = false;
    }

    public void updateMenu() {
        removeAll();
        menuItems.forEach(this::add);
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
}
