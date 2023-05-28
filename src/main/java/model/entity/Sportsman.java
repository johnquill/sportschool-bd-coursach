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
@NoArgsConstructor
@AllArgsConstructor
public class Sportsman {

    public static final List<Column> cols = Arrays.asList(
            new Column("id", "Ид", long.class, false),
            new Column("family", "Фамилия", String.class, true),
            new Column("name", "Имя", String.class, true),
            new Column("patronymic", "Отчество", String.class, true),
            new Column("section", "Секция", "section", "name", JComboBox.class, true),
            new Column("profession", "Профессия", String.class, true)
    );

    private long id;

    private String surname;

    private String name;

    private String patronymic;

    private String section;

    private String profession;

    public Sportsman(String surname, String name, String patronymic, String section, String profession) {
        this.surname = surname;
        this.name = name;
        this.patronymic = patronymic;
        this.section = section;
        this.profession = profession;
    }

    public Sportsman(Object[] array) {
        this.surname = (String) array[0];
        this.name = (String) array[1];
        this.patronymic = (String) array[2];
        this.section = (String) array[3];
        this.profession = (String) array[4];
    }
}
