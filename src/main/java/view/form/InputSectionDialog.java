package view.form;

import model.entity.Section;
import presenter.Presenter;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class InputSectionDialog extends JDialog {

    Presenter presenter;

    public JTextField name;
    public JTextField schedule;
    public JTextField room;
    private JTextArea description;
    private JComboBox<String> is_working = new JComboBox<>(new String[] {"Работает", "Не работает"});
    private JTextField sport;
    private JComboBox<String> coach;

    private JPanel inputPanel;
    EntityPanel entityPanel;

    public InputSectionDialog(Presenter presenter, EntityPanel panel, boolean isAdd) throws Exception {
        this.presenter = presenter;
        this.entityPanel = panel;
        inputPanel = new JPanel();
        stylize();

        buildFields(inputPanel, isAdd);

        if (!isAdd) {
            int row = panel.table.getSelectedRow();
            if (row < 0) {
                throw new Exception("Не выбрана секция");
            }
            name.setText((String) panel.table.getValueAt(row, 1));
            schedule.setText((String) panel.table.getValueAt(row, 2));
            room.setText(String.valueOf(panel.table.getValueAt(row, 3)));
            description.setText((String) panel.table.getValueAt(row, 4));
            is_working.setSelectedItem(panel.table.getValueAt(row, 5));
            sport.setText((String) panel.table.getValueAt(row, 6));
            coach.setSelectedItem(panel.table.getValueAt(row, 7));
        }

        add(inputPanel);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    private void stylize() {
        setLocationRelativeTo(null);
        GridLayout layout = new GridLayout(9, 2);
        layout.setVgap(5);
        layout.setHgap(5);
        inputPanel.setLayout(layout);
        setSize(300, 300);
        setModal(true);
        inputPanel.setBorder(new EmptyBorder(10, 15, 10,15));
    }

    public void buildFields(JPanel inputPanel, boolean isAdd) {
        name = new JTextField();
        inputPanel.add(new JLabel("Название"));
        inputPanel.add(name);
        schedule = new JTextField();
        inputPanel.add(new JLabel("Расписание"));
        inputPanel.add(schedule);
        room = new JTextField();
        inputPanel.add(new JLabel("Зал"));
        inputPanel.add(room);
        description = new JTextArea();
        description.setLineWrap(true);
        inputPanel.add(new JLabel("Описание"));
        inputPanel.add(description);
        inputPanel.add(new JLabel("Статус работы"));
        inputPanel.add(is_working);
        sport = new JTextField();
        inputPanel.add(new JLabel("Вид спорта"));
        inputPanel.add(sport);
        coach = new JComboBox<>(presenter.getTrainers());
        inputPanel.add(new JLabel("Тренер"));
        inputPanel.add(coach);

        inputPanel.add(new JPanel());
        inputPanel.add(new JPanel());
        JButton add = new JButton("Сохранить");
        inputPanel.add(add);
        JButton cancel = new JButton("Отменить");
        inputPanel.add(cancel);
        if (isAdd) {
            add.addActionListener(e -> {
                try {
                    presenter.addSection(new Section((Long) null, name.getText(), schedule.getText(), Integer.parseInt(room.getText()), description.getText(),
                            is_working.getSelectedIndex() == 0, sport.getText(), (String) coach.getSelectedItem()));
                    entityPanel.updateTable();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, ex.getMessage(), "Ошибка", JOptionPane.ERROR_MESSAGE);
                }
            });
        } else {
            add.addActionListener(e -> {
                try {
                    presenter.updateSection(new Section((Long) entityPanel.table.getValueAt(entityPanel.table.getSelectedRow(), 0),
                            name.getText(), schedule.getText(), Integer.parseInt(room.getText()), description.getText(),
                            is_working.getSelectedIndex() == 0, sport.getText(), (String) coach.getSelectedItem()));
                    entityPanel.updateTable();
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
