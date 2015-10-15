package beans;

import func.Calendar;
import func.DataBaseVoids;
import func.FileWorking;
import func.LoadData;
import models.Holiday;
import org.jdom2.Document;
import org.jdom2.Element;

import javax.ejb.Stateless;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Stateless
public class HolidayBean implements HolidayWorking {
    public ResultSet selectAllHolidaysNames() throws SQLException {
        DataBaseVoids.main();
        Statement stmnt = DataBaseVoids.con.createStatement();
        return stmnt.executeQuery("SELECT NAME, START_DATE FROM HOLIDAYLIBRARY.HOLIDAY");
    }

    public ResultSet selectHoliday() throws SQLException{
        DataBaseVoids.main();
        PreparedStatement stmnt = DataBaseVoids.con.prepareStatement("SELECT * FROM HOLIDAY");
        return stmnt.executeQuery();
    }

    public ResultSet insertHoliday(String holidayName, int typeId, String holidayDate) throws SQLException{
        DataBaseVoids.main();
        PreparedStatement stmnt = DataBaseVoids.con.prepareStatement("INSERT INTO HOLIDAY (ID, NAME, " +
                "TYPE_ID, START_DATE) VALUES (holiday_seq.nextval, '" + holidayName + "', '" +
                typeId + "', TO_DATE('" + holidayDate + "', 'DD - MM - YYYY'))");
        return stmnt.executeQuery();
    }

    public ResultSet insertHolidayTwoDate(String holidayName, int typeId, String holidayStartDate, String holidayEndDate) throws SQLException{
        DataBaseVoids.main();
        PreparedStatement stmnt = DataBaseVoids.con.prepareStatement("INSERT INTO HOLIDAY (ID, NAME, " +
                "TYPE_ID, START_DATE, END_DATE) VALUES (holiday_seq.nextval, '" + holidayName + "', '" +
                typeId + "', TO_DATE('" +  holidayStartDate + "', 'DD - MM - YYYY'), " +
                "TO_DATE('" +  holidayEndDate + "', 'DD - MM - YYYY'))");
        return stmnt.executeQuery();
    }

    public void updateHoliday(int id, String holidayName, int typeId, String holidayDate) throws SQLException{
        DataBaseVoids.main();
        PreparedStatement stmnt = DataBaseVoids.con.prepareStatement("UPDATE HOLIDAY SET NAME = '" + holidayName +
                "', TYPE_ID = '" + typeId + "', START_DATE = TO_DATE('" + holidayDate + "', 'DD - MM - YYYY') " +
                " WHERE ID = '" + id + "'");
        stmnt.executeQuery();
    }

    public void updateHolidayTwoDate(int id, String holidayName, int typeId, String holidayStartDate, String holidayEndDate) throws SQLException {
        DataBaseVoids.main();
        PreparedStatement stmnt = DataBaseVoids.con.prepareStatement("UPDATE HOLIDAY SET NAME = '" + holidayName +
                "', TYPE_ID = '" + typeId + "', START_DATE = TO_DATE('" + holidayStartDate +
                "', 'DD - MM - YYYY'), END_DATE = TO_DATE('" + holidayEndDate +
                "', 'DD - MM - YYYY') WHERE ID = '" + id + "'");
    }

    private ResultSet getHolidaysType() throws SQLException{
        DataBaseVoids.main();
        PreparedStatement stmnt = DataBaseVoids.con.prepareStatement("SELECT NAME FROM TYPE");
        return stmnt.executeQuery();
    }

    public ArrayList<String> getHolidaysTypeList() throws SQLException{
        return LoadData.getResultList(getHolidaysType());
    }


    public ArrayList<String> getUserHolidays(int userId) throws SQLException {
        ArrayList<String> result = new ArrayList<>();
        while (selectUserHolidays(userId).next()) {
            result.add(selectUserHolidays(userId).getString("NAME"));
        }
        return result;
    }

    private ResultSet selectUserHolidays(int userID) throws SQLException {
        DataBaseVoids.main();
        PreparedStatement stmnt = DataBaseVoids.con.prepareStatement("SELECT * FROM HOLIDAY WHERE ID IN (SELECT " +
                "HOLIDAY_ID FROM TRADITION WHERE USER_ID = " + userID + ")");
        return stmnt.executeQuery();
    }

