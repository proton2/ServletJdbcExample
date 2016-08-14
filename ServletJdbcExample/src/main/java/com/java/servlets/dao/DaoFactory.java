package com.java.servlets.dao;

import com.java.servlets.model.User;
import com.java.servlets.model.WorkTask;

/**
 * Created by proton2 on 13.08.2016.
 */
public class DaoFactory {
    private static ModelDao<User> userDao = null;
    private static ModelDao<WorkTask> workTaskDao = null;

    public static UserDao getUserDao(){
        if (userDao==null){
            userDao = new UserDao();
        }
        return (UserDao) userDao;
    }

    public static WorkTaskDao getWorkTaskDao(){
        if(workTaskDao==null){
            workTaskDao = new WorkTaskDao();
        }
        return (WorkTaskDao) workTaskDao;
    }
}
