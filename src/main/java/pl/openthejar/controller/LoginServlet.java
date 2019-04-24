package pl.openthejar.controller;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");

        String login = req.getParameter("username");
        String pass = req.getParameter("password");

        if(login.equals("admin@wp.pl") && pass.equals("admin")) {
            req.getRequestDispatcher("/index.jsp").forward(req, resp);
        } else {
            req.setAttribute("errorMessage", "Login or password");
            req.getRequestDispatcher("/index.jsp").forward(req, resp);
        }
    }
}
