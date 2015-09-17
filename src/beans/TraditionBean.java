package beans;

import func.DataBaseVoids;
import func.FileWorking;
import models.Country;
import models.Holiday;
import models.Tradition;
import org.jdom2.Document;
import org.jdom2.Element;

import javax.ejb.Stateless;
import javax.xml.crypto.Data;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by iocz on 07/10/15.
 */
@Stateless
public class TraditionBean implements TraditionWorking {
    private ResultSet selectUserTraditions(int userID) throws SQLException {
        DataBaseVoids.main();
        PreparedStatement stmnt = DataBaseVoids.con.prepareStatement("SELECT * FROM TRADITION WHERE USER_ID = " + userID);
        return stmnt.executeQuery();
    }

    public ResultSet selectAllTraditions() throws SQLException {
        DataBaseVoids.main();
        PreparedStatement stmnt = DataBaseVoids.con.prepareStatement("SELECT * FROM TRADITION");
        return stmnt.executeQuery();
    }

    public void insertIntoTradition(int countryId, int holidayId, int userId, String description, String imgUrl) throws SQLException{
        DataBaseVoids.main();
        String request = "INSERT INTO TRADITION (ID, COUNTRY_ID, " +
                "HOLIDAY_ID, USER_ID, DESCRIPTION, IMG_URL) VALUES (tradition_seq.nextval, '" + countryId +
                "', '" + holidayId + "', '" +  userId + "', '" + description + "', '" + imgUrl +"')";
        PreparedStatement stmnt = DataBaseVoids.con.prepareStatement(request);
        stmnt.executeQuery();
    }

    public void insertTradition(int countryId, int holidayId, int userId, String description, String imgUrl) throws SQLException {
        insertIntoTradition(countryId, holidayId, userId, description, imgUrl);
        int traditionId = getLastTraditionID();
        insertIntoStatistic(traditionId);
    }

    public void updateTradition(int traditionId, int countryId, int holidayId, String description, String imgUrl) throws SQLException {
        DataBaseVoids.main();
        String request = "UPDATE TRADITION SET COUNTRY_ID = '" + countryId + "', HOLIDAY_ID = '" +
                holidayId + "', DESCRIPTION = '" + description + "', IMG_URL = '" + imgUrl + "' WHERE " +
                "ID = '" + traditionId + "'";
        PreparedStatement stmnt = DataBaseVoids.con.prepareStatement(request);
        stmnt.executeQuery();
    }

    public int getLastTraditionID() throws SQLException{
        DataBaseVoids.main();
        String request = "SELECT MAX(ID) FROM TRADITION";
        PreparedStatement stmnt = DataBaseVoids.con.prepareStatement(request);
        return DataBaseVoids.getIntegerValue(stmnt.executeQuery());
    }

    public void deleteTradition(int traditionId) throws SQLException{
        DataBaseVoids.main();
        PreparedStatement stmnt = DataBaseVoids.con.prepareStatement("DELETE FROM TRADITION WHERE ID = '" +
                traditionId + "'");
        stmnt.executeQuery();
    }

    public ResultSet getUserTraditions(int userId) throws SQLException {
        return selectUserTraditions(userId);
    }

    private void insertIntoStatistic(int traditionId) throws SQLException {
        DataBaseVoids.main();
        PreparedStatement stmnt = DataBaseVoids.con.prepareStatement("INSERT INTO STATISTIC (ID, TRADITION_ID) " +
                "VALUES ('" + traditionId + "', '" + traditionId + "')");
        stmnt.executeQuery();
    }

    public void deleteStatistic(int traditionId) throws SQLException {
        DataBaseVoids.main();
        PreparedStatement stmnt = DataBaseVoids.con.prepareStatement("DELETE FROM STATISTIC WHERE TRADITION_ID = '" +
                traditionId + "'");
        stmnt.executeQuery();
    }

    private ResultSet selectTraditionCountries(Integer traditionId) throws SQLException {
        DataBaseVoids.main();
        PreparedStatement stmnt = DataBaseVoids.con.prepareStatement("SELECT COUNTRY_ID FROM TRADITION WHERE ID = '" +
            traditionId + "'");
        return stmnt.executeQuery();
    }

    private ResultSet selectTraditionHolidays(Integer traditionId) throws SQLException {
        DataBaseVoids.main();
        PreparedStatement stmnt = DataBaseVoids.con.prepareStatement("SELECT HOLIDAY_ID FROM TRADITION WHERE ID = '" +
        traditionId + "'");
        return stmnt.executeQuery();
    }

    private Integer setCountryId(ResultSet resultSet) {
        Integer id = 0;
        try {
            //TODO fix.
            while (resultSet.next()){
                id = resultSet.getInt("ID");
            }
        } catch (SQLException e) {
            System.err.println("In resultSet working error!");
        }
        return id;
    }

    private LinkedList<Integer> setHolidayId(ResultSet resultSet) throws SQLException{
        LinkedList<Integer> result = new LinkedList<>();
        while (resultSet.next()) {
            result.add(resultSet.getInt("ID"));
        }
        return result;
    }

    private ResultSet getCountry(Integer id) throws SQLException{
        DataBaseVoids.main();
        PreparedStatement stmnt = DataBaseVoids.con.prepareStatement(
                "SELECT * FROM COUNTRY WHERE ID = '" + id + "'");
        return stmnt.executeQuery();
    }

