package view.form;

import model.Column;
import model.entity.EntityBuilder;
import presenter.Presenter;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class InputEntityDialog extends JDialog {

    Presenter presenter;

    private JPanel inputPanel;
    EntityPanel entityPanel;

    public InputEntityDialog(Presenter presenter, EntityPanel panel, boolean isAdd) throws Exception {
        this.presenter = presenter;
        this.entityPanel = panel;
        inputPanel = new JPanel();
        stylize();

        buildFields(inputPanel, entityPanel.entity);
        int row = panel.table.getSelectedRow();
        if (!isAdd) {
            if (row < 0) {
                throw new Exception("Не выбрана Секция");
            }
            //family.setText((String) panel.table.getValueAt(row, 1));
            //name.setText((String) panel.table.getValueAt(row, 2));
            //patronymic.setText((String) panel.table.getValueAt(row, 3));
            //section.setSelectedItem(panel.table.getValueAt(row, 4));
            //profession.setText((String) panel.table.getValueAt(row, 5));
        }

        add(inputPanel);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
    }


    private void buildFields(JPanel inputPanel, Class entity) {
        try {
            java.util.List<Column> columns = (java.util.List<Column>) entity.getField("cols").get(null);
            List<Component> inputs = new ArrayList<>();
            columns.forEach(column -> {
                if (column.isEditable()) {
                    inputPanel.add(new JLabel(column.getRusName()));
                    if (JComboBox.class.equals(column.getType())) {
                        JComboBox comboBox = new JComboBox(presenter.getVariants(column.getReferenceTable(), column.getReferenceCol()));
                        inputs.add(comboBox);
                        inputPanel.add(comboBox);
                    } else {
                        JTextField textField = new JTextField();
                        inputs.add(textField);
                        inputPanel.add(textField);
                    }
                }
            });
            JButton add = new JButton("Сохранить");
            inputPanel.add(add);
            add.addActionListener(e -> {
                try {
                    presenter.addEntity(entity,
                            EntityBuilder.buildNewEntity(entity, inputs.stream().map(el -> getValue(el)).toArray()));
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
                entityPanel.updateTable();
            });

            JButton cancel = new JButton("Отменить");
            inputPanel.add(cancel);
            cancel.addActionListener(e -> dispose());
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private String getValue(Component el) {
        if (el instanceof JComboBox) {
            return (String) ((JComboBox<String>) el).getSelectedItem();
        } else if (el instanceof JTextField) {
            return ((JTextField) el).getText();
        }
        return "";
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
}
