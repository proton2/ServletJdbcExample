package com.java.servlets.controller.Commands;

import com.java.servlets.controller.ActionCommand;
import com.java.servlets.controller.PageURL;
import com.java.servlets.dao.impl.WorkTaskViewDaoImpl;
import com.java.servlets.model.WorkTaskView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by b.yacenko on 13.06.2017.
 */
public class ListWorktasksCommand implements ActionCommand {

    private int page = 1;
    private int recordsPerPage = 5;

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        if (req.getParameter("page") != null)
            page = Integer.parseInt(req.getParameter("page"));

        WorkTaskViewDaoImpl workTaskViewDao = WorkTaskViewDaoImpl.getInstance();
        List<WorkTaskView> items = workTaskViewDao.getAll((page - 1) * recordsPerPage, recordsPerPage);
        int numOfRecords = workTaskViewDao.getNumOfRecords("worktask");
        int noOfPages = (int) Math.ceil(numOfRecords * 1.0 / recordsPerPage);
        req.setAttribute("workTasks", items);
        req.setAttribute("noOfPages", noOfPages);
        req.setAttribute("currentPage", page);

        return PageURL.LIST_WORKTASK;
    }
}
