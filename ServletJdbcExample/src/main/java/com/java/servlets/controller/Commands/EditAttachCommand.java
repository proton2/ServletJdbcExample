package com.java.servlets.controller.Commands;

import com.java.servlets.controller.ActionCommand;
import com.java.servlets.controller.PageURL;
import com.java.servlets.dao.impl.AttachDaoImpl;
import com.java.servlets.model.Attach;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by b.yacenko on 14.06.2017.
 */
public class EditAttachCommand implements ActionCommand {
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        Long wt_id = Long.parseLong(req.getParameter("worktask_id"));
        req.setAttribute("wt_id", wt_id);
        Long attachId = Long.parseLong(req.getParameter("attach_id"));
        Attach attach = AttachDaoImpl.getInstance().getById(attachId);
        req.setAttribute("attach", attach);
        return PageURL.INSERT_OR_EDIT_ATTACH;
    }
}
