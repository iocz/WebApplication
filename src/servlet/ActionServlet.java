package servlet;

import beans.UserWorking;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
/**
 * Created by iocz on 12/09/15.
 */

/**
 * Servlet implementation class servlet.ActionServlet
 */
@WebServlet(urlPatterns = "/myservlet")
public class ActionServlet extends HttpServlet {
        @EJB
        //private HelloUser userBean;
        private UserWorking user;

        @Override
        protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            boolean name = false;
            try {
                name = user.isAuthenticate("FOG", "Ivov");
            } catch (SQLException e) {
                e.printStackTrace();
            }
            req.setAttribute("name",name);
            req.getRequestDispatcher("hello.jsp").forward(req,resp);
        }
}


