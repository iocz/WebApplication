package servlet;

import beans.CountryWorking;
import beans.HolidayWorking;
import beans.TraditionWorking;
import func.Calendar;
import func.DataBaseVoids;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;

/**
 * Created by iocz on 08/10/15.
 */
@WebServlet(urlPatterns = "/addTraditionServlet")
public class AddTraditionServlet extends HttpServlet {
    @EJB
    private CountryWorking countryBean;
    @EJB
    private HolidayWorking holidayBean;
    @EJB
    private TraditionWorking traditionBean;

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException {
        String countryName = request.getParameter("holidayCountry");
        //if (countryName != null && !func.Content.checkCountryList(countryName)) {
        if (countryName != null) {
            try {
                countryBean.insertCountry(countryName);
            } catch (SQLException e) {
                //TODO change after test.
                e.printStackTrace();
            }
        }

        String id = request.getParameter("id");
        String oldHolidayName = request.getParameter("title");
        String holidayName = request.getParameter("holidayName");
        String holidayType = request.getParameter("holidayType");
        String holidayDate = request.getParameter("holidayDate");
        String traditionDescription = request.getParameter("traditionDescription");
        if (oldHolidayName != null && holidayDate != null) {
            try {
                holidayBean.updateHoliday(Integer.parseInt(id), holidayName,
                        Integer.parseInt(holidayType), holidayDate);
                int holidayId = DataBaseVoids.getIdValue("HOLIDAY", "'" + holidayName + "'");
                int countryId = DataBaseVoids.getIdValue("COUNTRY", "'" + countryName + "'");
                traditionBean.updateTradition(Integer.parseInt(id), countryId, holidayId, traditionDescription,
                        "default.jpg");
            } catch (SQLException e) {
                //TODO change after test.
                e.printStackTrace();
            }
        } else if (holidayName != null && holidayType != null && holidayDate != null) {
            System.out.println("Номер типа праздника: " + Integer.parseInt(holidayType));
            try {
                holidayBean.insertHoliday(holidayName, Integer.parseInt(holidayType), holidayDate);
                int holidayId = DataBaseVoids.getIdValue("HOLIDAY", "'" + holidayName + "'");
                int countryId = DataBaseVoids.getIdValue("COUNTRY", "'" + countryName + "'");
                //TODO прикрепить к новости изображение
                traditionBean.insertTradition(countryId, holidayId, Calendar.getUserId(),
                        traditionDescription, "default.jpg");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
