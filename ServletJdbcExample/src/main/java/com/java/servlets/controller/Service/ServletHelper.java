package com.java.servlets.controller.Service;

import com.java.servlets.dao.AuthorizationDao;
import com.java.servlets.dao.impl.UserDaoImpl;
import com.java.servlets.model.Attach;
import com.java.servlets.model.TaskStatus;
import com.java.servlets.model.User;
import com.java.servlets.model.UserRole;
import com.java.servlets.model.WorkNote;
import com.java.servlets.model.WorkTask;
import com.java.servlets.util.SysHelper;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

/**
 * Created by proton2 on 09.10.2016.
 */
public class ServletHelper {

    private static final Logger LOGGER = LoggerFactory.getLogger(SysHelper.class);

    public static WorkTask getWorkTaskFromRequest(HttpServletRequest request) {
        WorkTask wt = new WorkTask();

        String id = request.getParameter("id");
        if (id != null && !id.isEmpty()) {
            wt.setId(Long.parseLong(id));
        }

        String userIdStr = request.getParameter("taskuser_id");
        if (userIdStr != null && !userIdStr.isEmpty()) {
            Long userId = Long.parseLong(userIdStr);
            UserDaoImpl userDao = UserDaoImpl.getInstance();
            User user = userDao.getById(userId);
            //User user = (User) DaoFactory.getById(userId, User.class);
            wt.setTaskUser(user);
        }

        wt.setCaption(request.getParameter("caption"));
        wt.setTaskContext(request.getParameter("textarea1"));
        wt.setTaskStatus(request.getParameter("taskstatus") == null ?
                null :
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

    public static User getUserFromRequest(HttpServletRequest request) {
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

    public static WorkNote getWorkNoteFromRequest(HttpServletRequest request) {
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

    public static Collection<WorkTask> importWorkTasks(HttpServletRequest request) throws IOException, ServletException {
        Part filePart = request.getPart("excelFile");
        String partFile = SysHelper.getFileName1(filePart);
        if (partFile.isEmpty()) {
            return null;
        }
        InputStream inputStream = filePart.getInputStream();

        String fileExt = SysHelper.getFileExt(partFile);
        Workbook wb = null;
        if (fileExt.equalsIgnoreCase("xls")) {
            wb = new HSSFWorkbook(inputStream);
        } else if (fileExt.equalsIgnoreCase("xlsx")) {
            wb = new XSSFWorkbook(inputStream);
        } else {
            return null;
        }

        Sheet sheet = wb.getSheetAt(0);
        Iterator<Row> it = sheet.iterator();
        if (it.hasNext()) {
            it.next();
        } else {
            return null;
        }
        ArrayList<WorkTask> workTaskList = new ArrayList<>(sheet.getPhysicalNumberOfRows() - 1);
        AuthorizationDao dao = new AuthorizationDao();

        while (it.hasNext()) {
            Row row = it.next();
            WorkTask workTask = new WorkTask();
            workTask.setCaption(row.getCell(1).getStringCellValue());
            workTask.setTaskContext(row.getCell(2).getStringCellValue());
            try {
                workTask.setTaskDate(row.getCell(3).getDateCellValue());
            } catch (Exception e) {
                continue;
            }
            try {
                workTask.setDeadLine(row.getCell(4).getDateCellValue());
            } catch (Exception e) {
                continue;
            }

            Long userId = dao.getUserId(row.getCell(5).getStringCellValue());
            if (userId == -1) {
                continue;
            }
            User user = new User();
            user.setId(userId);
            workTask.setTaskUser(user);

            String taskStatus = row.getCell(6).getStringCellValue();
            if (taskStatus.equalsIgnoreCase("new")) {
                workTask.setTaskStatus(TaskStatus.NEW);
            } else if (taskStatus.equalsIgnoreCase("actual")) {
                workTask.setTaskStatus(TaskStatus.ACTUAL);
            } else if (taskStatus.equalsIgnoreCase("closed")) {
                workTask.setTaskStatus(TaskStatus.CLOSED);
            } else {
                continue;
            }
            workTaskList.add(workTask);
        }

        return workTaskList;
    }

    public static Attach getAttachFromRequest(HttpServletRequest request) throws IOException, ServletException {
        Attach attach = new Attach();

        String uploadFilePath = request.getServletContext().getInitParameter("uploads") + File.separator;
        File fileSaveDir = new File(uploadFilePath);
        if (!fileSaveDir.exists()) {
            fileSaveDir.mkdirs();
        }
        Part filePart = request.getPart("attachFile");
        String partFile = SysHelper.getFileName1(filePart);
        String fname = partFile.isEmpty() ?
                request.getParameter("fName") :
                partFile.substring(partFile.lastIndexOf("\\") + 1);

        attach.setCaption(request.getParameter("caption"));
        attach.setFileName(fname.isEmpty() ? null : fname);

        File f = new File(uploadFilePath + fname);
        if (!f.exists()) {
            filePart.write(uploadFilePath + fname);
            if (!partFile.isEmpty() && !request.getParameter("fName").isEmpty()) {
                SysHelper.deleteFile(uploadFilePath + request.getParameter("fName"));
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
}
