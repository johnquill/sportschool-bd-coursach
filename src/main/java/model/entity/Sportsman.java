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

    private String family;

    private String name;

    private String patronymic;

    private String section;

    private String profession;

    public Sportsman(String family, String name, String patronymic, String section, String profession) {
        this.family = family;
        this.name = name;
        this.patronymic = patronymic;
        this.section = section;
        this.profession = profession;
    }
}
