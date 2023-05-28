package model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Column {

    private String dbName;
    private String rusName;
    private String referenceTable;
    private String referenceCol;
    private Class type;
    private boolean isEditable;

    public Column(String dbName, String rusName, Class type, boolean isEditable) {
        this.dbName = dbName;
        this.rusName = rusName;
        this.type = type;
        this.isEditable = isEditable;
    }
}
