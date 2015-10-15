package beans;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public interface CommentsWorking {
    ResultSet selectComments() throws SQLException;
    ResultSet selectTraditionComments(Integer traditionId) throws SQLException;
    void saveCommentsXML(ResultSet resultSet, String direct) throws IOException, SQLException;
}
