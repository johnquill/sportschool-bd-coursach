package view.form;

import model.entity.Sportsman;
import presenter.Presenter;

import javax.swing.*;
import java.awt.*;

public class InputEntityDialog extends JDialog {
    
    Presenter presenter;

    public InputEntityDialog(Presenter presenter) {
        this.presenter = presenter;
        setLocationRelativeTo(null);
        GridLayout layout = new GridLayout(7, 2);
        layout.setVgap(5);
        layout.setHgap(5);
        setLayout(layout);
        setSize(300, 300);
        buildFields();
    }

    public void buildFields() {
        JTextField family = new JTextField();
        add(new JLabel("Фамилия"));
        add(family);
        JTextField name = new JTextField();
        add(new JLabel("Имя"));
        add(name);
        JTextField patronymic = new JTextField();
        add(new JLabel("Отчество"));
        add(patronymic);
        JComboBox<String> section = new JComboBox<>();
        add(new JLabel("Секция"));
        add(section);
        JTextField profession = new JTextField();
        add(new JLabel("Профессия"));
        add(profession);
        
        JButton add = new JButton("Добавить");
        add(add);
        JButton cancel = new JButton("Отменить");
        add(cancel);

        add.addActionListener(e -> {
            presenter.addSportsman(new Sportsman(family.getText(), name.getText(), patronymic.getText(), (String) section.getSelectedItem(), profession.getText()));
        });
        cancel.addActionListener(e -> {
            dispose();
        });

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);

    }
}
