package com.java.servlets.controller.Commands;

import com.java.servlets.controller.ActionCommand;
import com.java.servlets.controller.PageURL;
import com.java.servlets.controller.Service.ServletHelper;
import com.java.servlets.dao.impl.AttachDaoImpl;
import com.java.servlets.model.Attach;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by b.yacenko on 14.06.2017.
 */
public class SaveAttachFileCommand implements ActionCommand {
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Attach attach = null;
        try {
            attach = ServletHelper.getAttachFromRequest(req);
        } catch (IOException | ServletException e) {
            e.printStackTrace();
        }
        AttachDaoImpl attachDao = AttachDaoImpl.getInstance();

        if (attach.getFileName() != null && attach.getId() == null){
            attachDao.insert(attach);
            return PageURL.EDIT_WORKTASK_ACTION + attach.getWorkTask().getId();
        }
        else if ((attach.getFileName() != null && attach.getId() != null))
        {
            attachDao.update(attach);
            return PageURL.EDIT_WORKTASK_ACTION + attach.getWorkTask().getId();
        }

        if (attach.getFileName() == null){
            req.setAttribute("wt_id", attach.getWorkTask().getId());
            req.setAttribute("attach", attach);
            System.out.println("<font color=red>Filename is empty.</font>");
            return PageURL.INSERT_OR_EDIT_ATTACH;
        }
        return PageURL.ERROR_PAGE;
    }
}
