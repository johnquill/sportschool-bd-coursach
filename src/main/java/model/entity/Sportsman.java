package model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Sportsman {

    private Long id;

    private String name;

    private String family;

    private String patronymic;

    private List<Section> section;

    private Profession profession;
}
