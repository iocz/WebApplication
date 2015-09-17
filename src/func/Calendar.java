package func;

import model.Country;
import model.Holiday;
import model.Tradition;
import model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

/**
 * Created by 1 on 23.06.2015.
 */
public class Calendar {

    private static HashMap<Integer, Holiday> holidays = new HashMap<>();
    private static HashMap<Integer, Country> countries = new HashMap<>();
    private static HashMap<Integer, Tradition> traditions = new HashMap<>();
    private static ArrayList<String> ImgURlList = new ArrayList<>();
    private static ArrayList<Integer> idList = new ArrayList<>();
    private static String language = "RU";
    private static int userId = 0;
    private static boolean isWrongLoginPass = false;
    private static boolean showNews =false;

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

    public static boolean getIsWrongLoginPass() {
        return isWrongLoginPass;
    }

    public static boolean getShowNews() {
        return showNews;
    }

    public static HashMap<Integer, Holiday> getHolidays() {
        return holidays;
    }

    public static HashMap<Integer, Country> getCountries() {
        return countries;
    }

    public static HashMap<Integer, Tradition> getTraditions() {
        return traditions;
    }

    public static ArrayList<String> getImgURlList() {
        return ImgURlList;
    }

    public static ArrayList<Integer> getIdList() {
        return  idList;
    }

    public static String getLanguage() {
        return language;
    }
}
