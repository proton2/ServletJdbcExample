package com.java.servlets.controller;

import com.java.servlets.dao.DaoFactory;
import com.java.servlets.dao.ImportDao;
import com.java.servlets.model.Attach;
import com.java.servlets.model.User;
import com.java.servlets.model.UserView;
import com.java.servlets.model.WorkNote;
import com.java.servlets.model.WorkTask;
import com.java.servlets.model.WorkTaskView;
import com.java.servlets.controller.Service.ServletHelper;
import com.java.servlets.util.SysHelper;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.List;

/**
 * Created by proton2 on 06.08.2016.
 */
public class WorkTaskController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static String INSERT_OR_EDIT = "/workTask.jsp";
    private static String LIST_ITEMS = "/listWorkTask.jsp";
    private static String SELECT_USER = "/selectUser.jsp";
    private static String INSERT_OR_EDIT_ATTACH = "/attach.jsp";
    private static String EDIT_WORKTASK = "/WorkTaskController?action=edit&id=";
    private static String EDIT_WORKTASK_REDIRECT = "/ServletJdbcExample/WorkTaskController?action=edit&id=";
    private static String DOWNLOAD = "/download?downloadfile=";
    private static String LIST_WORKTASK_REDIRECT = "/ServletJdbcExample/WorkTaskController?action=list";
    private static String LIST_WORKTASK_FORWARD = "/WorkTaskController?action=list";

    private String forward = "";
    private int page = 1;
    private int recordsPerPage = 5;

    public WorkTaskController() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if (action.equalsIgnoreCase("delete")) {
            Long userId = Long.parseLong(request.getParameter("id"));
            DaoFactory.delete(userId, WorkTask.class);
            forward = LIST_WORKTASK_FORWARD;
        } else if (action.equalsIgnoreCase("edit")) {
            forward = INSERT_OR_EDIT;
            Long workTaskId = Long.parseLong(request.getParameter("id"));
            WorkTask workTask = (WorkTask) DaoFactory.getById(workTaskId, WorkTask.class);
            request.setAttribute("taskuser", workTask.getTaskUser());
            request.setAttribute("workTask", workTask);
            request.setAttribute("attaches", DaoFactory.getListById(workTask.getId(), Attach.class));
            request.setAttribute("notes", DaoFactory.getListById(workTask.getId(), WorkNote.class));
        } else if (action.equalsIgnoreCase("select_user")) {
            Long userId = Long.parseLong(request.getParameter("id"));
            User user = (User) DaoFactory.getById(userId, User.class);
            WorkTask wt = (WorkTask) request.getSession().getAttribute("workTask");
            request.setAttribute("workTask", wt);
            request.setAttribute("taskuser", user);
            if (wt.getId()!=null) {
                request.setAttribute("attaches", DaoFactory.getListById(wt.getId(), Attach.class));
                request.setAttribute("notes", DaoFactory.getListById(wt.getId(), WorkNote.class));
            }
            forward = INSERT_OR_EDIT;
        } else if (action.equalsIgnoreCase("delete_attach")) {
            Long attachId = Long.parseLong(request.getParameter("attach_id"));
            DaoFactory.delete(attachId, Attach.class);
            String uploadFilePath = request.getServletContext().getInitParameter("uploads") + File.separator;
            String delfile = request.getParameter("att_filename");
            SysHelper.deleteFile(uploadFilePath + delfile);
            forward = EDIT_WORKTASK + request.getParameter("worktask_id");
        } else if (action.equalsIgnoreCase("download_attach")) {
            String file = request.getParameter("att_filename");
            forward = DOWNLOAD + file;
        } else if (action.equalsIgnoreCase("edit_attach")) {
            Long wt_id = Long.parseLong(request.getParameter("worktask_id"));
            request.setAttribute("wt_id", wt_id);
            Long attachId = Long.parseLong(request.getParameter("attach_id"));
            Attach attach = (Attach) DaoFactory.getById(attachId, Attach.class);
            request.setAttribute("attach", attach);
            forward = INSERT_OR_EDIT_ATTACH;
        } else if (action.equalsIgnoreCase("insert_attach")) {
            Long wt_id = Long.parseLong(request.getParameter("worktask_id").isEmpty() ? "-1" : request.getParameter("worktask_id"));
            if (wt_id!=-1) {
                request.setAttribute("wt_id", wt_id);
                forward = INSERT_OR_EDIT_ATTACH;
            }
        } else if (action.equalsIgnoreCase("list")) {
            forward = LIST_ITEMS;
            if(request.getParameter("page") != null)
                page = Integer.parseInt(request.getParameter("page"));
            List<WorkTaskView> items =
                    (List<WorkTaskView>) DaoFactory.getAll(WorkTaskView.class, (page-1)*recordsPerPage, recordsPerPage);
            int numOfRecords = DaoFactory.getNumOfRecords(WorkTaskView.class);
            int noOfPages = (int) Math.ceil(numOfRecords * 1.0 / recordsPerPage);
            request.setAttribute("workTasks", items);
            request.setAttribute("noOfPages", noOfPages);
            request.setAttribute("currentPage", page);
        } else if (action.equalsIgnoreCase("open_comment")) {
            Long noteId = Long.parseLong(request.getParameter("note_id"));
            WorkNote note = (WorkNote) DaoFactory.getById(noteId, WorkNote.class);
            Long workTaskId = Long.parseLong(request.getParameter("worktask_id"));
            request.setAttribute("worknote", note);
            forward = EDIT_WORKTASK + workTaskId;
        }else if (action.equalsIgnoreCase("delete_comment")) {
            Long noteId = Long.parseLong(request.getParameter("note_id"));
            DaoFactory.delete(noteId, WorkNote.class);
            Long workTaskId = Long.parseLong(request.getParameter("worktask_id"));
            forward = EDIT_WORKTASK + workTaskId;
        } else {
            forward = INSERT_OR_EDIT;
        }

        RequestDispatcher view = request.getRequestDispatcher(forward);
        view.forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String buttonPressed = request.getParameter("button");

        if (buttonPressed.equalsIgnoreCase("Save")) {
            WorkTask wt = ServletHelper.getWorkTaskFromRequest(request);
            if (wt.getId() == null) {
                DaoFactory.insert(wt);
            } else {
                DaoFactory.update(wt);
            }
            request.getSession().removeAttribute("workTask");
            response.sendRedirect(LIST_WORKTASK_REDIRECT);
        } else if (buttonPressed.equalsIgnoreCase("Set user")) {
            WorkTask wt = ServletHelper.getWorkTaskFromRequest(request);
            request.getSession().setAttribute("workTask", wt);
            request.setAttribute("users", DaoFactory.getAll(UserView.class, 0, 0));
            forward = SELECT_USER;
            RequestDispatcher view = request.getRequestDispatcher(forward);
            view.forward(request, response);
        } else if (buttonPressed.equalsIgnoreCase("Save comment")) {
            WorkNote wn = ServletHelper.getWorkNoteFromRequest(request);
            if (!wn.getDescription().isEmpty() && !wn.getCaption().isEmpty()) {
                if (wn.getId() == null) {
                    DaoFactory.insert(wn);
                } else {
                    DaoFactory.update(wn);
                }
            }
            response.sendRedirect(EDIT_WORKTASK_REDIRECT + wn.getSubject().getId());
        } else if (buttonPressed.equalsIgnoreCase("Import")) {
            Collection<WorkTask> workTaskList = ServletHelper.importWorkTasks(request);
            if (workTaskList!=null && !workTaskList.isEmpty()) {
                ImportDao importDao = new ImportDao();
                importDao.importCollection(workTaskList);
            }
            response.sendRedirect(LIST_WORKTASK_REDIRECT);
        }
    }
}