package com.java.servlets.controller.Commands;

import com.java.servlets.controller.ActionCommand;
import com.java.servlets.controller.PageURL;
import com.java.servlets.dao.impl.WorkNoteDaoImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by b.yacenko on 14.06.2017.
 */
public class DeleteCommentCommand implements ActionCommand {
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        Long noteId = Long.parseLong(req.getParameter("note_id"));
        WorkNoteDaoImpl.getInstance().delete(noteId);
        Long workTaskId = Long.parseLong(req.getParameter("worktask_id"));
        return PageURL.EDIT_WORKTASK + workTaskId;
    }
}