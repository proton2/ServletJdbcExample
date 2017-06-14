package com.java.servlets.controller.Commands;

import com.java.servlets.controller.ActionCommand;
import com.java.servlets.controller.PageURL;
import com.java.servlets.dao.impl.AttachDaoImpl;
import com.java.servlets.util.SysHelper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;

/**
 * Created by b.yacenko on 14.06.2017.
 */
public class DeleteAttachCommand implements ActionCommand {
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp){
        Long attachId = Long.parseLong(req.getParameter("attach_id"));
        AttachDaoImpl.getInstance().delete(attachId);
        String uploadFilePath = req.getServletContext().getInitParameter("uploads") + File.separator;
        String delfile = req.getParameter("att_filename");
        SysHelper.deleteFile(uploadFilePath + delfile);
        return PageURL.EDIT_WORKTASK + req.getParameter("worktask_id");
    }
}
