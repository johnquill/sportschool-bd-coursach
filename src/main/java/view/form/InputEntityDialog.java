package view.form;

import model.entity.Sportsman;
import presenter.Presenter;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class InputEntityDialog extends JDialog {
    
    Presenter presenter;

    public InputEntityDialog(Presenter presenter) {
        this.presenter = presenter;
        setLocationRelativeTo(null);
        JPanel inputPanel = new JPanel();
        GridLayout layout = new GridLayout(7, 2);
        layout.setVgap(5);
        layout.setHgap(5);
        inputPanel.setLayout(layout);
        setSize(300, 300);
        buildFields(inputPanel);
        inputPanel.setBorder(new EmptyBorder(10, 15, 10,15));

        add(inputPanel);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    public void buildFields(JPanel inputPanel) {
        JTextField family = new JTextField();
        inputPanel.add(new JLabel("Фамилия"));
        inputPanel.add(family);
        JTextField name = new JTextField();
        inputPanel.add(new JLabel("Имя"));
        inputPanel.add(name);
        JTextField patronymic = new JTextField();
        inputPanel.add(new JLabel("Отчество"));
        inputPanel.add(patronymic);
        JComboBox<String> section = new JComboBox<>();
        inputPanel.add(new JLabel("Секция"));
        inputPanel.add(section);
        JTextField profession = new JTextField();
        inputPanel.add(new JLabel("Профессия"));
        inputPanel.add(profession);

        inputPanel.add(new JPanel());
        inputPanel.add(new JPanel());
        JButton add = new JButton("Добавить");
        inputPanel.add(add);
        JButton cancel = new JButton("Отменить");
        inputPanel.add(cancel);

        add.addActionListener(e -> {
            presenter.addSportsman(new Sportsman(family.getText(), name.getText(), patronymic.getText(), (String) section.getSelectedItem(), profession.getText()));
        });
        cancel.addActionListener(e -> {
            dispose();
        });
    }
}
