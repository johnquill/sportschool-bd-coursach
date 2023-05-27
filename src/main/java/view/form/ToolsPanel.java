package view.form;

import presenter.Presenter;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class ToolsPanel extends JPanel {

    Presenter presenter;

    EntityPanel panel;


    public ToolsPanel(EntityPanel panel, Class entity, Presenter presenter) {
        this.panel = panel;
        this.presenter = presenter;
        deleteButton();
        editButton();
        addButton();
    }

    private void addButton() {
        JButton addButton = new JButton("Добавить");
        addButton.addActionListener(e -> {
            new InputSportsmanDialog(presenter, true);
        });
        add(addButton);
    }

    private void editButton() {
        JButton addButton = new JButton("Изменить");
        addButton.addActionListener(e -> {
            try {
                InputSportsmanDialog dialog = new InputSportsmanDialog(presenter, panel, false);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Ошибка", JOptionPane.ERROR_MESSAGE);
            }
        });
        add(addButton);
    }

    private void deleteButton() {
        JButton addButton = new JButton("Удалить");
        addButton.addActionListener(e -> {
            try {
                int result = JOptionPane.showConfirmDialog(this, "Вы действительно хотите удалить " +
                        " имя фамилия отчетсво" + "?", "Удаление", JOptionPane.YES_NO_OPTION);
                if (result == JOptionPane.YES_OPTION) {
                    int idx = panel.table.getSelectedRow();
                    long id = (long) panel.tableModel.getValueAt(idx, 0);
                    presenter.deleteSportsmanById(id);
                    panel.updateTable();
                }
            } catch (ArrayIndexOutOfBoundsException ex) {
                JOptionPane.showMessageDialog(this, "Выберите удаляемую строчку", "Ошибка удаления", JOptionPane.ERROR_MESSAGE);
            }
        });
        add(addButton);
    }
}
