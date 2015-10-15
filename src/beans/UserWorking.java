package beans;

import java.io.IOException;
import java.sql.SQLException;

public interface UserWorking {
    //TODO шифровать пароли.
    Boolean isAuthenticate(String login, String pass) throws SQLException;
    Integer getUserId(String login) throws SQLException;
    Integer addUser(String login, String pass) throws SQLException;
    String getUserLogin(Integer id) throws SQLException;
    void saveUsers(String direct) throws IOException, SQLException;
}
