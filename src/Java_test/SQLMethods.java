package Java_test;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * Created by iocz on 02/09/15.
 */
public class SQLMethods {
    public static ArrayList<String> getListSQLRequest(String selectValue, String fromValue,
                                                      ArrayList<BigDecimal> IdList,
                                                      boolean isDate) {
        ArrayList<String> result = new ArrayList<>();
        for (int i = 0; i < IdList.size(); i++) {
            Connection connection = Connect.getConnection();
            Statement statement = Connect.getStatement(connection);
            String resultSet = "SELECT " + selectValue + " FROM " + fromValue +
                    " WHERE ID = " + IdList.get(i);
            result.add(Connect.getStringResult(statement, resultSet, isDate));
        }
        return result;
    }

    public static void addNewCountry(String name) {
        Connection connection = Connect.getConnection();
        Statement statement = Connect.getStatement(connection);
        String resultSet = "INSERT INTO COUNTRY (ID, NAME)" +
                " VALUES (country_seq.nextval, '" + name + "')";
        try {
            ResultSet rs = statement.executeQuery(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void addNewHoliday(String name, int typeId, String holidayDate) {
        Connection connection = Connect.getConnection();
        Statement statement = Connect.getStatement(connection);
        String resultSet = "INSERT INTO HOLIDAY (ID, NAME, TYPE_ID, START_DATE)" +
                " VALUES (holiday_seq.nextval, '" + name + "', '" + typeId + "', TO_DATE('" +  holidayDate + "', 'DD - MM - YYYY'))";
        try {
            ResultSet rs = statement.executeQuery(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void addNewTradition(int countryId, int holidayId,
                                       int userId, String description) {
        Connection connection = Connect.getConnection();
        Statement statement = Connect.getStatement(connection);
        String resultSet = "INSERT INTO TRADITION (ID, COUNTRY_ID, HOLIDAY_ID," +
                " USER_ID, DESCRIPTION) VALUES (tradition_seq.nextval, '" + countryId + "', '"
        + holidayId + "', '" +  userId + "', " + "'" + description + "')";
        try {
            ResultSet rs = statement.executeQuery(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void addNewStatistic(int traditionId) {
        Connection connection = Connect.getConnection();
        Statement statement = Connect.getStatement(connection);
        String resultSet = "INSERT INTO STATISTIC (ID, TRADITION_ID) VALUES ('" +
        traditionId + "', '" + traditionId + "')";
        try {
            ResultSet rs = statement.executeQuery(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void addNewComment(String text, int traditionId, int userId) {
        Connection connection = Connect.getConnection();
        Statement statement = Connect.getStatement(connection);
        String resultSet = "INSERT INTO COMMENTS (ID, TEXT, TRADITION_ID," +
                " USER_ID) VALUES (comments_seq.nextval, '" + text + "', '"
                + traditionId + "', '" +  userId + "')";
        try {
            ResultSet rs = statement.executeQuery(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static Statement addStatment() {
        Connection connection = Connect.getConnection();
        Statement statement = Connect.getStatement(connection);
        return statement;
    }

    private static void closeSession(Statement statement) throws SQLException {
        Connection connection = statement.getConnection();
        statement.close();
        connection.close();
    }

    public static void addNewNode(String name, String text) {
        Statement statement = addStatment();
        String resultSet = "INSERT INTO GUEST_BOOK (ID, NAME, TEXT, CURRENT_DATA)" +
                "VALUES (gbook_seq.nextval, '" + name + "', '" + text + "', " +
                new java.util.Date() + ")";
        try {
            ResultSet rs = statement.executeQuery(resultSet);
            closeSession(statement);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    //increment Reads.
    public static void incReads(int traditionId, int count) {
        Connection connection = Connect.getConnection();
        Statement statement = Connect.getStatement(connection);
        count++;
        String resultSet = "UPDATE STATISTIC SET READS = " + count + " WHERE TRADITION_ID = '" +
                traditionId + "'";
        try {
            ResultSet rs = statement.executeQuery(resultSet);
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static int getReads(int traditionId) {
        Integer result = 0;
        Connection connection = Connect.getConnection();
        Statement statement = Connect.getStatement(connection);
        ResultSet resultSetGetReads = null;
        try {
            resultSetGetReads = Connect.resultSetSelectFromWhere(
              statement, "READS", "STATISTIC", "TRADITION_ID = '" + traditionId +"'");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        result = Connect.getIntegerValue(resultSetGetReads);
        try {
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static void deleteComments (int traditionId) {
        System.out.println("Start delete comments");
        Connection connection = Connect.getConnection();
        Statement statement = Connect.getStatement(connection);
        String resultSet = "DELETE FROM COMMENTS WHERE TRADITION_ID = '" + traditionId + "'";
        ResultSet rs = null;
        try {
            rs = statement.executeQuery(resultSet);
            System.out.println("Comments delete");
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void deleteStatistic (int traditionId) {
        Connection connection = Connect.getConnection();
        Statement statement = Connect.getStatement(connection);
        String resultSet = "DELETE FROM STATISTIC WHERE TRADITION_ID = '" + traditionId + "'";
        ResultSet rs = null;
        try {
            rs = statement.executeQuery(resultSet);
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void deleteTradition (int traditionId) {
        Connection connection = Connect.getConnection();
        Statement statement = Connect.getStatement(connection);
        String resultSet = "DELETE FROM TRADITION WHERE ID = '" + traditionId + "'";
        ResultSet rs = null;
        try {
            rs = statement.executeQuery(resultSet);
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static int commentCount (int traditionId) {
        Integer result = 0;
        Connection connection = Connect.getConnection();
        Statement statement = Connect.getStatement(connection);
        ResultSet resultSetGetReads = null;
        try {
            resultSetGetReads = Connect.resultSetSelectFromWhere(
                    statement, "count(*)", "COMMENTS", "TRADITION_ID = '" + traditionId + "'");
            result = Connect.getIntegerValue(resultSetGetReads);
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static ArrayList<BigDecimal> getIdList(String selectValue, String fromValue)
                throws SQLException{
        Connection connection = Connect.getConnection();
        Statement statement = Connect.getStatement(connection);
        ResultSet resultSetHolidayIdFromTradition =
                Connect.resultSetSelectFrom(statement, selectValue, fromValue);
        return Connect.getIntResultList(resultSetHolidayIdFromTradition);
    }

    public static Integer getIdValue(String tableName, String valueName) {
        Connection connection = Connect.getConnection();
        Statement statementId = Connect.getStatement(connection);
        ResultSet resultSetId = null;
        try {
            resultSetId = Connect.resultSetSelectFromWhere(statementId, "ID",
                    tableName, "NAME = " + valueName);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        int idValue = Connect.getIntegerValue(resultSetId);
        try {
            statementId.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return idValue;
    }
    /*
    public static ArrayList<String> getImgUrlList() {
        Connection connection = Connect.getConnection();
        Statement statement = Connect.getStatement(connection);
        try {
            ResultSet resultSetImgUrl = Connect.resultSetSelectFromWhere(statement,
                    "IMG_URL", "TRADITION", "ID = TRADITION_ID");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    */
}
