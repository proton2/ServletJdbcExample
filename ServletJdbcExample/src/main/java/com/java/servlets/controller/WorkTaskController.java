package com.java.servlets.controller;

import com.java.servlets.dao.DaoFactory;
import com.java.servlets.model.User;
import com.java.servlets.model.UserView;
import com.java.servlets.model.WorkTask;
import com.java.servlets.model.WorkTaskView;
import com.java.servlets.util.ServletHelper;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by proton2 on 06.08.2016.
 */
public class WorkTaskController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static String INSERT_OR_EDIT = "/workTask.jsp";
    private static String LIST_ITEMS = "/listWorkTask.jsp";
    private static String SELECT_USER = "/selectUser.jsp";

    private ServletHelper helper;
    private String forward = "";

    public WorkTaskController() {
        super();
        helper = new ServletHelper();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if (action.equalsIgnoreCase("delete")) {
            Long userId = Long.parseLong(request.getParameter("id"));
            DaoFactory.delete(userId, WorkTask.class);
            forward = LIST_ITEMS;
            request.setAttribute("workTasks", DaoFactory.getAll(WorkTask.class));
        } else if (action.equalsIgnoreCase("edit")) {
            forward = INSERT_OR_EDIT;
            Long workTaskId = Long.parseLong(request.getParameter("id"));
            WorkTask workTask = (WorkTask) DaoFactory.getById(workTaskId, true, WorkTask.class, "user");
            request.setAttribute("taskuser", DaoFactory.getById(workTask.getTaskUser().getId(), true, UserView.class));
            request.setAttribute("workTask", workTask);
        } else if (action.equalsIgnoreCase("select_user")) {
            Long userId = Long.parseLong(request.getParameter("id"));
            User user = (User) DaoFactory.getById(userId, true, User.class);
            request.setAttribute("workTask", request.getSession().getAttribute("workTask"));
            request.setAttribute("taskuser", user);
            forward = INSERT_OR_EDIT;
        } else if (action.equalsIgnoreCase("list")) {
            forward = LIST_ITEMS;
            request.setAttribute("workTasks", DaoFactory.getAll(WorkTaskView.class));
        } else {
            forward = INSERT_OR_EDIT;
        }

        RequestDispatcher view = request.getRequestDispatcher(forward);
        view.forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String buttonPressed = request.getParameter("button");
        WorkTask wt = helper.getWorkTaskFromRequest(request);

        if (buttonPressed.equalsIgnoreCase("Save")) {
            if (wt.getId() == null) {
                DaoFactory.insert(wt);
            } else {
                DaoFactory.update(wt);
            }
            request.getSession().removeAttribute("workTask");

            forward = LIST_ITEMS;
            request.setAttribute("workTasks", DaoFactory.getAll(WorkTaskView.class));
        } else if (buttonPressed.equalsIgnoreCase("Set user")) {
            request.getSession().setAttribute("workTask", wt);
            request.setAttribute("users", DaoFactory.getAll(UserView.class));
            forward = SELECT_USER;
        }

        RequestDispatcher view = request.getRequestDispatcher(forward);
        view.forward(request, response);
    }
}