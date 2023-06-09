package view.form;

import model.entity.Coach;
import presenter.Presenter;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class InputCoachDialog extends JDialog {
    Presenter presenter;

    public JTextField family;
    public JTextField name;
    public JTextField patronymic;
    public JTextField sport;

    private JPanel inputPanel;
    EntityPanel entityPanel;

    public InputCoachDialog(Presenter presenter, EntityPanel panel, boolean isAdd) throws Exception {
        this.presenter = presenter;
        this.entityPanel = panel;
        inputPanel = new JPanel();
        stylize();

        buildFields(inputPanel, isAdd);

        if (!isAdd) {
            int row = panel.table.getSelectedRow();
            if (row < 0) {
                throw new Exception("Не выбран тренер");
            }
            family.setText((String) panel.table.getValueAt(row, 1));
            name.setText((String) panel.table.getValueAt(row, 2));
            patronymic.setText((String) panel.table.getValueAt(row, 3));
            sport.setText((String) panel.table.getValueAt(row, 5));
        }

        add(inputPanel);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    private void stylize() {
        setLocationRelativeTo(null);
        GridLayout layout = new GridLayout(6, 2);
        layout.setVgap(5);
        layout.setHgap(5);
        inputPanel.setLayout(layout);
        setSize(300, 300);
        setModal(true);
        inputPanel.setBorder(new EmptyBorder(10, 15, 10, 15));
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
        sport = new JTextField();
        inputPanel.add(new JLabel("Вид спорта"));
        inputPanel.add(sport);

        inputPanel.add(new JPanel());
        inputPanel.add(new JPanel());
        JButton add = new JButton("Сохранить");
        inputPanel.add(add);
        JButton cancel = new JButton("Отменить");
        inputPanel.add(cancel);
        if (isAdd) {
            add.addActionListener(e -> {
                try {
                    presenter.addCoach(new Coach(0, family.getText(), name.getText(), patronymic.getText(), sport.getText()));
                    entityPanel.updateTable();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, ex.getMessage(), "Ошибка", JOptionPane.ERROR_MESSAGE);
                }
                dispose();
            });
        } else {
            add.addActionListener(e -> {
                try {
                    presenter.updateCoach(
                            new Coach((Long) entityPanel.table.getValueAt(entityPanel.table.getSelectedRow(), 0),
                                    family.getText(), name.getText(), patronymic.getText(), sport.getText()));
                    entityPanel.updateTable();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, ex.getMessage(), "Ошибка", JOptionPane.ERROR_MESSAGE);
                }
                dispose();
            });
        }
        cancel.addActionListener(e -> {
            dispose();
        });
    }
}

