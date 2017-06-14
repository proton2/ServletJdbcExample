package com.java.servlets.controller.Commands;

import com.java.servlets.controller.ActionCommand;
import com.java.servlets.controller.PageURL;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DownloadAttachCommand implements ActionCommand{
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse response) {
        String file = req.getParameter("att_filename");
        return PageURL.DOWNLOAD + file;
    }
}
