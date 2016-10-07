package com.java.servlets.util;

import com.java.servlets.dao.DaoFactory;
import com.java.servlets.model.User;
import com.java.servlets.model.WorkTask;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by proton2 on 09.10.2016.
 */
public class ServletHelper {
    public WorkTask getWorkTaskFromRequest(HttpServletRequest request){
        WorkTask wt = new WorkTask();

        String id = request.getParameter("id");
        if (id!=null && !id.isEmpty()){
            wt.setId(Long.parseLong(id));
        }

        String userIdStr = request.getParameter("taskuser_id");
        if (userIdStr!=null && !userIdStr.isEmpty()) {
            Long userId = Long.parseLong(userIdStr);
            User user = (User) DaoFactory.getById(userId, true, User.class);
            wt.setTaskUser(user);
        }

        wt.setCaption(request.getParameter("caption"));
        wt.setTaskContext(request.getParameter("textarea1"));

        try {
            Date taskdate = new SimpleDateFormat("dd/MM/yyyy").parse(request.getParameter("taskDate"));
            wt.setTaskDate(taskdate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        try {
            Date deadline = new SimpleDateFormat("dd/MM/yyyy").parse(request.getParameter("deadLine"));
            wt.setDeadLine(deadline);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return wt;
    }

    public User getUserFromRequest(HttpServletRequest request){
        User user = new User();
        user.setFirstName(request.getParameter("firstname"));
        user.setLastName(request.getParameter("lastname"));
        user.setCaption(request.getParameter("caption"));
        user.setEmail(request.getParameter("email"));

        String id = request.getParameter("id");
        if (id!=null && !id.isEmpty()){
            user.setId(Long.parseLong(id));
        }

        return user;
    }
}