    private ResultSet getHoliday(Integer id) throws SQLException{
        DataBaseVoids.main();
        PreparedStatement stmnt = DataBaseVoids.con.prepareStatement(
                "SELECT * FROM HOLIDAY WHERE ID = '" + id + "'");
        return stmnt.executeQuery();
    }

    public Country getTraditionCountry(Integer traditionId) throws SQLException{
        int id = setCountryId(selectTraditionCountries(traditionId));
        ResultSet resultSet = getCountry(id);
        String name = resultSet.getString("NAME");
        return new Country(id, name);
    }

    public LinkedList<Holiday> getTraditionHolidayList(Integer traditionId) throws SQLException{
        LinkedList<Holiday> result = new LinkedList<>();
        int i = 0;
        while (selectTraditionHolidays(traditionId).next()) {
            int id = setHolidayId(selectTraditionHolidays(traditionId)).get(i);
            ResultSet resultSet = getHoliday(id);
            String name = resultSet.getString("NAME");
            String typeId = resultSet.getString("TYPE_ID");
            Date startDate = resultSet.getDate("START_DATE");
            Date endDate = resultSet.getDate("END_DATE");
            if (endDate != null) {
                result.add(new Holiday(id, name, startDate,
                        endDate, typeId));
            } else result.add(new Holiday(id, name, startDate, typeId));
            i++;
        }
        return result;
    }

    public ResultSet insertComment(String text, int traditionId, int userId) throws  SQLException{
        DataBaseVoids.main();
        PreparedStatement stmnt = DataBaseVoids.con.prepareStatement("INSERT INTO COMMENTS (ID, TEXT, " +
                "TRADITION_ID, CUR_DATE, USER_ID) VALUES (comments_seq.nextval, '" + text + "', '"
                + traditionId + "', '" + new java.util.Date() + "', '" +  userId + "')");
        return stmnt.executeQuery();
    }

    public void deleteComments(int traditionId) throws SQLException{
        DataBaseVoids.main();
        PreparedStatement stmnt = DataBaseVoids.con.prepareStatement("DELETE FROM COMMENTS WHERE TRADITION_ID = '" +
                traditionId + "'");
        stmnt.executeQuery();
    }

    public int getCommentCount(int traditionId) throws SQLException{
        DataBaseVoids.main();
        PreparedStatement stmnt = DataBaseVoids.con.prepareStatement("SELECT COUNT(*) FROM COMMENTS " +
                "WHERE TRADITION_ID = '" + traditionId + "'");
        return DataBaseVoids.getIntegerValue(stmnt.executeQuery());
    }

    public ResultSet getTraditionComment(int traditionId) throws SQLException{
        DataBaseVoids.main();
        PreparedStatement stmnt = DataBaseVoids.con.prepareStatement("SELECT TEXT FROM COMMENTS " +
                "WHERE TRADITION_ID = '" + traditionId + "'");
        return stmnt.executeQuery();
    }

    public ResultSet incReads(int traditionId, int count) throws SQLException{
        DataBaseVoids.main();
        count++;
        PreparedStatement stmnt = DataBaseVoids.con.prepareStatement("UPDATE STATISTIC SET READS = " + count +
                " WHERE TRADITION_ID = '" + traditionId + "'");
        return stmnt.executeQuery();
    }

    public int getReads(int traditionId) throws SQLException{
        DataBaseVoids.main();
        PreparedStatement stmnt = DataBaseVoids.con.prepareStatement("SELECT READS FROM STATISTIC " +
                "WHERE TRADITION_ID = '" + traditionId +"'");
        return DataBaseVoids.getIntegerValue(stmnt.executeQuery());
    }

    public void saveTradition(List<Tradition> traditions, String direct) throws IOException {
        Element root = new Element("traditionSave");
        Document doc = new Document(root);
        for (Tradition tradition : traditions) {
            Element traditionElement = new Element("tradition");

            Element traditionDescription = new Element("traditionDescription");
            traditionDescription.setText(tradition.getDescription());
            traditionElement.addContent(traditionDescription);

            Element traditionId = new Element("traditionId");
            traditionId.setText(String.valueOf(tradition.getId()));
            traditionElement.addContent(traditionId);

            //TODO add holiday bean.
            Element holidayElement = new Element("holiday");

            Element holidayName = new Element("holidayName");
            holidayName.setText(tradition.getHoliday().getName());
            holidayElement.addContent(holidayName);

            Element holidayStartDate = new Element("holidayStartDate");
            holidayStartDate.setText(tradition.getHoliday().getStartDate());
            holidayElement.addContent(holidayStartDate);

            /*Element holidayEndDate = new Element("holidayEndDate");
            holidayEndDate.setText((String)tradition.getHoliday().getEndDate());
            holidayElement.addContent(holidayEndDate);
*/
            Element holidayType = new Element("holidayType");
            holidayType.setText(tradition.getHoliday().getType().toString());
            holidayElement.addContent(holidayType);

            traditionElement.addContent(holidayElement);

            Element elementCountry = new Element("country");

            Element countryName = new Element("countryName");
            countryName.setText(tradition.getCountry().getName());
            elementCountry.addContent(countryName);

            traditionElement.addContent(elementCountry);

            root.addContent(traditionElement);
            FileWorking.writeXml(doc, direct);
        }
    }
}
