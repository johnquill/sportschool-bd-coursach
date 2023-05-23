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

    private long sectionId;

    private long professionId;
}
