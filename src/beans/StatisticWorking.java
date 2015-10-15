package beans;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public interface StatisticWorking {
    ResultSet selectStatistic() throws SQLException;
    ResultSet getTraditionStatistic(Integer traditionId) throws SQLException;
    void saveStatisticXML(ResultSet resultSet, String direct)
            throws IOException, SQLException;
}
