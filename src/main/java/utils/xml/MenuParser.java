package utils.xml;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;

import java.io.File;

public class MenuParser {

    public static Menu parseToObject(File file) throws JAXBException {
        JAXBContext jc = JAXBContext.newInstance(Menu.class);
        Unmarshaller unmarshaller = jc.createUnmarshaller();
        return (Menu) unmarshaller.unmarshal(file);
    }
}
