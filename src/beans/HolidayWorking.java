package beans;

import models.Holiday;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface HolidayWorking {
    ResultSet selectAllHolidaysNames() throws SQLException;
    ResultSet selectHoliday() throws SQLException;
    ResultSet insertHoliday(String holidayName, int typeId, String holidayDate) throws SQLException;
    ResultSet insertHolidayTwoDate(String holidayName, int typeId, String holidayStartDate, String holidayEndDate) throws SQLException;
    void updateHoliday(int id, String holidayName, int typeId, String holidayDate) throws SQLException;
    void updateHolidayTwoDate(int id, String holidayName, int typeId, String holidayStartDate, String holidayEndDate) throws SQLException;
    ArrayList<String> getHolidaysTypeList() throws SQLException;
    ArrayList<String> getUserHolidays(int userId) throws SQLException;
    void searchHoliday(String name) throws SQLException;
    ResultSet selectHolidayType(Holiday holiday) throws SQLException;
    ResultSet getHolidayTraditions(String holidayName, int userId) throws SQLException;
    void saveHolidaysXML(ResultSet resultSet, String direct) throws IOException, SQLException;
    void saveHolidaysXML(List<Holiday> holidays, String direct) throws IOException;
}
