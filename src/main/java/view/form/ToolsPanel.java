package view.form;

import model.dao.SportsmanDaoImpl;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class ToolsPanel extends JPanel {

    JTable table;
    DefaultTableModel tableModel;

    public ToolsPanel(JTable table, DefaultTableModel tableModel, Class entity) {
        this.table = table;
        this.tableModel = tableModel;
        deleteButton();
        editButton();
        addButton();
    }

    private void addButton() {
        JButton addButton = new JButton("Добавить");
        addButton.addActionListener(e -> {

        });
        add(addButton);
    }

    private void editButton() {
        JButton addButton = new JButton("Изменить");
        addButton.addActionListener(e -> {

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
                    SportsmanDaoImpl sportsmanDao = new SportsmanDaoImpl();
                    int idx = table.getSelectedRow();
                    long id = (long) tableModel.getValueAt(idx, 0);
                    sportsmanDao.deleteById(id);
                    //TODO тут нужно быть внимательнее: если будет сделана сортировка, удалится что-то не то
                    tableModel.removeRow(idx);
                }
            } catch (ArrayIndexOutOfBoundsException ex) {
                JOptionPane.showMessageDialog(this, "Выберите удаляемую строчку", "Ошибка удаления", JOptionPane.ERROR_MESSAGE);
            }
        });
        add(addButton);
    }
}
