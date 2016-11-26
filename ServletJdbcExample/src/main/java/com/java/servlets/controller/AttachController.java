package com.java.servlets.controller;

import com.java.servlets.dao.DaoFactory;
import com.java.servlets.model.Attach;
import com.java.servlets.util.ServletHelper;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by proton2 on 05.11.2016.
 */
public class AttachController extends HttpServlet {
    private static String INSERT_OR_EDIT = "/workTask.jsp";
    private static String EDIT_WORKTASK = "/ServletJdbcExample/WorkTaskController?action=edit&id=";
    private static String INSERT_OR_EDIT_ATTACH = "/attach.jsp";
    private String forward = "";

    private ServletHelper helper;

    public AttachController() {
        super();
        helper = new ServletHelper();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        forward = INSERT_OR_EDIT;
        RequestDispatcher view = request.getRequestDispatcher(forward);
        view.forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Attach attach = helper.getAttachFromRequest(request);

        if (attach.getFileName() != null && attach.getId() == null){
            DaoFactory.insert(attach);
            response.sendRedirect(EDIT_WORKTASK + attach.getWorkTask().getId());
        }
        else if ((attach.getFileName() != null && attach.getId() != null))
        {
            DaoFactory.update(attach);
            response.sendRedirect(EDIT_WORKTASK + attach.getWorkTask().getId());
        }

        if (attach.getFileName() == null){
            request.setAttribute("wt_id", attach.getWorkTask().getId());
            request.setAttribute("attach", attach);

            PrintWriter out = response.getWriter();
            out.println("<font color=red>Filename is empty.</font>");
            RequestDispatcher view = request.getRequestDispatcher(INSERT_OR_EDIT_ATTACH);
            view.include(request, response);
        }
    }
}