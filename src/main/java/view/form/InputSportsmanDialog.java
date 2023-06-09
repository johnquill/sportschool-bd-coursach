package view.form;

import model.entity.Sportsman;
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

public class InputSportsmanDialog extends JDialog {
    
    Presenter presenter;

    public JTextField family;
    public JTextField name;
    public JTextField patronymic;
    public JComboBox<String> section;
    public JTextField profession;

    private final JPanel inputPanel;
    EntityPanel entityPanel;
    PathChooser pathChooser = new PathChooser(this, ".doc");
    String docName = "Договор зачисления в секцию";
    WordExporter wordExporter = new WordExporter(
            new File("src/main/resources/documents/Зачисление-в-секцию.doc"));
    JLabel createDoc;

    public InputSportsmanDialog(Presenter presenter, EntityPanel panel, boolean isAdd) throws Exception {
        this.presenter = presenter;
        this.entityPanel = panel;
        inputPanel = new JPanel(new BorderLayout());
        JPanel fieldPanel = new JPanel();
        stylize(fieldPanel);

        buildFields(fieldPanel, isAdd);

        inputPanel.add(fieldPanel, BorderLayout.CENTER);
        inputPanel.add(createDoc, BorderLayout.SOUTH);

        if (!isAdd) {
            int row = panel.table.getSelectedRow();
            if (row < 0) {
                throw new Exception("Не выбран спортсмен");
            }
            family.setText((String) panel.table.getValueAt(row, 1));
            name.setText((String) panel.table.getValueAt(row, 2));
            patronymic.setText((String) panel.table.getValueAt(row, 3));
            section.setSelectedItem(panel.table.getValueAt(row, 4));
            profession.setText((String) panel.table.getValueAt(row, 5));
        }

        add(inputPanel);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    private void stylize(JPanel fieldPanel) {
        setLocationRelativeTo(null);
        GridLayout layout = new GridLayout(7, 2);
        layout.setVgap(5);
        layout.setHgap(5);
        fieldPanel.setLayout(layout);
        setSize(300, 300);
        setModal(true);
        inputPanel.setBorder(new EmptyBorder(10, 15, 10,15));
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
        section = new JComboBox<>(presenter.getSectionNames());
        inputPanel.add(new JLabel("Секция"));
        inputPanel.add(section);
        profession = new JTextField();
        inputPanel.add(new JLabel("Профессия"));
        inputPanel.add(profession);
        inputPanel.add(new JPanel());
        inputPanel.add(new JPanel());

        createDoc = new JLabel("<html><u>Создать документ о зачислении</u></html>");
        createDoc.setForeground(Color.BLUE);
        createDoc.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                File file = pathChooser.choosePath(String.join
                        (" ", family.getText(), name.getText(), patronymic.getText()) + " " + docName);
                if (file != null) {
                    wordExporter.setPath(file);
                    wordExporter.open();
                    wordExporter.replaceText("ДАТА", DateUtils.getCurrentDate());
                    wordExporter.replaceText("ФАМИЛИЯ", family.getText());
                    wordExporter.replaceText("ИМЯ", name.getText());
                    wordExporter.replaceText("ОТЧЕСТВО", patronymic.getText());
                    wordExporter.replaceText("СЕКЦИЯ", (String) section.getSelectedItem());
                    wordExporter.save();
                    JOptionPane.showMessageDialog(getContentPane(), "Документ успешно сохранен по пути " + file.getAbsolutePath());
                }
            }
        });
        inputPanel.add(createDoc);
        JButton add = new JButton("Сохранить");
        inputPanel.add(add);
        JButton cancel = new JButton("Отменить");
        inputPanel.add(cancel);
        if (isAdd) {
            add.addActionListener(e -> {
                try {
                    presenter.addSportsman(new Sportsman(family.getText(), name.getText(), patronymic.getText(), (String) section.getSelectedItem(), profession.getText()));
                    entityPanel.updateTable();
                    dispose();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, ex.getMessage(), "Ошибка", JOptionPane.ERROR_MESSAGE);
                }
                dispose();
            });
        } else {
            add.addActionListener(e -> {
                try {
                    presenter.updateSportsman(
                            new Sportsman((Long) entityPanel.table.getValueAt(entityPanel.table.getSelectedRow(), 0),
                                    family.getText(), name.getText(), patronymic.getText(),
                                    (String) section.getSelectedItem(), profession.getText()));
                    entityPanel.updateTable();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, ex.getMessage(), "Ошибка", JOptionPane.ERROR_MESSAGE);
                }
                dispose();
            });
        }
        cancel.addActionListener(e -> dispose());
    }
}
