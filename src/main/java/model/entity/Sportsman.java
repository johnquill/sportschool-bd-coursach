package model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Sportsman {

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
}
