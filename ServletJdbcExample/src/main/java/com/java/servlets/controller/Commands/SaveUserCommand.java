package com.java.servlets.controller.Commands;

import com.java.servlets.controller.PageURL;
import com.java.servlets.controller.Service.ServletHelper;
import com.java.servlets.dao.impl.UserDaoImpl;
import com.java.servlets.dao.impl.UserViewDaoImpl;
import com.java.servlets.model.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by b.yacenko on 14.06.2017.
 */
public class SaveUserCommand implements com.java.servlets.controller.ActionCommand {
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        String button = req.getParameter("button");
        if (button.equalsIgnoreCase("Save user")) {
            User user1 = ServletHelper.getUserFromRequest(req);
            if (user1.getId() == null) {
                UserDaoImpl.getInstance().insert(user1);
            } else {
                UserDaoImpl.getInstance().update(user1);
            }
            req.setAttribute("users", UserViewDaoImpl.getInstance().getAll(0, 50));
            return PageURL.LIST_USER;
        }
        return PageURL.ERROR_PAGE;
    }
}
