package view.form;

import model.entity.Sportsman;
import presenter.Presenter;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class InputSportsmanDialog extends JDialog {
    
    Presenter presenter;

    public JTextField family;
    public JTextField name;
    public JTextField patronymic;
    public JComboBox<String> section;
    public JTextField profession;

    private JPanel inputPanel;

    public InputSportsmanDialog(Presenter presenter, boolean isAdd) {
        this.presenter = presenter;
        inputPanel = new JPanel();
        stylize();

        buildFields(inputPanel, isAdd);

        add(inputPanel);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    public InputSportsmanDialog(Presenter presenter, JTable table, boolean isAdd) {
        this.presenter = presenter;
        inputPanel = new JPanel();
        stylize();

        buildFields(inputPanel, isAdd);

        add(inputPanel);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    private void stylize() {
        setLocationRelativeTo(null);
        GridLayout layout = new GridLayout(7, 2);
        layout.setVgap(5);
        layout.setHgap(5);
        inputPanel.setLayout(layout);
        setSize(300, 300);
        setModal(true);
        inputPanel.setBorder(new EmptyBorder(10, 15, 10,15));
    }

    public void buildFields(JPanel inputPanel, boolean isAdd) {
        family = new JTextField();
        inputPanel.add(new JLabel("Фамилия"));
        inputPanel.add(family);
        name = new JTextField();
        inputPanel.add(new JLabel("Имя"));
        inputPanel.add(name);
        patronymic = new JTextField();
        inputPanel.add(new JLabel("Отчество"));
        inputPanel.add(patronymic);
        section = new JComboBox<>(presenter.getSectionNames());
        inputPanel.add(new JLabel("Секция"));
        inputPanel.add(section);
        profession = new JTextField();
        inputPanel.add(new JLabel("Профессия"));
        inputPanel.add(profession);

        inputPanel.add(new JPanel());
        inputPanel.add(new JPanel());
        JButton add = new JButton("Сохранить");
        inputPanel.add(add);
        JButton cancel = new JButton("Отменить");
        inputPanel.add(cancel);
        if (isAdd) {
            add.addActionListener(e -> {
                try {
                    presenter.addSportsman(new Sportsman(family.getText(), name.getText(), patronymic.getText(), (String) section.getSelectedItem(), profession.getText()));
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, ex.getMessage(), "Ошибка", JOptionPane.ERROR_MESSAGE);
                }
            });
        }
        cancel.addActionListener(e -> {
            dispose();
        });
    }
}
