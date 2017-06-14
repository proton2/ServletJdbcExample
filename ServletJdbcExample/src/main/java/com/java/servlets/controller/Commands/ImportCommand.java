package com.java.servlets.controller.Commands;

import com.java.servlets.controller.PageURL;
import com.java.servlets.controller.Service.ServletHelper;
import com.java.servlets.dao.ImportDao;
import com.java.servlets.model.WorkTask;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;

/**
 * Created by b.yacenko on 14.06.2017.
 */
public class ImportCommand implements com.java.servlets.controller.ActionCommand {
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        String buttonPressed = req.getParameter("button");
        if (buttonPressed.equalsIgnoreCase("Import from excel")) {
            Collection<WorkTask> workTaskList = null;
            try {
                workTaskList = ServletHelper.importWorkTasks(req);
            } catch (IOException | ServletException e) {
                e.printStackTrace();
            }
            if (workTaskList != null && !workTaskList.isEmpty()) {
                ImportDao importDao = new ImportDao();
                importDao.importCollection(workTaskList);
            }
            return PageURL.LIST_WORKTASK_FORWARD;
        }
        return PageURL.ERROR_PAGE;
    }
}