    private int insertHoliday(String name, Date startDate, int typeId) throws SQLException {
        DataBaseVoids.main();
        Statement stmnt = DataBaseVoids.con.createStatement();

        ResultSet set = stmnt.executeQuery("SELECT COUNT (*) as num from HOLIDAYLIBRARY.HOLIDAY");
        int holidayCount = 0;
        if (set.next()) holidayCount = Integer.parseInt(set.getObject("num").toString()) + 1;
        stmnt.execute("INSERT INTO HOLIDAYLIBRARY.HOLIDAY (ID, NAME, START_DATE, TYPE_ID) VALUES (" + holidayCount + ", " + name + ", " + startDate + ", " + typeId + ")");
        return holidayCount;
    }

    public void searchHoliday(String name) throws SQLException{
        ResultSet traditions = getHolidayTraditions(name,
                Calendar.getUserId());
        Calendar.getTraditions().clear();
        Calendar.setTraditionsList(traditions);
    }

    private ResultSet selectHolidayTraditions(String holidayName, int userId) throws SQLException {
        DataBaseVoids.main();
        PreparedStatement stmnt = DataBaseVoids.con.prepareStatement("SELECT * FROM TRADITION where HOLIDAY_ID IN " +
                "(SELECT ID FROM HOLIDAY WHERE NAME ='" + holidayName +
                "') AND USER_ID =" + userId);
        return stmnt.executeQuery();
    }


    public ResultSet selectHolidayType(Holiday holiday) throws SQLException {
        DataBaseVoids.main();
        PreparedStatement stmnt = DataBaseVoids.con.prepareStatement("SELECT * FROM TYPE WHERE ID IN " +
            "(SELECT TYPE_ID FROM HOLIDAY WHERE ID = '" + holiday.getId() + "')");
        return stmnt.executeQuery();
    }

    public ResultSet getHolidayTraditions(String holidayName, int userId) throws SQLException {
        return selectHolidayTraditions(holidayName, userId);
    }

    public void saveHolidaysXML(ResultSet resultSet, String direct)
            throws IOException, SQLException {
        Element root = new Element("holidaysSave");
        Document doc = new Document(root);
        while (resultSet.next()) {
            Element holidayElement = new Element("holiday");
            Element holidayId = new Element("holidayId");
            holidayId.setText(resultSet.getString("ID"));
            holidayElement.addContent(holidayId);

            Element holidayName = new Element("holidayName");
            holidayName.setText(resultSet.getString("NAME"));
            holidayElement.addContent(holidayName);

            Element holidayStartDate = new Element("holidayStartDate");
            holidayStartDate.setText(resultSet.getString("START_DATE"));
            holidayElement.addContent(holidayStartDate);

            //TODO fix it.
            /*Element holidayEndDate = new Element("holidayEndDate");
            holidayEndDate.setText((String)holiday.getEndDate());
            holidayElement.addContent(holidayEndDate);*/

            Element holidayType = new Element("holidayType");
            holidayType.setText(resultSet.getString("TYPE_ID"));
            holidayElement.addContent(holidayType);

            root.addContent(holidayElement);
            FileWorking.writeXml(doc, direct);
        }
    }

    public void saveHolidaysXML(List<Holiday> holidays, String direct) throws IOException {
        Element root = new Element("holidaysSave");
        Document doc = new Document(root);
        for (Holiday holiday : holidays) {
            Element holidayElement = new Element("holiday");
            Element holidayId = new Element("holidayId");
            holidayId.setText(String.valueOf(holiday.getId()));
            holidayElement.addContent(holidayId);

            Element holidayName = new Element("holidayName");
            holidayName.setText(holiday.getName());
            holidayElement.addContent(holidayName);

            Element holidayStartDate = new Element("holidayStartDate");
            holidayStartDate.setText(holiday.getStartDate());
            holidayElement.addContent(holidayStartDate);

            //TODO fix it.
            /*Element holidayEndDate = new Element("holidayEndDate");
            holidayEndDate.setText((String)holiday.getEndDate());
            holidayElement.addContent(holidayEndDate);*/

            Element holidayType = new Element("holidayType");
            holidayType.setText(holiday.getType().toString());
            holidayElement.addContent(holidayType);

            root.addContent(holidayElement);
            FileWorking.writeXml(doc, direct);
        }
    }

}
