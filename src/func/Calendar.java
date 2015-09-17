package func;

import models.Country;
import models.Holiday;
import models.Tradition;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

/**
 * Created by 1 on 23.06.2015.
 */
public class Calendar {
    private static List<Holiday> holidays = new LinkedList<>();
    private static List<Country> countries = new LinkedList<>();
    private static List<Tradition> traditions = new ArrayList<>();
    private static ArrayList<String> ImgURlList = new ArrayList<>();

    private static ArrayList<String> countryList = new ArrayList<>();
    private static String language = "RU";
    private static int userId = 0;
    private static boolean isWrongLoginPass = false;
    private static boolean showNews = false;

    public static void setUserId(int userId) {
        Calendar.userId = userId;
    }

    public static int getUserId() {
        return userId;
    }

    public static void setIsWrongLoginPass(boolean isWrongLoginPass) {
        Calendar.isWrongLoginPass = isWrongLoginPass;
    }

    public static void setShowNews(boolean showNews) {
        Calendar.showNews = showNews;
    }
    //TODO переделать в бины.
    public static void setCountriesList(ResultSet resultSet) {
            int id;
            String name;
            try {
                while (resultSet.next()){
                    id = resultSet.getInt("ID");
                    name = resultSet.getString("NAME");
                    countries.add(new Country(id, name));
                }
            } catch (SQLException e) {
                System.err.println("In resultSet working error!");
            }
    }

    public static void setHolidaysList(ResultSet resultSet) {
        int id;
        String name;
        String type_id;
        Date startDate;
        Date endDate;
        try {
            while (resultSet.next()) {
                id = resultSet.getInt("ID");
                name = resultSet.getString("NAME");
                //TODO fix type_id.
                //type_id = resultSet.getString("TYPE_ID");
                type_id = "OTHER";
                startDate = resultSet.getDate("START_DATE");
                endDate = resultSet.getDate("END_DATE");
                if (endDate == null) {
                    holidays.add(new Holiday(id, name, startDate, type_id));
                } else {
                    holidays.add(new Holiday(name, startDate, endDate, type_id));
                }
            }
        } catch (SQLException e) {
            System.err.println("In resultSet working error!");
        }
    }

    //TODO setTradition & setTraditionList -> один метод.
    public static void setTraditionsList(ResultSet resultSet) {
        int id;
        int countryId;
        int holidayId;
        String description;
        String imgUrl;
        try {
            while (resultSet.next()){
                id = resultSet.getInt("ID");
                countryId = resultSet.getInt("COUNTRY_ID");
                holidayId = resultSet.getInt("HOLIDAY_ID");
                //TODO clobToString перенести в другой класс.
                description = LoadData.clobToString(resultSet.getClob("DESCRIPTION"));
                imgUrl = resultSet.getString("IMG_URL");
                traditions.add(new Tradition(id, Calendar.getHolidays().get(holidayId),
                        Calendar.getCountries().get(countryId), description));
                Calendar.getImgURlList().add(imgUrl);
            }
        } catch (SQLException e){
            System.err.println("In resultSet working error!");
        }
    }

    public static ArrayList<Tradition> setTraditionList(ResultSet resultSet) throws SQLException{
        int id;
        int countryId;
        int holidayId;
        String description;
        //String imgUrl;
        ArrayList<Tradition> result = new ArrayList<>();
        while (resultSet.next()) {
            id = resultSet.getInt("ID");
            countryId = resultSet.getInt("COUNTRY_ID");
            holidayId = resultSet.getInt("HOLIDAY_ID");
            //TODO clobToString перенести в другой класс.
            description = LoadData.clobToString(resultSet.getClob("DESCRIPTION"));
            //imgUrl = resultSet.getString("IMG_URL");
            result.add(new Tradition(id, Calendar.getHolidays().get(holidayId),
                    Calendar.getCountries().get(countryId), description));
            //Calendar.getImgURlList().add(imgUrl);
        }
        return result;
    }

    public static Tradition setTradition(ResultSet resultSet) throws SQLException{
        int id;
        int countryId;
        int holidayId;
        String description;
        //String imgUrl;
        Tradition result = new Tradition();
            while (resultSet.next()) {
                id = resultSet.getInt("ID");
                countryId = resultSet.getInt("COUNTRY_ID");
                holidayId = resultSet.getInt("HOLIDAY_ID");
                //TODO clobToString перенести в другой класс.
                description = LoadData.clobToString(resultSet.getClob("DESCRIPTION"));
                //imgUrl = resultSet.getString("IMG_URL");
                result = new Tradition(id, Calendar.getHolidays().get(holidayId),
                        Calendar.getCountries().get(countryId), description);
                //Calendar.getImgURlList().add(imgUrl);
            }
        return result;
    }

    public static boolean getIsWrongLoginPass() {
        return isWrongLoginPass;
    }

    public static boolean getShowNews() {
        return showNews;
    }

    public static List<Holiday> getHolidays() {
        return holidays;
    }

    public static List<Country> getCountries() {
        return countries;
    }

    public static List<Tradition> getTraditions() {
        return traditions;
    }

    public static ArrayList<String> getImgURlList() {
        return ImgURlList;
    }

    //public static ArrayList<String> getCountryList() {
  //      return countryList;
    //}
    //TODO Добавить обработку языка
    public static String getLanguage() {
        return language;
    }
}
