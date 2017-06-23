package com.java.servlets.controller.Commands;

import com.java.servlets.controller.ActionCommand;
import com.java.servlets.controller.PageURL;
import com.java.servlets.dao.impl.WorkTaskDaoImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by b.yacenko on 13.06.2017.
 */
public class DeleteWorkTaskCommand implements ActionCommand {
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Long userId = Long.parseLong(req.getParameter("id"));
        WorkTaskDaoImpl.getInstance().delete(userId);
        return PageURL.LIST_WORKTASK_ACTION;
    }
}