package com.java.servlets.controller.Commands;

import com.java.servlets.controller.PageURL;
import com.java.servlets.controller.Service.ServletHelper;
import com.java.servlets.model.WorkTask;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by b.yacenko on 14.06.2017.
 */
public class SetUserCommand implements com.java.servlets.controller.ActionCommand {
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        WorkTask wt = ServletHelper.getWorkTaskFromRequest(req);
        req.getSession().setAttribute("workTask", wt);
        new ListUsersCommand().execute(req, resp);
        return PageURL.SELECT_USER;
    }
}
