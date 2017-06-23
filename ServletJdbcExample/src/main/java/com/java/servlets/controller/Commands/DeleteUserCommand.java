package com.java.servlets.controller.Commands;

import com.java.servlets.controller.PageURL;
import com.java.servlets.dao.impl.UserDaoImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by b.yacenko on 14.06.2017.
 */
public class DeleteUserCommand implements com.java.servlets.controller.ActionCommand {
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Long userId = Long.parseLong(req.getParameter("id"));
        UserDaoImpl userDao = UserDaoImpl.getInstance();
        userDao.delete(userId);
        return PageURL.LIST_USER_ACTION;
    }
}
