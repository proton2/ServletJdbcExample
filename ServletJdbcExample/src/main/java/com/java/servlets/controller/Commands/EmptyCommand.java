package com.java.servlets.controller.Commands;

import com.java.servlets.controller.ActionCommand;
import com.java.servlets.controller.PageURL;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by b.yacenko on 13.06.2017.
 */
public class EmptyCommand implements ActionCommand {
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        return PageURL.LOGIN_PAGE;
    }
}
