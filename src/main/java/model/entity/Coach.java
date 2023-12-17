package model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Coach extends Entity {

    private long id;

    private String surname;

    private String name;

    private String patronymic;

    private String sport;
}