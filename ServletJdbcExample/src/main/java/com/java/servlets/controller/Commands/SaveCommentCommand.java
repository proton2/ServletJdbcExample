package com.java.servlets.controller.Commands;

import com.java.servlets.controller.ActionCommand;
import com.java.servlets.controller.PageURL;
import com.java.servlets.controller.Service.ServletHelper;
import com.java.servlets.dao.impl.WorkNoteDaoImpl;
import com.java.servlets.model.WorkNote;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by b.yacenko on 14.06.2017.
 */
public class SaveCommentCommand implements ActionCommand {

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        WorkNote wn = ServletHelper.getWorkNoteFromRequest(req);
        if (!wn.getDescription().isEmpty() && !wn.getCaption().isEmpty()) {
            if (wn.getId() == null) {
                WorkNoteDaoImpl.getInstance().insert(wn);
            } else {
                WorkNoteDaoImpl.getInstance().update(wn);
            }
        }
        return PageURL.EDIT_WORKTASK_ACTION + wn.getSubject().getId();
    }
}
