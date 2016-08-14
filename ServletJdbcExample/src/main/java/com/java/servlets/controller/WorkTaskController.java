package com.java.servlets.controller;

import com.java.servlets.dao.DaoFactory;
import com.java.servlets.model.User;
import com.java.servlets.model.WorkTask;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by proton2 on 06.08.2016.
 */
public class WorkTaskController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static String INSERT_OR_EDIT = "/workTask.jsp";
    private static String LIST_ITEMS = "/listWorkTask.jsp";
    private static String SELECT_USER = "/selectUser.jsp";

    public WorkTaskController(){
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String forward = "";
        String action = request.getParameter("action");

        if (action.equalsIgnoreCase("delete")){
            Long userId = Long.parseLong(request.getParameter("id"));
            DaoFactory.getWorkTaskDao().delete(userId);
            forward = LIST_ITEMS;
            request.setAttribute("workTasks", DaoFactory.getWorkTaskDao().getAll());
        }
        else if (action.equalsIgnoreCase("edit")){
            forward = INSERT_OR_EDIT;
            Long workTaskId = Long.parseLong(request.getParameter("id"));
            WorkTask workTask = DaoFactory.getWorkTaskDao().getById(workTaskId);
            request.setAttribute("taskuser", workTask.getTaskUser());
            request.setAttribute("workTask", workTask);
        }
        else if (action.equalsIgnoreCase("select_user_list")) {
            request.setAttribute("users", DaoFactory.getUserDao().getAll());
            forward = SELECT_USER;
        }
        else if (action.equalsIgnoreCase("select_user")){
            Long userId = Long.parseLong(request.getParameter("id"));
            User user = DaoFactory.getUserDao().getById(userId);
            request.setAttribute("taskuser", user);
            forward = INSERT_OR_EDIT;
        }
        else if (action.equalsIgnoreCase("list")){
            forward = LIST_ITEMS;
            request.setAttribute("workTasks", DaoFactory.getWorkTaskDao().getAll());
        } else {
            forward = INSERT_OR_EDIT;
        }

        RequestDispatcher view = request.getRequestDispatcher(forward);
        view.forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        WorkTask wt = new WorkTask();

        String wtId = request.getParameter("id");

        Long userId = Long.parseLong(request.getParameter("taskuser_id"));
        User user = DaoFactory.getUserDao().getById(userId);
        wt.setTaskUser(user);

        wt.setCaption(request.getParameter("caption"));
        wt.setTaskContext(request.getParameter("textarea1"));

        try {
            Date taskdate = new SimpleDateFormat("dd/MM/yyyy").parse(request.getParameter("taskDate"));
            wt.setTaskDate(taskdate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        try {
            Date deadline = new SimpleDateFormat("dd/MM/yyyy").parse(request.getParameter("deadLine"));
            wt.setDeadLine(deadline);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (wtId == null || wtId.isEmpty()){
            DaoFactory.getWorkTaskDao().insert(wt);
        }
        else
        {
            wt.setId(Long.parseLong(wtId));
            DaoFactory.getWorkTaskDao().update(wt);
        }

        RequestDispatcher view = request.getRequestDispatcher(LIST_ITEMS);
        request.setAttribute("workTasks", DaoFactory.getWorkTaskDao().getAll());
        view.forward(request, response);
    }
}
