package func;

import Java_test.Connect;
import model.Country;
import model.Holiday;
import model.Tradition;

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

    public static void setHashMapHolidays(ResultSet resultSet){
        int id;
        String name;
        String type_id;
        Date startDate;
        Date endDate;
        try {
            while (resultSet.next()) {
                id = resultSet.getInt("ID");
                name = resultSet.getString("NAME");
                //type_id = resultSet.getString("TYPE_ID");
                type_id = "OTHER";
                startDate = resultSet.getDate("START_DATE");
                endDate = resultSet.getDate("END_DATE");
                if (endDate == null) {
                    Calendar.getHolidays().put(id, new Holiday(name, startDate, type_id));
                } else {
                    Calendar.getHolidays().put(id, new Holiday(name, startDate, endDate, type_id));
                }
            }
        } catch (SQLException e) {
            System.err.println("In resultSet working error!");
        }
    }

    public static void setHashMapCountries(ResultSet resultSet){
        int id;
        String name;
        try {
            while (resultSet.next()){
                id = resultSet.getInt("ID");
                name = resultSet.getString("NAME");
                Calendar.getCountries().put(id, new Country(name));
            }
        } catch (SQLException e) {
            System.err.println("In resultSet working error!");
        }
    }

    public static void setHashMapTraditions(ResultSet resultSet){
        int id;
        int countryId;
        int holidayId;
        String description;
        String imgUrl;
        try {
            while (resultSet.next()){
                id = resultSet.getInt("ID");
                Calendar.getIdList().add(id);
                countryId = resultSet.getInt("COUNTRY_ID");
                holidayId = resultSet.getInt("HOLIDAY_ID");
                description = clobToString(resultSet.getClob("DESCRIPTION"));
                imgUrl = resultSet.getString("IMG_URL");
                Calendar.getTraditions().put(id, new Tradition(Calendar.getHolidays().get(holidayId),
                        Calendar.getCountries().get(countryId), description));
                Calendar.getImgURlList().add(imgUrl);
            }
        } catch (SQLException e){
            System.err.println("In resultSet working error!");
        }
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
}
