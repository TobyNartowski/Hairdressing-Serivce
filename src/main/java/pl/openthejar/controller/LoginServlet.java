package pl.openthejar.controller;

import pl.openthejar.dao.EntityDao;
import pl.openthejar.model.Client;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private EntityDao<Client> clientDao = new EntityDao<>(Client.class);
    private static final long serialVersionUID = 102831973239L;

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
