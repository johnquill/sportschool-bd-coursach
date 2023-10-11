package model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Section extends Entity {
    private long id;

    private String name;

    private String schedule;

    private int room;

    private String description;

    private Boolean is_working;

    private String sport;

    private String[] coach;
}
