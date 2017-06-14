package com.java.servlets.controller.Commands;

import com.java.servlets.controller.PageURL;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by b.yacenko on 14.06.2017.
 */
public class InsertWorkTaskCommand implements com.java.servlets.controller.ActionCommand {
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        return PageURL.INSERT_OR_EDIT_WORKTASK;
    }
}
