package beans;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public interface TypeWorking {
    void saveTypeXML(String direct) throws IOException, SQLException;
    void saveTypeXML(ResultSet resultSet, String direct) throws IOException, SQLException;
}
