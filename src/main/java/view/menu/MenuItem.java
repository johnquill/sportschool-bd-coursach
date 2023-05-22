package view.menu;

import utils.xml.Item;
import view.MainPanel;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class MenuItem extends JButton {

    public static Color background = new Color(150, 150, 150);
    public static Color selectedBackground = new Color(125, 125, 125);
    public boolean isOpen = false;
    public int type;

    private ImageIcon doc;
    private ImageIcon docs;
    {
        try {
            docs = new ImageIcon(ImageIO.read(new File("src/main/resources/icons/many_documents.png"))
                    .getScaledInstance(30, 30, Image.SCALE_SMOOTH));
            doc = new ImageIcon(ImageIO.read(new File("src/main/resources/icons/document.png"))
                    .getScaledInstance(30, 30, Image.SCALE_SMOOTH));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public MenuItem(Item el, ArrayList<MenuItem> buttons, int layer, int index, MenuPanel menuPanel, MainPanel mainPanel) {
        int newIndex = index == -1 ? buttons.size() : index;
        buttons.add(newIndex, this);
        if (el.getPanelClass() == null) {
            type = 0;
            stylise(el, layer);
            addActionListener(e -> {
                if (isOpen) {
                    isOpen = false;
                    for (int i = 0; i < el.getChildren().size(); i++) {
                        buttons.remove(newIndex+1);
                    }
                    menuPanel.updateMenu();
                } else {
                    isOpen = true;
                    el.getChildren().forEach(ch -> {
                        new MenuItem(ch, buttons, layer + 1, newIndex+1, menuPanel, mainPanel);
                    });
                    menuPanel.updateMenu();
                }
            });
        } else {
            type = 1;
            stylise(el, layer);
            addActionListener(e -> {
                if (!isOpen) {
                    menuPanel.closeOpened();
                    isOpen = true;
                    setBackground(selectedBackground);
                    mainPanel.open(el.getPanelClass());
                } else {
                    isOpen = false;
                    setBackground(background);
                    mainPanel.close();
                }
            });
        }
    }

    private void stylise(Item el, int layer) {
        setText(el.getName());
        setHorizontalAlignment(SwingConstants.LEFT);
        setBackground(background);
        setBorder(BorderFactory.createEmptyBorder(0, 25 * layer, 0, 0));
        if (type == 0) {
            setIcon(docs);
        } else {
            setIcon(doc);
        }
    }

}
