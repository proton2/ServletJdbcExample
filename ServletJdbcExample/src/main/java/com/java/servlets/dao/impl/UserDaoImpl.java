package com.java.servlets.dao.impl;

import com.java.servlets.dao.AbstractDao;
import com.java.servlets.model.User;

/**
 * Created by proton2 on 03.06.2017.
 */
public class UserDaoImpl extends AbstractDao<User> {
    private static UserDaoImpl crudDAO;

    private UserDaoImpl(Class<User> type) {
        super(type);
    }

    public static synchronized UserDaoImpl getInstance() {
        if (crudDAO == null) {
            crudDAO = new UserDaoImpl(User.class);
        }
        return crudDAO;
    }
}