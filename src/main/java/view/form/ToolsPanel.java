package view.form;

import model.entity.Coach;
import presenter.Presenter;

import javax.swing.*;
import java.awt.*;

public class ToolsPanel extends JPanel {

    Presenter presenter;

    EntityPanel panel;
    Class entity;


    public ToolsPanel(EntityPanel panel, Class entity, Presenter presenter) {
        this.entity = entity;
        this.panel = panel;
        this.presenter = presenter;
        deleteButton();
        editButton();
        addButton();
    }

    private void addButton() {
        JButton addButton = new JButton("Добавить");
        addButton.addActionListener(e -> {
            /// TODO: 28.05.2023 Тут не должно быть так что в идеале надо избавлять переделыванием архитектуры
            // возможно нужно просто перехватывать раньше
            try {
                InputDialogBuilder.buildInputEntity(presenter, panel, true);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });
        add(addButton);
    }

    private void editButton() {
        JButton addButton = new JButton("Изменить");
        addButton.addActionListener(e -> {
            try {
                InputDialogBuilder.buildInputEntity(presenter, panel, false);
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
                if (entity != Coach.class) {
                    int result = JOptionPane.showOptionDialog(this,
                            "Вы действительно хотите удалить выделенную запись", "Удаление", JOptionPane.YES_NO_OPTION,
                            JOptionPane.QUESTION_MESSAGE, (Icon) null, new String[] {"Да", "Нет"}, "Yes");
                    if (result == JOptionPane.YES_OPTION) {
                        int idx = panel.table.getSelectedRow();
                        long id = (long) panel.tableModel.getValueAt(idx, 0);
                        presenter.deleteEntityById(entity, id);
                        panel.updateTable();
                    }
                } else {
                    new CoachDelDialog(panel, presenter);
                }
            } catch (ArrayIndexOutOfBoundsException ex) {
                JOptionPane.showMessageDialog(this, "Выберите удаляемую строчку", "Ошибка удаления", JOptionPane.ERROR_MESSAGE);
            } catch (Exception er) {
                JOptionPane.showMessageDialog(this, er.getMessage(), "Ошибка", JOptionPane.ERROR_MESSAGE);
            }
        });
        add(addButton);
    }
}
