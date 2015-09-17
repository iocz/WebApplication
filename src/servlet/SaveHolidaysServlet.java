package servlet;

import beans.HolidayWorking;
import com.sun.beans.util.Cache;
import func.Calendar;

import javax.ejb.EJB;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by iocz on 11/10/15.
 */
@WebServlet(urlPatterns = "saveHolidaysXML")
public class SaveHolidaysServlet extends HttpServlet {
    @EJB
    private HolidayWorking holidayBean;

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws IOException{
        String[] holidays = request.getParameterValues("holiday");
        holidayBean.saveHolidaysXML(Calendar.getHolidays(),
                "/home/iocz/Документы/WebApplication/src/resources/xml/holidaySave.xml");
        response.sendRedirect("/WebApp/index.jsp?xml");
    }
}
