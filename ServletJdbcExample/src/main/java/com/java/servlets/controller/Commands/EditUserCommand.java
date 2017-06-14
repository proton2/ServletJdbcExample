package com.java.servlets.controller.Commands;

import com.java.servlets.controller.PageURL;
import com.java.servlets.dao.impl.UserDaoImpl;
import com.java.servlets.dao.impl.WorkTaskViewDaoImpl;
import com.java.servlets.model.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by b.yacenko on 14.06.2017.
 */
public class EditUserCommand implements com.java.servlets.controller.ActionCommand {
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        Long userId = Long.parseLong(req.getParameter("id"));
        User user = UserDaoImpl.getInstance().getById(userId);
        req.setAttribute("user", user);
        req.setAttribute("userTasks", WorkTaskViewDaoImpl.getInstance().getListById(user.getId()));
        return PageURL.EDIT_USER;
    }
}
