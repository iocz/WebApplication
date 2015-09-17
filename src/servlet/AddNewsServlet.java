package servlet;

import beans.HolidayWorking;
import beans.TraditionWorking;
import func.Calendar;
import func.Content;
import func.LoadData;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by iocz on 08/10/15.
 */
@WebServlet(urlPatterns = "/addNewsServlet")
public class AddNewsServlet extends HttpServlet {
    @EJB
    private TraditionWorking traditionBean;
    @EJB
    private HolidayWorking holidayBean;

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws IOException,
            ServletException {
        String name = request.getParameter("holidayName");
        try {
            ResultSet traditions = holidayBean.getHolidayTraditions(name, Calendar.getUserId());

            while (traditions.next()) {
                Calendar.getTraditions().clear();
                Calendar.setTraditionsList(holidayBean.getHolidayTraditions(
                        name, Calendar.getUserId()));
                ArrayList<String> pages = Content.getPages();
                for (int i = 0; i < pages.size(); i++) {
                    request.getRequestDispatcher(pages.get(i)).include(request, response);
                }
                Calendar.getTraditions().clear();
                Calendar.setTraditionsList(traditions);
            }
        } catch (SQLException e) {
            //TODO fix after test.
            e.printStackTrace();
        }
    }

    private void response(HttpServletResponse response,
                          String name) throws IOException{
        PrintWriter out = response.getWriter();
        try {
            ResultSet traditions = holidayBean.getHolidayTraditions(name, Calendar.getUserId());

            while (traditions.next()) {
                Calendar.getTraditions().clear();
                Calendar.setTraditionsList(holidayBean.getHolidayTraditions(
                        name, Calendar.getUserId()));
                ArrayList<String> pages = Content.getPages();
                for (int i = 0; i < pages.size(); i++) {

                }
                Calendar.getTraditions().clear();
                Calendar.setTraditionsList(traditions);
            }
        } catch (SQLException e) {
            //TODO fix after test.
            e.printStackTrace();
        }
    }
}
