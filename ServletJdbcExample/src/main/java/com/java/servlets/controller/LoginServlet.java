package com.java.servlets.controller;

import com.java.servlets.dao.AuthorizationDao;
import com.java.servlets.model.UserRole;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by proton2 on 29.10.2016.
 */
public class LoginServlet extends HttpServlet {
    private static String LIST_ITEMS = "/WorkTaskController?action=list";
    private static String LOGIN_PAGE = "/login.jsp";
    private String forward = "";

    private static final Logger LOGGER = LoggerFactory.getLogger(LoginServlet.class);

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if (action != null && action.equalsIgnoreCase("logout")) {
            HttpSession session = request.getSession(false);
            if (session != null) {
                LOGGER.info(String.format("User logout: %s, role: %s",
                        session.getAttribute("user"), session.getAttribute("role")));
                session.invalidate();
            }
            forward = LOGIN_PAGE;
        } if (action != null && action.equalsIgnoreCase("login")) {
            forward = LOGIN_PAGE;
        } else {
            forward = LOGIN_PAGE;
        }

        RequestDispatcher view = request.getRequestDispatcher(forward);
        view.forward(request, response);
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter("login");
        String password = req.getParameter("password");

        AuthorizationDao dao = new AuthorizationDao();
        UserRole role = dao.checkAccess(login, password);
        if (role!=null) {
            HttpSession session = req.getSession();
            session.setAttribute("user", login);
            session.setAttribute("user_id", dao.getUserId(login, password));
            session.setAttribute("role", role);
            session.setMaxInactiveInterval(30 * 60);
            LOGGER.info(String.format("Login successfull: login = %s, role: %s", login, session.getAttribute("role")));
            resp.sendRedirect("/ServletJdbcExample" + LIST_ITEMS);
        } else {
            LOGGER.info(String.format("Login unsuccessfull: login = %s, password: %s.", login, password));
            RequestDispatcher view = req.getRequestDispatcher(LOGIN_PAGE);
            PrintWriter out = resp.getWriter();
            out.println("<font color=red>User name or password is wrong.</font>");
            view.include(req, resp);
        }
    }
}