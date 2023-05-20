package model.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Table;

@Entity
@Table(name = "profession")
@Getter
@Setter
public class Profession {

    @javax.persistence.Id
    @GeneratedValue
    private Long id;

    private String name;
}
