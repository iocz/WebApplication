package beans;

import models.Country;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by iocz on 12/10/15.
 */
public interface StatisticWorking {
    ResultSet selectStatistic() throws SQLException;
    ResultSet getTraditionStatistic(Integer traditionId) throws SQLException;
    void saveStatisticXML(ResultSet resultSet, String direct)
            throws IOException, SQLException;
}
