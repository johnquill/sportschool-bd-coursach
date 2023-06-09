package view.form;

import model.entity.Coach;
import presenter.Presenter;
import utils.PathChooser;
import utils.date.DateUtils;
import utils.word.WordExporter;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

public class InputCoachDialog extends JDialog {
    Presenter presenter;

    public JTextField family;
    public JTextField name;
    public JTextField patronymic;
    public JTextField sport;

    private JPanel inputPanel;
    EntityPanel entityPanel;
    JLabel createDoc;
    PathChooser pathChooser = new PathChooser(this, ".doc");
    String addDoc = "Принятие на работу";
    WordExporter addExporter = new WordExporter(
            new File("src/main/resources/documents/Принятие-на-работу.doc"));

    public InputCoachDialog(Presenter presenter, EntityPanel panel, boolean isAdd) throws Exception {
        this.presenter = presenter;
        this.entityPanel = panel;
        JPanel dialogPanel = new JPanel();
        dialogPanel.setLayout(new BorderLayout());
        inputPanel = new JPanel();
        stylize(dialogPanel);

        buildFields(inputPanel, isAdd);
        dialogPanel.add(inputPanel, BorderLayout.CENTER);
        dialogPanel.add(createDoc, BorderLayout.SOUTH);

        if (!isAdd) {
            int row = panel.table.getSelectedRow();
            if (row < 0) {
                throw new Exception("Не выбран тренер");
            }
            family.setText((String) panel.table.getValueAt(row, 1));
            name.setText((String) panel.table.getValueAt(row, 2));
            patronymic.setText((String) panel.table.getValueAt(row, 3));
            sport.setText((String) panel.table.getValueAt(row, 4));
        }

        add(dialogPanel);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
    }


    private void stylize(JPanel dialogPanel) {
        setLocationRelativeTo(null);
        GridLayout layout = new GridLayout(6, 2);
        layout.setVgap(5);
        layout.setHgap(5);
        inputPanel.setLayout(layout);
        setSize(300, 300);
        setModal(true);
        dialogPanel.setBorder(new EmptyBorder(10, 15, 10, 15));
    }

    public void buildFields(JPanel inputPanel, boolean isAdd) {
        family = new JTextField();
        inputPanel.add(new JLabel("Фамилия"));
        inputPanel.add(family);
        name = new JTextField();
        inputPanel.add(new JLabel("Имя"));
        inputPanel.add(name);
        patronymic = new JTextField();
        inputPanel.add(new JLabel("Отчество"));
        inputPanel.add(patronymic);
        sport = new JTextField();
        inputPanel.add(new JLabel("Вид спорта"));
        inputPanel.add(sport);

        createDoc = new JLabel("<html><u>Создать документ о принятии на работу</u></html>");
        createDoc.setForeground(Color.BLUE);
        createDoc.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                File file = pathChooser.choosePath(String.join
                        (" ", family.getText(), name.getText(), patronymic.getText()) + " " + addDoc);
                if (file != null) {
                    addExporter.setPath(file);
                    addExporter.open();
                    addExporter.replaceText("ДАТА", DateUtils.getCurrentDate());
                    addExporter.replaceText("ФАМИЛИЯ", family.getText());
                    addExporter.replaceText("ИМЯ", name.getText());
                    addExporter.replaceText("ОТЧЕСТВО", patronymic.getText());
                    addExporter.replaceText("ВИДСПОРТА", sport.getText());
                    addExporter.save();
                    JOptionPane.showMessageDialog(getContentPane(), "Документ успешно сохранен по пути " + file.getAbsolutePath());
                }
            }
        });
        inputPanel.add(createDoc);

        inputPanel.add(new JPanel());
        inputPanel.add(new JPanel());
        JButton add = new JButton("Сохранить");
        inputPanel.add(add);
        JButton cancel = new JButton("Отменить");
        inputPanel.add(cancel);
        if (isAdd) {
            add.addActionListener(e -> {
                try {
                    presenter.addCoach(new Coach(0, family.getText(), name.getText(), patronymic.getText(), sport.getText()));
                    entityPanel.updateTable();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, ex.getMessage(), "Ошибка", JOptionPane.ERROR_MESSAGE);
                }
                dispose();
            });
        } else {
            add.addActionListener(e -> {
                try {
                    presenter.updateCoach(
                            new Coach((Long) entityPanel.table.getValueAt(entityPanel.table.getSelectedRow(), 0),
                                    family.getText(), name.getText(), patronymic.getText(), sport.getText()));
                    entityPanel.updateTable();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, ex.getMessage(), "Ошибка", JOptionPane.ERROR_MESSAGE);
                }
                dispose();
            });
        }
        cancel.addActionListener(e -> {
            dispose();
        });
    }
}

