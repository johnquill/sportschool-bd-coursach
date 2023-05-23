package view.form;

import presenter.Presenter;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class EntityPanel extends JPanel {

    //TODO: Возможно сюда впихнуть GENERIC
    public EntityPanel(Presenter presenter, Class entity) {
        setLayout(new BorderLayout());
        DefaultTableModel tableModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int i, int i1) {
                return false;
            }
        };
        String[] headers = presenter.getTableHeaders(entity);
        Object[][] data = presenter.getTableData(entity);
        tableModel.setDataVector(data, headers);
        JTable table = new JTable(tableModel);

        ToolsPanel tools = new ToolsPanel(table, tableModel, entity);
        add(tools, BorderLayout.NORTH);

        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);
    }


}
