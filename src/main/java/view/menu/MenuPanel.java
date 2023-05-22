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
    private ImageIcon doc = new ImageIcon(ImageIO.read(new File("src/main/resources/icons/document.png"))
            .getScaledInstance(50, 50, Image.SCALE_SMOOTH));
    private ImageIcon docs = new ImageIcon(ImageIO.read(new File("src/main/resources/icons/many_documents.png"))
            .getScaledInstance(50, 50, Image.SCALE_SMOOTH));
    private Menu menu;

    public MenuPanel(MainPanel mainPanel) throws IOException {
        this.mainPanel = mainPanel;
        menu = parseMenu();
        buildMenu(menu);
    }

    private void buildMenu(Menu menu) {
        ArrayList<JButton> buttons = new ArrayList<>();
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        menu.getItems().forEach(el -> createMenuItem(el, buttons));
        buttons.forEach(this::add);
    }

    private void createMenuItem(Item el, ArrayList<JButton> buttons) {
        createMenuItem(el, "", buttons, -1);
    }

    private void createMenuItem(Item el, String name, ArrayList<JButton> buttons, int index) {
        index = index == -1 ? buttons.size() : index;
        name = "".equals(name) ? el.getName() : name + "/" + el.getName();
        JButton button = new JButton(name, doc);
        button.setMaximumSize(new Dimension(200, 75));
        button.setHorizontalAlignment(SwingConstants.LEFT);
        buttons.add(index, button);
        if (el.getPanelClass() == null) {
            button.addActionListener(e -> {
                el.getChildren().forEach(ch -> {
                    createMenuItem(ch, button.getText(), buttons, buttons.indexOf(button) + 1);
                });
                removeAll();
                buttons.forEach(this::add);
                revalidate();
                repaint();
            });
        } else {
            button.addActionListener(e -> mainPanel.open(el.getPanelClass()));
        }
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
