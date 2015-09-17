package func;

import oracle.jdbc.proxy.annotation.Pre;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class DataBaseVoids {
    private DataBaseVoids() {}
    private static Connection con = null;
    private static ArrayList<String> list = new ArrayList<>();
    public static Map<Integer, Integer> idResults = new HashMap<>();

    public static void main() {
        Locale.setDefault(Locale.ENGLISH);
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            con = DriverManager.getConnection("jdbc:oracle:thin:@//localhost:1521/XE", "kolobok", "miller");
        } catch (ClassNotFoundException e) {
            System.out.println("Class not found... again");
        } catch (SQLException e) {
            System.out.println("SQL error while connect to jdbc or make request");
            System.out.println(e.getMessage());
        }
    }

    private static int getIntegerValue(ResultSet resultSet) throws SQLException{
        int result = 0;
        while (resultSet.next()) {
            result = resultSet.getInt(1);
        }
        return result;
    }

    private static String getStringValue(ResultSet resultSet) throws SQLException{
        String result = "";
        while (resultSet.next()) {
            result = resultSet.getString(1);
        }
        return result;
    }

    public static ResultSet selectAllHolidaysNames() throws SQLException {
        ResultSet resultSet = null;
        Statement stmnt = con.createStatement();
        return resultSet = stmnt.executeQuery("SELECT NAME, START_DATE FROM HOLIDAYLIBRARY.HOLIDAY");
    }

    public static boolean isLogIn(String pass, String login) throws SQLException {

        ResultSet rs = selectUser(pass, login);
        if (rs.next()) {
            Calendar.setUserId(Integer.parseInt(rs.getString("ID")));
            return true;
        }
        return false;
    }

    private static ResultSet selectUser(String pass, String login) throws SQLException {
        ResultSet resultSet = null;
        PreparedStatement stmnt = con.prepareStatement("SELECT ID FROM USERLIST WHERE LOGIN='" + login + "' AND PASS='" + pass + "'");
        return resultSet = stmnt.executeQuery();
    }

    private static ResultSet selectUserTraditions(int userID) throws SQLException {
        ResultSet resultSet = null;
        PreparedStatement stmnt = con.prepareStatement("SELECT * FROM TRADITION WHERE USER_ID = " + userID);
        return resultSet = stmnt.executeQuery();
    }

    public static ResultSet selectAllTraditions() throws SQLException {
        ResultSet resultSet = null;
        PreparedStatement stmnt = con.prepareStatement("SELECT * FROM TRADITION");
        return resultSet = stmnt.executeQuery();
    }

    public static ResultSet selectCountries() throws SQLException {
        ResultSet resultSet = null;
        PreparedStatement stmnt = con.prepareStatement("SELECT * FROM COUNTRY");
        return resultSet = stmnt.executeQuery();
    }

    public static ResultSet insertCountry(String countryName) throws SQLException {
        ResultSet resultSet = null;
        PreparedStatement stmnt = con.prepareStatement("INSERT INTO COUNTRY (ID, NAME)" +
                "VALUES (country_seq.nextval, '" + countryName + "')");
        return resultSet = stmnt.executeQuery();
    }

    public static void insertNewImg(int id, String url) throws SQLException{
        //ResultSet resultSet = null;
        PreparedStatement stmnt = con.prepareStatement("INSERT INTO IMG_URL (ID, URL)" +
                "VALUES ('" + id + "', '" + url + "')");
        //resultSet = stmnt.executeQuery();
        stmnt.executeQuery();
    }

    public static String getImgUrl(int id) throws SQLException{
        ResultSet resultSet = null;
        PreparedStatement stmnt = con.prepareStatement("SELECT URL FROM IMG_URL " +
                "WHERE ID = '" + id + "')");
        resultSet = stmnt.executeQuery();
        return getStringValue(resultSet);
    }

    public static ResultSet selectHoliday() throws SQLException{
        ResultSet resultSet = null;
        PreparedStatement stmnt = con.prepareStatement("SELECT * FROM HOLIDAY");
        return resultSet = stmnt.executeQuery();
    }

    public static ResultSet insertHoliday(String holidayName, int typeId, String holidayDate)
                                                                            throws SQLException{
        ResultSet resultSet = null;
        PreparedStatement stmnt = con.prepareStatement("INSERT INTO HOLIDAY (ID, NAME, " +
                "TYPE_ID, START_DATE) VALUES (holiday_seq.nextval, '" + holidayName + "', '" +
                typeId + "', TO_DATE('" + holidayDate + "', 'DD - MM - YYYY'))");
        return resultSet = stmnt.executeQuery();
    }

    public static ResultSet insertHolidayTwoDate(String holidayName, int typeId, String holidayStartDate,
                                          String holidayEndDate) throws SQLException{
        ResultSet resultSet = null;
        PreparedStatement stmnt = con.prepareStatement("INSERT INTO HOLIDAY (ID, NAME, " +
                "TYPE_ID, START_DATE, END_DATE) VALUES (holiday_seq.nextval, '" + holidayName + "', '" +
                typeId + "', TO_DATE('" +  holidayStartDate + "', 'DD - MM - YYYY'), " +
                "TO_DATE('" +  holidayEndDate + "', 'DD - MM - YYYY'))");
        return resultSet = stmnt.executeQuery();
    }

    public static ResultSet insertTradition(int countryId, int holidayId, int userId,
                                             String description, String imgUrl) throws SQLException{
        ResultSet resultSet = null;
        String request = "INSERT INTO TRADITION (ID, COUNTRY_ID, " +
                "HOLIDAY_ID, USER_ID, DESCRIPTION, IMG_URL) VALUES (tradition_seq.nextval, '" + countryId +
                "', '" + holidayId + "', '" +  userId + "', '" + description + "', '" + imgUrl +"')";
        PreparedStatement stmnt = con.prepareStatement(request);
        System.out.println(request);
        return resultSet = stmnt.executeQuery();
    }

    public static void deleteTradition(int traditionId) throws SQLException{
        PreparedStatement stmnt = con.prepareStatement("DELETE FROM TRADITION WHERE ID = '" +
                traditionId + "'");
        //ResultSet resultSet = stmnt.executeQuery();
        stmnt.executeQuery();
    }

    public static ResultSet insertStatistic(int traditionId) throws SQLException {
        ResultSet resultSet = null;
        PreparedStatement stmnt = con.prepareStatement("INSERT INTO STATISTIC (ID, TRADITION_ID) " +
                "VALUES ('" + traditionId + "', '" + traditionId + "')");
        return resultSet = stmnt.executeQuery();
    }

    public static void deleteStatistic(int traditionId) throws SQLException {
        PreparedStatement stmnt = con.prepareStatement("DELETE FROM STATISTIC WHERE TRADITION_ID = '" +
                traditionId + "'");
        //ResultSet resultSet = stmnt.executeQuery();
        stmnt.executeQuery();
    }

    public static ResultSet insertComment(String text, int traditionId, int userId)
                                                                    throws  SQLException{
        ResultSet resultSet = null;
        PreparedStatement stmnt = con.prepareStatement("INSERT INTO COMMENTS (ID, TEXT, " +
                "TRADITION_ID, CUR_DATE, USER_ID) VALUES (comments_seq.nextval, '" + text + "', '"
                + traditionId + "', '" + new java.util.Date() + "', '" +  userId + "')");
        return resultSet = stmnt.executeQuery();
    }

    public static void deleteComments(int traditionId) throws SQLException{
        PreparedStatement stmnt = con.prepareStatement("DELETE FROM COMMENTS WHERE TRADITION_ID = '" +
                traditionId + "'");
        //ResultSet resultSet = stmnt.executeQuery();
        stmnt.executeQuery();
    }

    public static int getCommentCount(int traditionId) throws SQLException{
        ResultSet resultSet = null;
        PreparedStatement stmnt = con.prepareStatement("SELECT COUNT(*) FROM COMMENTS " +
                "WHERE TRADITION_ID = '" + traditionId + "'");
        resultSet = stmnt.executeQuery();
        return getIntegerValue(resultSet);
    }

    public static ResultSet getTraditionComment(int traditionId) throws SQLException{
        ResultSet resultSet = null;
        PreparedStatement stmnt = con.prepareStatement("SELECT TEXT FROM COMMENTS " +
                "WHERE TRADITION_ID = '" + traditionId + "'");
         return resultSet = stmnt.executeQuery();
    }

    public static ResultSet incReads(int traditionId, int count) throws SQLException{
        count++;
        ResultSet resultSet = null;
        PreparedStatement stmnt = con.prepareStatement("UPDATE STATISTIC SET READS = " + count +
                " WHERE TRADITION_ID = '" + traditionId + "'");
        return resultSet = stmnt.executeQuery();
    }

    public static int getReads(int traditionId) throws SQLException{
        ResultSet resultSet = null;
        PreparedStatement stmnt = con.prepareStatement("SELECT READS FROM STATISTIC " +
                "WHERE TRADITION_ID = '" + traditionId +"'");
        resultSet = stmnt.executeQuery();
        return getIntegerValue(resultSet);
    }

    public static ResultSet getUserHolidays(int userId) throws SQLException {
        return selectUserHolidays(userId);
    }

    public static ResultSet getUserTraditions(int userId) throws SQLException {
        return selectUserTraditions(userId);
    }

    public static ResultSet getCountries() throws SQLException {
        return selectCountries();
    }

    public static ResultSet getCountryTraditions(String countryName, int userId) throws SQLException {
        return selectCountryTraditions(countryName, userId);
    }

    public static ResultSet getHolidayTraditions(String holidayName, int userId) throws SQLException {
        return selectHolidayTraditions(holidayName, userId);
    }

    public static int addUser(String login, String pass) throws SQLException {
        return insertUser(login, pass);
    }

    public static int getUserId(String login) throws SQLException {
        PreparedStatement stmnt = con.prepareStatement("SELECT ID FROM USERLIST WHERE LOGIN = '" +
                 login + "'");
        return getIntegerValue(stmnt.executeQuery());
    }

    //region Работа с базой
    private static ResultSet selectUserHolidays(int userID) throws SQLException {
        PreparedStatement stmnt = con.prepareStatement("SELECT * FROM HOLIDAYLIBRARY.HOLIDAY WHERE ID IN (SELECT HOLIDAY_ID FROM HOLIDAYLIBRARY.TRADITION WHERE USER_ID = " + userID + ")");
        return stmnt.executeQuery();
    }

    private static ResultSet selectCountryTraditions(String countryName, int userId) throws SQLException {
        PreparedStatement stmnt = con.prepareStatement("SELECT * FROM TRADITION where COUNTRY_ID IN " +
                "(SELECT ID FROM COUNTRY WHERE NAME ='" + countryName +
                "') AND USER_ID =" + userId);
        return stmnt.executeQuery();
    }

    private static ResultSet selectHolidayTraditions(String holidayName, int userId) throws SQLException {
        PreparedStatement stmnt = con.prepareStatement("SELECT * FROM HOLIDAYLIBRARY.TRADITION where HOLIDAY_ID IN " +
                "(SELECT ID FROM HOLIDAYLIBRARY.HOLIDAY WHERE NAME ='" + holidayName +
                "') AND USER_ID =" + userId);
        return stmnt.executeQuery();
    }

    //TODO разбить на меньшие модули и пререписать
    private static int insertUser(String login, String pass) throws SQLException {
        Statement stmnt = con.createStatement();

        ResultSet set = stmnt.executeQuery("SELECT COUNT (*) as num from ADDRESS");
        int addressCount = 0;
        if (set.next()) addressCount = Integer.parseInt(set.getObject("num").toString()) + 1;
        stmnt.execute("INSERT INTO ADDRESS (ID,COUNTRY_ID, STREET, HOUSE) VALUES ('"+addressCount+"', " + "'1', 'Not defined', '0')");

        int dataCount = 0;
        set = stmnt.executeQuery("Select count(*) as num from USERDATA");
        if (set.next())dataCount = Integer.parseInt(set.getObject("num").toString())+1;
        stmnt.execute("INSERT into USERDATA (ID,NAME, SURNAME, ADDRESS_ID, STATUS_ID) VALUES ("+dataCount+", 'Not defined', 'Not defined',"+ addressCount+", 2)");

        stmnt.execute("INSERT INTO USERLIST (ID, LOGIN, PASS, DATAID) VALUES (" + dataCount + ", \'" + login + "\', \'" + pass + "\', " + dataCount+")");
        return dataCount;
    }

    private static int insertHoliday(String name, Date startDate, int typeId) throws SQLException {
        Statement stmnt = con.createStatement();

        ResultSet set = stmnt.executeQuery("SELECT COUNT (*) as num from HOLIDAYLIBRARY.HOLIDAY");
        int holidayCount = 0;
        if (set.next()) holidayCount = Integer.parseInt(set.getObject("num").toString()) + 1;
        stmnt.execute("INSERT INTO HOLIDAYLIBRARY.HOLIDAY (ID, NAME, START_DATE, TYPE_ID) VALUES ("+holidayCount+", "+name+", "+ startDate + ", " + typeId + ")");
        return holidayCount;
    }

    private static int insertTradition(int countryId, int holidayId, int userID, String description) throws SQLException {
        Statement stmnt = con.createStatement();

        ResultSet set = stmnt.executeQuery("SELECT COUNT (*) as num from HOLIDAYLIBRARY.TRADITION");
        int traditionCount = 0;
        if (set.next()) traditionCount = Integer.parseInt(set.getObject("num").toString()) + 1;
        stmnt.execute("INSERT INTO HOLIDAYLIBRARY.TRADITION (ID, COUNTRY_ID, HOLIDAY_ID, USER_ID, DESCRIPTION) " +
                "VALUES ("+traditionCount+", "+countryId+", "+ holidayId+ ", " + userID+ ", "+ description+")");
        return traditionCount;
    }
    //endregion
    ////поиск

    public static void setSearchString(String string) {
        String[] getString = string.split(" ");
        int length = 0;
        while (getString.length != length) {
            list.add(getString[length]);
            length++;
        }
    }

    public static ArrayList getSearchString() {
        return list;
    }

    private static void checkHoliday(int id) throws SQLException {//��������� ������ � ��������� �� ����� ����������, ���������� �� �����
        DataBaseVoids.main();
        int i = 0;
        int countFoundWords = 0;
        String[] getHoliday = new String[2];
        ResultSet resultSet = null;
        Statement stmnt = DataBaseVoids.con.createStatement();
//��������� �������� �� �� �� id:
        resultSet = stmnt.executeQuery(
                "SELECT name FROM holiday where id='" + id + "'");
        if (resultSet.next()) {
            getHoliday = resultSet.getString("name").split(" ");
        }
//��������� �� ���� � ������� ���� �� �������:
        while (list.size() != i) {
            for (int j = 0; j < getHoliday.length; j++) {
                if (getHoliday[j].equals(list.get(i))) {
                    countFoundWords++;
                }
            }
            i++;
        }
        //���������� id ��������� � ����� ���������� � �����(����-��������)
        // ��� �������������� ����������� ������:
        idResults.put(id, countFoundWords);
    }

    private static void checkAllHolidays() throws SQLException {
        DataBaseVoids.main();
        Statement stmnt = DataBaseVoids.con.createStatement();
        int countHolidays = 0;
        ResultSet resultSet = stmnt.executeQuery(
                "SELECT id FROM holiday");
        while (resultSet.next()) {
            countHolidays++;
        }

        for (int i = 0; i < countHolidays; i++)
            checkHoliday(i + 1);
    }

    public static void printFoundResults() throws SQLException {//�������� ��������� ������ �� ��
        ResultSet resultSet = null;
        Statement stmnt = DataBaseVoids.con.createStatement();

        for (Map.Entry<Integer, Integer> e : idResults.entrySet()) {
            if (e.getValue() > 0) {
                resultSet = stmnt.executeQuery(
                        "SELECT name FROM holiday where id='" + e.getKey() + "'");
                if (resultSet.next()) {
                    System.out.println("Holiday name: " + (resultSet.getString("name")));
                }

                resultSet = stmnt.executeQuery(
                        "SELECT description FROM tradition where holiday_id='" + e.getKey() + "'");
                if (resultSet.next()) {
                    System.out.println("Description: " + resultSet.getString("description"));
                }

                resultSet = stmnt.executeQuery(
                        "SELECT country.name  FROM country JOIN tradition  ON" +
                                "(tradition.country_id = country.id) " +
                                "WHERE  tradition.holiday_id='" + e.getKey() + "'");
                if (resultSet.next()) {
                    System.out.println("Country: " + resultSet.getString("name"));
                    System.out.println();
                }
            }
        }
    }
}
