package model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Table;


@Entity
@Table(name = "sportsman")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Sportsman {

    @javax.persistence.Id
    @GeneratedValue
    private Long id;

    private String name;
    private String family;
    private String patronymic;

    //@ManyToMany
    //@JoinColumn(name="section_id")
    //private List<Section> section;
}
