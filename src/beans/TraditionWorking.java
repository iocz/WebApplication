package beans;

import model.Country;
import model.Holiday;
import model.Tradition;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public interface TraditionWorking {
    ResultSet selectAllTraditions() throws SQLException;
    void insertIntoTradition(int countryId, int holidayId, int userId, String description, String imgUrl) throws SQLException;
    void insertTradition(int countryId, int holidayId, int userId, String description, String imgUrl) throws SQLException;
    void updateTradition(int traditionId, int countryId, int holidayId, String description, String imgUrl) throws SQLException;
    int getLastTraditionID() throws SQLException;
    void deleteTradition(int traditionId) throws SQLException;
    ResultSet getUserTraditions(int userId) throws SQLException;
    void deleteStatistic(int traditionId) throws SQLException;
    ResultSet insertComment(String text, int traditionId, int userId) throws  SQLException;
    void deleteComments(int traditionId) throws SQLException;
    int getCommentCount(int traditionId) throws SQLException;
    ResultSet getTraditionComment(int traditionId) throws SQLException;
    ResultSet incReads(int traditionId, int count) throws SQLException;
    int getReads(int traditionId) throws SQLException;
    void saveTradition(List<Tradition> traditions, String direct) throws IOException;
    Country getTraditionCountry(Integer traditionId) throws SQLException;
    LinkedList<Holiday> getTraditionHolidayList(Integer traditionId) throws SQLException;
}
