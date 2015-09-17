package servlet;

import Java_test.Connect;
import beans.TraditionWorking;
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
@WebServlet(urlPatterns = "saveTraditionsXML")
public class SaveTraditionsServlet extends HttpServlet {
    @EJB
    private TraditionWorking traditionBean;

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws IOException {
        traditionBean.saveTradition(Calendar.getTraditions(),
                "/home/iocz/Документы/WebApplication/src/resources/xml/traditionSave.xml");
        response.sendRedirect("/WebApp/index.jsp?xml");
    }
}
