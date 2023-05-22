package utils.xml;

import jakarta.xml.bind.annotation.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@XmlRootElement(name = "Menu")
@XmlAccessorType(XmlAccessType.FIELD)
@Getter
@Setter
public class Menu {

    @XmlElementWrapper(name = "Items")
    @XmlElement(name="Item")
    private ArrayList<Item> items = new ArrayList<>();

}
