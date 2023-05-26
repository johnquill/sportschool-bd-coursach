package view.form;

import presenter.Presenter;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class ToolsPanel extends JPanel {

    JTable table;
    DefaultTableModel tableModel;
    Presenter presenter;


    public ToolsPanel(JTable table, DefaultTableModel tableModel, Class entity, Presenter presenter) {
        this.presenter = presenter;
        this.table = table;
        this.tableModel = tableModel;
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
            InputSportsmanDialog dialog = new InputSportsmanDialog(presenter, table, false);
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
                    int idx = table.getSelectedRow();
                    long id = (long) tableModel.getValueAt(idx, 0);
                    presenter.deleteSportsmanById(id);
                    //TODO тут нужно быть внимательнее: если будет сделана сортировка, удалится что-то не то
                    //TODO возможно стоит просто удалять из базы и перезагружать таблицу
                    tableModel.removeRow(idx);
                }
            } catch (ArrayIndexOutOfBoundsException ex) {
                JOptionPane.showMessageDialog(this, "Выберите удаляемую строчку", "Ошибка удаления", JOptionPane.ERROR_MESSAGE);
            }
        });
        add(addButton);
    }
}
