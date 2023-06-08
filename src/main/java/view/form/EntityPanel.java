package view.form;

import presenter.Presenter;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseEvent;

public class EntityPanel extends JPanel {

    Presenter presenter;
    DefaultTableModel tableModel;
    JTable table;
    Class entity;

    //TODO: Возможно сюда впихнуть GENERIC
    public EntityPanel(Presenter presenter, Class entity) {
        this.presenter = presenter;
        this.entity = entity;
        setLayout(new BorderLayout());
        tableModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int i, int i1) {
                return false;
            }
        };
        try {
            updateTable();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Ошибка", JOptionPane.ERROR_MESSAGE);
        }
        table = new JTable(tableModel) {
            public String getToolTipText(MouseEvent e) {
                String tip = null;
                java.awt.Point p = e.getPoint();
                int rowIndex = rowAtPoint(p);
                int colIndex = columnAtPoint(p);

                try {
                    tip = getValueAt(rowIndex, colIndex).toString();
                } catch (RuntimeException e1) {
                    //catch null pointer exception if mouse is over an empty line
                }
                return tip;
            }
        };

        ToolsPanel tools = new ToolsPanel(this, entity, presenter);
        add(tools, BorderLayout.NORTH);

        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);
    }

    public void updateTable() throws Exception {
        String[] headers = presenter.getTableHeaders(entity);
        Object[][] data = presenter.getTableData(entity);
        tableModel.setDataVector(data, headers);
    }

}
