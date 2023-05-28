package model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import model.Column;

import javax.swing.*;
import java.util.Arrays;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Section {

    public static final List<Column> cols = Arrays.asList(
            new Column("id", "Ид", long.class, false),
            new Column("name", "Название", String.class, true),
            new Column("family", "График", String.class, true),
            new Column("name", "Зал", String.class, true),
            new Column("patronymic", "Описание", String.class, true),
            new Column("section", "Статус работы", JComboBox.class, true),
            new Column("profession", "Спорт", String.class, true),
            new Column("profession", "Тренер", String.class, true)
    );

    private long id;

    private String schedule;

    private int room;

    private String description;

    private Boolean is_working;

    private String sport;

    private String coach;
}
