package servlet;

import beans.CountryWorking;
import func.Calendar;
import func.FileWorking;
import models.Country;

import javax.ejb.EJB;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Created by iocz on 10/10/15.
 */
@WebServlet(urlPatterns = "/saveCountriesXML")
public class saveCountriesServlet extends HttpServlet {
    @EJB
    private CountryWorking countryBean;

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws IOException{
        String[] countries = request.getParameterValues("country");
        LinkedList<Country> countryList = new LinkedList<>();
        for (int i = 0; i < countries.length; i++) {
            countryList.add(Calendar.getCountries().get(Integer.parseInt(countries[i])));
        }
        countryBean.saveCountryXML(countryList, "/home/iocz/Документы/WebApplication/src/resources/xml/countrySave.xml");
            //FileWorking.saveCountryXML(Calendar.getCountries(), "/home/iocz/Документы/WebApplication/src/resources/xml/countrySave.xml");
        response.sendRedirect("/WebApp/index.jsp?xml");
    }

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response) {

    }
}
