package view.form;

import presenter.Presenter;
import utils.PathChooser;
import utils.date.DateUtils;
import utils.word.WordExporter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

public class CoachDelDialog extends JDialog {


    String delDoc = "Увольнение";
    WordExporter delExporter = new WordExporter(
            new File("src/main/resources/documents/Увольнение.doc"));
    PathChooser pathChooser = new PathChooser(this, ".doc");

    CoachDelDialog(EntityPanel panel, Presenter presenter) {
        setLayout(new FlowLayout());
        add(new JLabel("Вы действительно хотите уволить выбранного тренера?"), BorderLayout.NORTH);
        JPanel buttonPanel = new JPanel(new GridLayout(1, 2));
        JButton yesButton = new JButton("Да");
        yesButton.addActionListener(e -> {
            int idx = panel.table.getSelectedRow();
            long id = (long) panel.tableModel.getValueAt(idx, 0);
            try {
                presenter.deleteEntityById(panel.entity, id);
                panel.updateTable();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(panel, "Ошибка при удалении:\n" + ex, "Ошибка", JOptionPane.ERROR_MESSAGE);
            }
            dispose();
        });
        JButton noButton = new JButton("Нет");
        noButton.addActionListener(e -> dispose());
        buttonPanel.add(yesButton);
        buttonPanel.add(noButton);
        add(buttonPanel, BorderLayout.CENTER);
        add(createDelDocButton(panel), BorderLayout.SOUTH);
        setSize(400, 100);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private JLabel createDelDocButton(EntityPanel panel) {
        JLabel delDocButton = new JLabel("<html><u>Создать документ об увольнении</u></html>");
        delDocButton.setForeground(Color.BLUE);
        delDocButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int idx = panel.table.getSelectedRow();
                String family = (String) panel.tableModel.getValueAt(idx, 1);
                String name = (String) panel.tableModel.getValueAt(idx, 2);
                String patronymic = (String) panel.tableModel.getValueAt(idx, 3);
                String sport = (String) panel.tableModel.getValueAt(idx, 4);
                File file = pathChooser.choosePath(
                        String.join(" ", family, name, patronymic) + " " + delDoc);
                if (file != null) {
                    delExporter.setPath(file);
                    delExporter.open();
                    delExporter.replaceText("ДАТА", DateUtils.getCurrentDate());
                    delExporter.replaceText("ФАМИЛИЯ", family);
                    delExporter.replaceText("ИМЯ", name);
                    delExporter.replaceText("ОТЧЕСТВО", patronymic);
                    delExporter.replaceText("ВИДСПОРТА", sport);
                    delExporter.save();
                    JOptionPane.showMessageDialog(getContentPane(), "Документ успешно сохранен по пути " + file.getAbsolutePath());
                }
            }
        });
        return delDocButton;
    }
}
