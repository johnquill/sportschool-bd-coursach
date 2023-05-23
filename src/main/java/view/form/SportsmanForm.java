package view.form;

import presenter.Presenter;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class SportsmanForm extends JPanel {

    public SportsmanForm(Presenter presenter) {

        setLayout(new BorderLayout());

        DefaultTableModel tableModel = new DefaultTableModel();
        String[] headers = presenter.getSportsmanHeaders();
        Object[][] data = presenter.getSportsmanData();
        tableModel.setDataVector(data, headers);
        JTable table = new JTable(tableModel);

        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);
    }
}
