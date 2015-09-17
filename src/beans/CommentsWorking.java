package beans;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by iocz on 12/10/15.
 */
public interface CommentsWorking {
    ResultSet selectComments() throws SQLException;
    ResultSet selectTraditionComments(Integer traditionId) throws SQLException;
    void saveCommentsXML(ResultSet resultSet, String direct) throws IOException, SQLException;
}
