package servlet;

import func.Calendar;
import models.Saveable;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by iocz on 11/10/15.
 */
@WebServlet(urlPatterns = "selectElements")
public class selectElementsServlet extends HttpServlet {

    public void service(ServletRequest request, ServletResponse response) throws IOException{
        String beanType = request.getParameter("type");
        PrintWriter out = response.getWriter();
        out.print("<form action=\"" + actionServlet(beanType) + "\" method=\"post\">");
        out.print("<select multiple required=\"10\" class=\"b3radius field\" name=\"" + beanType + "\">");
        out.print("<option disabled>Выберите имена " + paramName(beanType) + "</option>");
        //TODO Переписать
        List<? extends Saveable> elementsList = formList(beanType);
        for (int i = 0; i < elementsList.size(); i++) {
            out.print("<option value=" + i + ">" +
                    elementsList.get(i).getName() + "</option>");
        }
        out.print("</select>");
        out.print("<br>");
        out.print("<input type=\"submit\" value=\"Save " + beanType + " in XML\"/>");
        out.print("</form>");
    }

    private String actionServlet(String beanType) {
        if (beanType.equals("country")) {
            return "saveInXML?type=country";
        } else if (beanType.equals("holiday")) {
            return "saveInXML?type=holiday";
        } else if (beanType.equals("tradition")) {
            return "saveInXML?type=tradition";
        } else {
            return "o_O";
        }
    }

    private List<? extends Saveable> formList(String beanType) {
        if (beanType.equals("country")) {
            return Calendar.getCountries();
        } else if (beanType.equals("holiday")) {
            return Calendar.getHolidays();
        } else if (beanType.equals("tradition")) {
            return Calendar.getTraditions();
        } else {
            return new ArrayList<>();
        }
    }

    private String paramName(String beanType) {
        if (beanType.equals("country")) {
            return "стран";
        } else if (beanType.equals("holiday")) {
            return "праздников";
        } else if (beanType.equals("tradition")) {
            return "традиций";
        } else {
            return "o_O";
        }
    }
}
