package com.java.servlets.controller.Commands;

import com.java.servlets.controller.PageURL;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class InsertAttachCommand implements com.java.servlets.controller.ActionCommand {
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        Long wt_id = Long.parseLong(req.getParameter("worktask_id").isEmpty() ? "-1" : req.getParameter("worktask_id"));
        if (wt_id != -1) {
            req.setAttribute("wt_id", wt_id);
            return PageURL.INSERT_OR_EDIT_ATTACH;
        } else
            return PageURL.ERROR_PAGE;
    }
}
