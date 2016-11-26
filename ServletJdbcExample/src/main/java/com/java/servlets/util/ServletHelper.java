package com.java.servlets.util;

import com.java.servlets.controller.LoginServlet;
import com.java.servlets.dao.DaoFactory;
import com.java.servlets.model.Attach;
import com.java.servlets.model.TaskStatus;
import com.java.servlets.model.User;
import com.java.servlets.model.UserRole;
import com.java.servlets.model.WorkNote;
import com.java.servlets.model.WorkTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by proton2 on 09.10.2016.
 */
public class ServletHelper {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoginServlet.class);

    public WorkTask getWorkTaskFromRequest(HttpServletRequest request) {
        WorkTask wt = new WorkTask();

        String id = request.getParameter("id");
        if (id != null && !id.isEmpty()) {
            wt.setId(Long.parseLong(id));
        }

        String userIdStr = request.getParameter("taskuser_id");
        if (userIdStr != null && !userIdStr.isEmpty()) {
            Long userId = Long.parseLong(userIdStr);
            User user = (User) DaoFactory.getById(userId, User.class);
            wt.setTaskUser(user);
        }

        wt.setCaption(request.getParameter("caption"));
        wt.setTaskContext(request.getParameter("textarea1"));
        wt.setTaskStatus(request.getParameter("taskstatus") == null ?
                null:
                TaskStatus.values()[Integer.parseInt(request.getParameter("taskstatus"))]);

        try {
            Date taskdate = request.getParameter("taskDate") == null ? null :
                    new SimpleDateFormat("dd/MM/yyyy").parse(request.getParameter("taskDate"));
            wt.setTaskDate(taskdate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        try {
            Date deadline = request.getParameter("deadLine") == null ? null :
                    new SimpleDateFormat("dd/MM/yyyy").parse(request.getParameter("deadLine"));
            wt.setDeadLine(deadline);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return wt;
    }

    public User getUserFromRequest(HttpServletRequest request) {
        User user = new User();
        user.setFirstName(request.getParameter("firstname"));
        user.setLastName(request.getParameter("lastname"));
        user.setCaption(request.getParameter("caption"));
        user.setEmail(request.getParameter("email"));
        user.setLogin(request.getParameter("login"));
        user.setPassword(request.getParameter("password"));
        user.setRole(UserRole.values()[Integer.parseInt(request.getParameter("role"))]);

        String id = request.getParameter("id");
        if (id != null && !id.isEmpty()) {
            user.setId(Long.parseLong(id));
        }

        return user;
    }

    public WorkNote getWorkNoteFromRequest(HttpServletRequest request) {
        WorkNote wn = new WorkNote();

        String id = request.getParameter("worknote_id");
        if (id != null && !id.isEmpty()) {
            wn.setId(Long.parseLong(id));
        }

        Date noteDate = null;
        try {
            noteDate = request.getParameter("noteDate") == null ? new Date() :
                    new SimpleDateFormat("dd/MM/yyyy").parse(request.getParameter("noteDate"));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        wn.setNoteDate(noteDate);

        Long userId = (Long) request.getSession().getAttribute("user_id");//Long.parseLong((String) request.getSession().getAttribute("user_id"));
        User user = new User();
        user.setId(userId);
        wn.setNoteUser(user);

        Long workTaskId = Long.parseLong(request.getParameter("worktask_id"));
        WorkTask wt = new WorkTask();
        wt.setId(workTaskId);
        wn.setSubject(wt);

        wn.setCaption(request.getParameter("worknote_caption"));
        wn.setDescription(request.getParameter("textarea2"));

        return wn;
    }

    public Attach getAttachFromRequest(HttpServletRequest request) throws IOException, ServletException {
        Attach attach = new Attach();

        String uploadFilePath = request.getServletContext().getInitParameter("uploads") + File.separator;
        File fileSaveDir = new File(uploadFilePath);
        if (!fileSaveDir.exists()) {
            fileSaveDir.mkdirs();
        }
        Part filePart = request.getPart("attachFile");
        String partFile = getFileName1(filePart);
        String fname = partFile.isEmpty()?
                request.getParameter("fName") :
                partFile.substring(partFile.lastIndexOf("\\") + 1);

        attach.setCaption(request.getParameter("caption"));
        attach.setFileName(fname.isEmpty() ? null : fname);

        File f = new File(uploadFilePath + fname);
        if (!f.exists()) {
            filePart.write(uploadFilePath + fname);
            if (!partFile.isEmpty() && !request.getParameter("fName").isEmpty()){
                deleteAttach(uploadFilePath + request.getParameter("fName"));
                LOGGER.info(request.getParameter("fName") + " is overrided!");
            }
            LOGGER.info(f.getName() + " is upload!");
        }

        WorkTask wt = new WorkTask();
        wt.setId(Long.parseLong(request.getParameter("worktask_id")));
        attach.setWorkTask(wt);

        String id = request.getParameter("id");
        if (id != null && !id.isEmpty()) {
            attach.setId(Long.parseLong(id));
        }
        return attach;
    }

    public String getFileName(Part part) {
        String contentDisp = part.getHeader("content-disposition");
        String[] tokens = contentDisp.split(";");
        for (String token : tokens) {
            if (token.trim().startsWith("filename")) {
                return token.substring(token.lastIndexOf("\\") + 1);
            }
        }
        return "";
    }

    public String getFileName1(final Part part) {
        final String partHeader = part.getHeader("content-disposition");
        for (String content : part.getHeader("content-disposition").split(";")) {
            if (content.trim().startsWith("filename")) {
                return content.substring(
                        content.indexOf('=') + 1).trim().replace("\"", "");
            }
        }
        return null;
    }

    public void deleteAttach(String filename) {
        try {
            File file = new File(filename);
            if (file.exists()) {
                if (file.delete()) {
                    LOGGER.info(file.getName() + " is deleted!");
                } else {
                    LOGGER.info("Delete operation is failed.");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
