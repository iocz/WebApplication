package servlet;

import beans.*;
import func.Calendar;
import models.*;

import javax.ejb.EJB;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by iocz on 11/10/15.
 */
@WebServlet(urlPatterns = "saveInXML")
public class saveInXMLServlet extends HttpServlet{
    @EJB
    private CountryWorking countryBean;
    @EJB
    private HolidayWorking holidayBean;
    @EJB
    private TypeWorking typeBean;
    @EJB
    private StatisticWorking statisticBean;
    @EJB
    private CommentsWorking commentsBean;
    @EJB
    private TraditionWorking traditionBean;

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws IOException{
        String type = request.getParameter("type");
        String[] elements = request.getParameterValues(type);
        //Integer[] id = new Integer[elements.length];
        //String[] names = new String[elements.length];
        List<Saveable> elementsList = getList(type);
        for (int i = 0; i < elements.length; i++) {
            elementsList.add(getElementList(type).get(Integer.parseInt(elements[i])));
            //id[i] = getElementList(type).get(Integer.parseInt(elements[i])).getId();
            //names[i] = getElementList(type).get(Integer.parseInt(elements[i])).getName();
        }
        try {
            saveInXML(type, elementsList);
        } catch (Exception e) {
            //TODO fix it.
            e.printStackTrace();
        }
        response.sendRedirect("/WebApp/index.jsp?xml");
    }

    private List<Saveable> getList(String type) {
        if (type.equals("country") || type.equals("holiday")) {
            return new LinkedList<>();
        } else {
            return new ArrayList<>();
        }
    }

    private List<? extends Saveable> getElementList(String type) {
        if (type.equals("country")) {
            return Calendar.getCountries();
        } else if (type.equals("holiday")) {
            return Calendar.getHolidays();
        } else if (type.equals("tradition")) {
            return Calendar.getTraditions();
        } else return new ArrayList<>();
    }

    private List<Tradition> getAdditionalTraditionList(String type, String[] names)
            throws SQLException{
        ArrayList<Tradition> result = new ArrayList<>();
        if (type.equals("country")) {
            //TODO additional tradition
            for (int i = 0; i < names.length; i++) {
                ArrayList<Tradition> traditionList = Calendar.setTraditionList(
                                countryBean.getCountryTraditions(
                                        names[i], Calendar.getUserId()));
                for (int j = 0; j < traditionList.size(); j++) {
                        result.add(traditionList.get(j));
                }
                result.add(Calendar.setTradition(countryBean.getCountryTraditions(names[i],
                        Calendar.getUserId())));
            }
        } else if (type.equals("holiday")) {
            for (int i = 0; i < names.length; i++) {
                result.add(Calendar.setTradition(holidayBean.getHolidayTraditions(names[i],
                        Calendar.getUserId())));
            }
        }
        return result;
    }

    //TODO additional country, holiday, statistic, comments
    private Country getAdditionalCountry(Integer traditionId) throws  SQLException{
        return traditionBean.getTraditionCountry(traditionId);
    }

    private LinkedList<Holiday> getAdditionalHolidayList(Integer traditionId) throws SQLException{
        return traditionBean.getTraditionHolidayList(traditionId);
    }

    private LinkedList<Country> getAdditionalCountryList(List<Tradition> traditionList)
            throws SQLException{
        LinkedList<Country> result = new LinkedList<>();
        for (int i = 0; i < traditionList.size(); i++) {
            result.add(getAdditionalCountry(traditionList.get(i).getId()));
        }
        return result;
    }

    private LinkedList<Holiday> getAdditionalHoliday(List<Tradition> traditionList) throws SQLException{
        LinkedList<Holiday> result = new LinkedList<>();
        for (int i = 0; i < traditionList.size(); i++) {
            for (int j = 0; j < getAdditionalHolidayList(traditionList.get(i).getId()).size(); j++) {
                for (int k = 0; k < getAdditionalHolidayList(traditionList.get(i).getId()).size(); k++) {
                    result.add(getAdditionalHolidayList(traditionList.get(i).getId()).get(k));
                }
            }
        }
        return result;
    }

