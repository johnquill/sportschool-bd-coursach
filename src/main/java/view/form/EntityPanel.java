package view.form;

import model.entity.Section;
import presenter.Presenter;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.stream.Stream;

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
        if (entity == Section.class) {
            JPanel upPanel = new JPanel();
            upPanel.setLayout(new GridLayout(2, 1));
            upPanel.add(tools);
            JComboBox<String> trainer = new JComboBox<>(
                    Stream.concat(Arrays.stream(new String[]{"Все тренера"}), Arrays.stream(presenter.getTrainers()))
                            .toArray(size -> (String[]) Array.newInstance(String.class, size)));
            trainer.setSelectedItem(1);
            TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(tableModel);
            table.setRowSorter(sorter);
            trainer.addActionListener(e -> {
                if (trainer.getSelectedIndex() != 0) {
                    RowFilter<DefaultTableModel, Object> rf = null;
                    //If current expression doesn't parse, don't update.
                    try {
                        rf = RowFilter.regexFilter((String) trainer.getSelectedItem(), 7);
                    } catch (java.util.regex.PatternSyntaxException ex) {
                        return;
                    }
                    sorter.setRowFilter(rf);
                } else {
                    sorter.setRowFilter(null);
                }
            });
            upPanel.add(trainer);
            add(upPanel, BorderLayout.NORTH);
        } else {
            add(tools, BorderLayout.NORTH);
        }

        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);
    }

    public void updateTable() throws Exception {
        String[] headers = presenter.getTableHeaders(entity);
        Object[][] data = presenter.getTableData(entity);
        tableModel.setDataVector(data, headers);
    }

}
