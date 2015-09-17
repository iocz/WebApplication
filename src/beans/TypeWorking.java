package beans;

import models.Holiday;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

/**
 * Created by iocz on 12/10/15.
 */
public interface TypeWorking {
    void saveTypeXML(String direct) throws IOException, SQLException;
    void saveTypeXML(ResultSet resultSet, String direct) throws IOException, SQLException;
}
