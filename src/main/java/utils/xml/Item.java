package utils.xml;

import jakarta.xml.bind.annotation.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@XmlAccessorType(XmlAccessType.FIELD)
@Getter
@Setter
public class Item {

    @XmlElement(name = "Name")
    private String name;

    @XmlElementWrapper(name = "Items")
    @XmlElement(name = "Item")
    private ArrayList<Item> children;

    @XmlElement(name = "Panel")
    private String panelClass;

}
