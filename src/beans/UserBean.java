package beans;

import func.Calendar;
import func.DataBaseVoids;
import func.FileWorking;
import models.User;
import org.jdom2.Document;
import org.jdom2.Element;

import javax.ejb.Stateless;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

@Stateless
public class UserBean implements UserWorking {
    @Override
    public Boolean isAuthenticate(String login, String pass) throws SQLException {
        ResultSet rs = selectUser(pass, login);
        if (rs.next()) {
            Calendar.setUserId(Integer.parseInt(rs.getString("ID")));
            return true;
        }
        return false;
    }

    private ResultSet selectUser(String pass, String login) throws SQLException {
        DataBaseVoids.main();
        PreparedStatement stmnt = DataBaseVoids.con.prepareStatement("SELECT ID FROM USERLIST WHERE LOGIN='" + login + "' AND PASS='" + pass + "'");
        return stmnt.executeQuery();
    }

    private ResultSet getUsers() throws SQLException {
        DataBaseVoids.main();
        PreparedStatement stmnt = DataBaseVoids.con.prepareStatement("SELECT * FROM USERLIST");
        return stmnt.executeQuery();
    }

    public Integer getUserId(String login) throws SQLException {
        DataBaseVoids.main();
        PreparedStatement stmnt = DataBaseVoids.con.prepareStatement("SELECT ID FROM USERLIST WHERE LOGIN = '" +
                login + "'");
        return DataBaseVoids.getIntegerValue(stmnt.executeQuery());
    }

    public Integer addUser(String login, String pass) throws SQLException {
        return insertUser(login, pass);
    }

    public String getUserLogin(Integer id) throws SQLException {
        PreparedStatement stmnt = DataBaseVoids.con.prepareStatement("SELECT LOGIN FROM USERLIST WHERE ID = '" +
                id + "'");
        return DataBaseVoids.getStringValue(stmnt.executeQuery());
    }

    //TODO разбить на меньшие модули и пререписать
    private Integer insertUser(String login, String pass) throws SQLException {
        DataBaseVoids.main();
        Statement stmnt = DataBaseVoids.con.createStatement();

        //TODO fix after change db.
        int adressId = 11;//getAdressId(stmnt);
        //insertAdress(stmnt, adressId);

        int userDataId = getUserDataId(stmnt);
        insertUserData(stmnt, userDataId);

        int userListId = getUserListId(stmnt);
        insertUserListValue(stmnt, userListId, login, pass, userDataId);

        return userDataId;
    }

    private Integer getAdressId(Statement stmnt) throws SQLException{
        ResultSet rsAdress = stmnt.executeQuery("SELECT ADRESS_SEQ.nextval from DUAL");
        int adressId = 0;
        if (rsAdress.next()) adressId = rsAdress.getInt(1);
        return adressId;
    }

    private void insertAdress(Statement stmnt, Integer adressId) throws SQLException{
        stmnt.execute("INSERT INTO ADRESS (ID,COUNTRY_ID, STREET, HOUSE) VALUES ('" +
                adressId + "' ,'1', 'Not defined', '0')");
    }

    private Integer getUserDataId(Statement stmnt) throws SQLException{
        ResultSet rsUserData = stmnt.executeQuery("SELECT USERDATA_SEQ.nextval FROM DUAL");
        int userDataId = 0;
        if(rsUserData.next()) userDataId = rsUserData.getInt(1);
        return userDataId;
    }

    private void insertUserData(Statement stmnt, Integer userDataId) throws SQLException{
        //TODO stmnt.execute("INSERT into USERDATA (ID, NAME, SURNAME, ADRESS_ID, STATUS_ID) VALUES ('" + userDataId + "', 'Not defined', 'Not defined','" + adressId + "', '2')");
        stmnt.execute("INSERT INTO USERDATA (ID, NAME, SURNAME, ADRESS_ID, STATUS_ID) VALUES ('" +
                userDataId + "', 'Not defined', 'Not defined','11', '2')");
    }

    private Integer getUserListId(Statement stmnt) throws SQLException{
        ResultSet rsUserList = stmnt.executeQuery("SELECT USERLIST_SEQ.nextval FROM DUAL");
        int userListId = 0;
        if(rsUserList.next()) userListId = rsUserList.getInt(1);
        return userListId;
    }

    private void insertUserListValue(Statement stmnt, Integer userListId, String login,
                                     String pass, Integer userDataId) throws SQLException{
        stmnt.execute("INSERT INTO USERLIST (ID, LOGIN, PASS, DATAID) VALUES ('" + userListId +
                "', \'" + login + "\', \'" + pass + "\', '" + userDataId + "')");
    }

    public void saveUsers(String direct) throws IOException, SQLException {
        Element root = new Element("userSave");
        Document doc = new Document(root);
        ResultSet resultSet = getUsers();
        while (resultSet.next()) {
            Element userElement = new Element("user");
            Element userId = new Element("userId");
            Element userName = new Element("userName");
            Element userPass = new Element("userPass");

            userId.setText(resultSet.getString("ID"));
            userName.setText(resultSet.getString("LOGIN"));
            userPass.setText(resultSet.getString("PASS"));
            //userName.setText(user.getLogin());
            //userPass.setText(user.getPass().toString());
            userElement.addContent(userName);
            userElement.addContent(userPass);
            root.addContent(userElement);
            FileWorking.writeXml(doc, direct);
        }
    }
}