    private void saveAdditionalTradition(List<? extends Saveable> elementList)
            throws SQLException, IOException{
        String[] names = new String[elementList.size()];
        for (int i = 0; i < elementList.size(); i++) {
            names[i] = elementList.get(i).getName();
        }
        ArrayList<Tradition> traditionList = (ArrayList<Tradition>)
                getAdditionalTraditionList("country", names);

        traditionBean.saveTradition(traditionList, StringConst.TRADITION_PATH_XML);
        //Additional statistic.
        //TODO change path.
        saveStatistic(traditionList);
        //Additional comments.
        saveComments(traditionList);
    }

    private void saveAdditionalType(Holiday holiday) throws SQLException, IOException{
        //TODO change direct.
        typeBean.saveTypeXML(holidayBean.selectHolidayType(holiday), StringConst.TYPE_PATH_XML);
    }

    private void saveAdditionalTypeList(List<Holiday> elementList)
            throws SQLException, IOException{
        for (int i = 0; i < elementList.size(); i++) {
            saveAdditionalType(elementList.get(i));
        }
    }

    private String[] getNames(List<? extends Saveable>elementList) {
        String[] result = new String[elementList.size()];
        for (int i = 0; i < elementList.size(); i++) {
            result[i] = elementList.get(i).getName();
        }
        return result;
    }

    private void saveStatistic(List<Tradition> traditionList) throws IOException, SQLException{
        for (int i = 0; i < traditionList.size(); i++) {
            statisticBean.saveStatisticXML(
                    //TODO change direct.
                    statisticBean.getTraditionStatistic(traditionList.get(i).getId()),
                    StringConst.STATISTIC_PATH_XML);
        }
    }

    private void saveComments(List<Tradition> traditionList) throws IOException, SQLException {
        for (int i = 0; i < traditionList.size(); i++) {
            commentsBean.saveCommentsXML(commentsBean.selectTraditionComments(
                    //TODO change direct.
                    traditionList.get(i).getId()), StringConst.COMMENTS_PATH_XML);
        }
    }

    private void saveInXML(String type, List<? extends Saveable> elementList)
            throws IOException, SQLException{
        if (type.equals("country")) {
            countryBean.saveCountryXML((List<Country>) elementList, StringConst.COUNTRY_PATH_XML);
            //Additional tradition
            saveAdditionalTradition(elementList);
        } else if (type.equals("holiday")) {
            //Save holiday.
            holidayBean.saveHolidaysXML((List<Holiday>) elementList, StringConst.HOLIDAY_PATH_XML);
            //Additional tradition.
            ArrayList<Tradition> traditionList = (ArrayList<Tradition>)getAdditionalTraditionList("holiday",
                    getNames(elementList));
            traditionBean.saveTradition(getAdditionalTraditionList("holiday",
                    getNames(elementList)), StringConst.TRADITION_PATH_XML);
            //Additional types.
            saveAdditionalTypeList((List<Holiday>) elementList);
            //Additional statistic.
            //TODO change path.
            saveStatistic(traditionList);
            //Additional comments.
            saveComments(traditionList);
            //TODO additional holiday, country, user, statistic, comments
        } else if (type.equals("tradition")) {
            traditionBean.saveTradition((List<Tradition>) elementList, StringConst.TRADITION_PATH_XML);
            //Additional holiday.
            holidayBean.saveHolidaysXML(getAdditionalHoliday((ArrayList<Tradition>) elementList),
                    StringConst.HOLIDAY_PATH_XML);
            //Additional country.
            countryBean.saveCountryXML(getAdditionalCountryList(
                    (ArrayList<Tradition>)elementList), StringConst.HOLIDAY_PATH_XML);
            //Additional statistic.
            //TODO change path.
            saveStatistic((ArrayList<Tradition>)elementList);
            //Additional comments.
            saveComments((ArrayList<Tradition>)elementList);
        }
    }
}
