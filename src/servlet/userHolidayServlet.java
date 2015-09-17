package servlet;

import beans.HolidayWorking;
import func.Calendar;

import javax.ejb.EJB;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by iocz on 08/10/15.
 */
@WebServlet(urlPatterns = "/getUserHolidays")
public class userHolidayServlet extends HttpServlet {
    @EJB
    private HolidayWorking holidayBean;

    @Override
    protected void doPost(HttpServletRequest request,
                                  HttpServletResponse response) throws IOException{
        try {
            responseMethod(response, holidayBean.getUserHolidays(Calendar.getUserId()));
        }catch (SQLException e){
            //TODO fix it.
            e.printStackTrace();
        }
    }

    private void responseMethod(HttpServletResponse response,
                                ArrayList<String> list) throws IOException, SQLException {
        PrintWriter out = response.getWriter();
        out.print("<div class=holidays>");
        out.print("<ul>");
        for (String item : list) {
            out.print("<li>");
            out.print("<a href = \"index.jsp?holidayName=" + item + " \">" + item + "</a>");
            out.print("</li>");
            out.print("</ul>");
            out.print("</div>");
        }
    }
}
