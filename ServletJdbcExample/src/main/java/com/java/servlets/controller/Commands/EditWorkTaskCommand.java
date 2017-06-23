package com.java.servlets.controller.Commands;

import com.java.servlets.controller.ActionCommand;
import com.java.servlets.controller.PageURL;
import com.java.servlets.dao.impl.AttachDaoImpl;
import com.java.servlets.dao.impl.WorkNoteDaoImpl;
import com.java.servlets.dao.impl.WorkTaskDaoImpl;
import com.java.servlets.model.WorkTask;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by b.yacenko on 13.06.2017.
 */
public class EditWorkTaskCommand implements ActionCommand {
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Long workTaskId = Long.parseLong(req.getParameter("id"));
        WorkTask workTask = WorkTaskDaoImpl.getInstance().getById(workTaskId);
        req.setAttribute("taskuser", workTask.getTaskUser());
        req.setAttribute("workTask", workTask);
        req.setAttribute("attaches", AttachDaoImpl.getInstance().getListById(workTask.getId()));
        req.setAttribute("notes", WorkNoteDaoImpl.getInstance().getListById(workTask.getId()));
        return PageURL.INSERT_OR_EDIT_WORKTASK;
    }
}
