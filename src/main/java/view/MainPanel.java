package view;

import javax.swing.*;
import java.awt.*;
import java.lang.reflect.InvocationTargetException;

public class MainPanel extends JPanel {

    JPanel form;
    JLabel hint = new JLabel("<html><div style='text-align: center;'>Выберите интерфейс из меню слева</div></html>", SwingConstants.CENTER);

    public MainPanel() {
        setLayout(new BorderLayout());
        add(hint);
    }

    public void open(String className) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        close();
        form = (JPanel) Class.forName(className).getDeclaredConstructor().newInstance();
        add(form, BorderLayout.CENTER);
        hint.setVisible(false);
        revalidate();
        repaint();
    }

    public void close() {
        if (form != null) {
            remove(form);
        }
        hint.setVisible(true);
        revalidate();
        repaint();
    }
}
