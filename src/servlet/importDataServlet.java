package servlet;

import beans.ImportWorking;
import jdk.internal.org.xml.sax.SAXException;
import models.StringConst;
import org.jdom2.JDOMException;

import javax.ejb.EJB;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(urlPatterns = "importData")
public class importDataServlet extends HttpServlet{
    @EJB
    private ImportWorking importBean;

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws IOException{
        try {
            String type = request.getParameter("type");
            if ("country".equals(type)) {
                importBean.loadCountry(StringConst.COUNTRY_PATH);
            } else if ("holiday".equals(type)) {
                importBean.loadHoliday(StringConst.HOLIDAY_PATH);
            }
        } //TODO fix catch.
        catch (SQLException e) {
            e.printStackTrace();
        } catch (JDOMException e) {
            e.printStackTrace();
        } catch (org.xml.sax.SAXException e) {
            e.printStackTrace();
        }
        response.sendRedirect("/WebApp/index.jsp?importXML");
    }
}
