package servlet;

import beans.*;

import javax.ejb.EJB;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

/**
 * Created by iocz on 13/10/15.
 */
@WebServlet(urlPatterns = "loadToDB")
public class loadToDBServlet extends HttpServlet{
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

}
