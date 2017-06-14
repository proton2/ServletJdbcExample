package com.java.servlets.controller.Commands;

import com.java.servlets.controller.ActionCommand;
import com.java.servlets.controller.PageURL;
import com.java.servlets.dao.impl.AttachDaoImpl;
import com.java.servlets.dao.impl.UserDaoImpl;
import com.java.servlets.dao.impl.WorkNoteDaoImpl;
import com.java.servlets.model.User;
import com.java.servlets.model.WorkTask;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by b.yacenko on 14.06.2017.
 */
public class SelectUserCommand implements ActionCommand {
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        Long userId = Long.parseLong(req.getParameter("id"));
        User user = UserDaoImpl.getInstance().getById(userId);
        WorkTask wt = (WorkTask) req.getSession().getAttribute("workTask");
        req.setAttribute("workTask", wt);
        req.setAttribute("taskuser", user);
        if (wt!=null && wt.getId() != null) {
            req.setAttribute("attaches", AttachDaoImpl.getInstance().getListById(wt.getId()));
            req.setAttribute("notes", WorkNoteDaoImpl.getInstance().getListById(wt.getId()));
        }
        return PageURL.INSERT_OR_EDIT_WORKTASK;
    }
}
