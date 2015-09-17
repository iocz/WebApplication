package func;

import models.Country;
import models.Holiday;
import models.Tradition;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.sql.*;
import java.util.*;
import java.util.Date;

/**
 * Created by iocz on 14/09/15.
 */
public class LoadData {
    private static Integer getColumnCount(ResultSet resultSet) throws SQLException {
        ResultSetMetaData metaData = resultSet.getMetaData();
        int numberOfColumns = metaData.getColumnCount() + 1;
        return numberOfColumns;
    }

    public static String clobToString(Clob data) throws SQLException{
        StringBuilder sb = new StringBuilder();
        try {
            Reader reader = data.getCharacterStream();
            BufferedReader br = new BufferedReader(reader);

            String line;
            while(null != (line = br.readLine())) {
                sb.append(line);
            }
            br.close();
        } catch (IOException e) {
            System.err.println("File reading exception!");
        }
        return sb.toString();
    }

    public static ArrayList<String> getResultList(ResultSet resultSet) {
        ArrayList<String> text = new ArrayList<>();
        try {
            int numberOfColumns = getColumnCount(resultSet);

            while (resultSet.next()) {
                for (int i = 1; i < numberOfColumns; i++) {
                            text.add(resultSet.getString(i));
                }
            }
        } catch (SQLException sqlException) {
            System.err.println("In resultSet working error!");
        }
        return text;
    }

    public static ArrayList<String> setCountriesList() throws SQLException{
        ResultSet set = DataBaseVoids.getCountries();
        return getResultList(set);
    }
}
