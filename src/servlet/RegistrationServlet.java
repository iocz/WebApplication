package servlet;

import beans.UserWorking;
import func.Calendar;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

/**
 * Created by iocz on 07/10/15.
 */
@WebServlet(urlPatterns = "/registrationServlet")
public class RegistrationServlet extends HttpServlet {
    @EJB
    private UserWorking userBean;

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws
            ServletException, IOException {
        String login = request.getParameter("logFieldReg");
        String pass1 = request.getParameter("passFieldReg");
        String pass2 = request.getParameter("repeatPass");
        if ((login != null)&&(pass1.equals(pass2))&&(pass1 != null)&&(pass2 != null)) {
            try {
                //TODO ajax registration suspect.
                Calendar.setUserId(userBean.addUser(login, pass1));
            }
            catch (SQLException e) {
                //System.err.println("Недопустимые логин и/или пароль");
                e.printStackTrace();
            }
            response.sendRedirect("index.jsp?news=true");
        } else {
            Calendar.setIsWrongLoginPass(true);
            response.sendRedirect("index.jsp?news=true");
        }
    }
}
