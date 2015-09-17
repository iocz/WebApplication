package servlet;

import beans.CountryWorking;
import beans.HolidayWorking;
import beans.TraditionWorking;
import func.Calendar;
import func.LoadData;
import models.Tradition;

import javax.ejb.EJB;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;

/**
 * Created by iocz on 08/10/15.
 */
@WebServlet(urlPatterns = "/loadDataServlet")
public class loadHashMapDataServlet extends HttpServlet {
    @EJB
    private CountryWorking countryBean;
    @EJB
    private HolidayWorking holidayBean;
    @EJB
    private TraditionWorking traditionBean;

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response){
        Calendar.getCountries().clear();
        Calendar.getHolidays().clear();
        Calendar.getTraditions().clear();
        //Загрузка данных в HashMap.
        try {
            Calendar.setCountriesList(countryBean.selectCountries());
            Calendar.setHolidaysList(holidayBean.selectHoliday());
            Calendar.setTraditionsList(traditionBean.selectAllTraditions());
        } catch (SQLException e) {
            //TODO change after test.
            e.printStackTrace();
        }
    }
}
