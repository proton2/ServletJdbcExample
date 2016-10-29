package com.java.servlets.controller;

import com.java.servlets.dao.AuthorizationDao;

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

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if (action != null && action.equalsIgnoreCase("logout")) {
            HttpSession session = request.getSession(false);
            if (session != null) {
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
        boolean access = dao.checkAccess(login, password);
        if (access) {
            HttpSession session = req.getSession();
            session.setAttribute("user", login);
            session.setMaxInactiveInterval(30 * 60);
            resp.sendRedirect("/ServletJdbcExample" + LIST_ITEMS);
        } else {
            RequestDispatcher view = req.getRequestDispatcher(LOGIN_PAGE);
            PrintWriter out = resp.getWriter();
            out.println("<font color=red>User name or password is wrong.</font>");
            view.include(req, resp);
        }
    }
}