package view.form;

import presenter.Presenter;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

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
        updateTable();
        table = new JTable(tableModel);

        ToolsPanel tools = new ToolsPanel(this, entity, presenter);
        add(tools, BorderLayout.NORTH);

        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);
    }

    public void updateTable() {
        String[] headers = presenter.getTableHeaders(entity);
        Object[][] data = presenter.getTableData(entity);
        tableModel.setDataVector(data, headers);
    }

}
