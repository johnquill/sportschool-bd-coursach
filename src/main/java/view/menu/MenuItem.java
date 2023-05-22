package view.menu;

import utils.xml.Item;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class MenuItem extends JButton {

    private final int layer;
    public boolean isOpen = false;
    public boolean isLeaf;
    public Item item;

    private final ImageIcon doc;
    private final ImageIcon docs;
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

    public MenuItem(Item item, int layer, MenuPanel menuPanel) {
        this.item = item;
        this.layer = layer;
        addActionListener(e -> {
            if (!isOpen) {
                menuPanel.open(this);
            } else {
                menuPanel.close(this);
            }
        });
        isLeaf = item.getPanelClass() != null;
        stylise();
    }

    private void stylise() {
        setText(item.getName());
        setHorizontalAlignment(SwingConstants.LEFT);
        setBackground(MenuPanel.background);
        setBorder(BorderFactory.createEmptyBorder(5, 25 * layer, 5, 0));
        if (isLeaf) {
            setIcon(doc);
        } else {
            setIcon(docs);
        }
    }

    public ArrayList<MenuItem> buildChildren(MenuPanel menuPanel) {
        ArrayList<MenuItem> items = new ArrayList<>();
        item.getChildren().forEach(ch -> items.add(new MenuItem(ch, layer+1, menuPanel)));
        return items;
    }
}
