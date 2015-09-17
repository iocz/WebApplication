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
import java.io.PrintWriter;
import java.sql.SQLException;

/**
 * Created by iocz on 07/10/15.
 */
@WebServlet(urlPatterns = "/logInServlet")
public class LogInServlet extends HttpServlet {
    @EJB
    private UserWorking userBean;

    @Override
    protected void doPost(HttpServletRequest request,
                         HttpServletResponse response) throws ServletException,
            IOException{
        String user = request.getParameter("logField");
        String pass = request.getParameter("passField");
        try{
            response(response, userBean.isAuthenticate(user, pass), user);
        } catch (SQLException e) {
            System.err.println("Неправильные логин и/или пароль");
        }
    }

    private void response(HttpServletResponse response,
                          Boolean result, String login) throws IOException, SQLException{
        PrintWriter out = response.getWriter();
        if (result) {
            Calendar.setUserId(userBean.getUserId(login));
            Calendar.setShowNews(true);
            response.sendRedirect("index.jsp?news=true");
        } else {
            Calendar.setIsWrongLoginPass(true);
            response.sendRedirect("index.jsp");
        }
    }
}
