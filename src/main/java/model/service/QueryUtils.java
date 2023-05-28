package model.service;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class QueryUtils {


    public static Object[] getColValues(String referenceTable, String colName) {
        try {
            Connection connection =
                    DriverManager.getConnection("jdbc:mysql://localhost:3306/sportschool", "admin",
                            System.getenv("PASSW"));
            Statement statement = connection.createStatement();
            ResultSet set = statement.executeQuery(String.format("select distinct %s from %s", colName, referenceTable));
            List<Object> result = new ArrayList<>();
            while (set.next()) {
                result.add(set.getObject(colName));
            }
            return result.toArray();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
