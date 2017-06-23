package com.java.servlets.controller.Commands;

import com.java.servlets.controller.PageURL;
import com.java.servlets.controller.Service.ServletHelper;
import com.java.servlets.dao.impl.WorkTaskDaoImpl;
import com.java.servlets.model.WorkTask;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by b.yacenko on 14.06.2017.
 */
public class SaveWorkTaskCommand implements com.java.servlets.controller.ActionCommand {
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String button = req.getParameter("button");
        if (button.equalsIgnoreCase("save")) {
            WorkTask wt = ServletHelper.getWorkTaskFromRequest(req);
            if (wt.getId() == null) {
                WorkTaskDaoImpl.getInstance().insert(wt);
            } else {
                WorkTaskDaoImpl.getInstance().update(wt);
            }
            req.getSession().removeAttribute("workTask");
            return PageURL.LIST_WORKTASK_ACTION;
        } else if (button.equalsIgnoreCase("Set user")) {
            return new SetUserCommand().execute(req, resp);
        } else
            return PageURL.ERROR_PAGE;
    }
}
