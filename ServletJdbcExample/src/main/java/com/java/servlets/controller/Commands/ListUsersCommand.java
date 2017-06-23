package com.java.servlets.controller.Commands;

import com.java.servlets.controller.ActionCommand;
import com.java.servlets.controller.PageURL;
import com.java.servlets.dao.impl.UserViewDaoImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by b.yacenko on 13.06.2017.
 */
public class ListUsersCommand implements ActionCommand {
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.setAttribute("users", UserViewDaoImpl.getInstance().getAll(0, 50));
        return PageURL.LIST_USER;
    }
}