package com.java.servlets.dao.impl;

import com.java.servlets.dao.AbstractDao;
import com.java.servlets.model.UserView;

/**
 * Created by proton2 on 03.06.2017.
 */
public class UserViewDaoImpl extends AbstractDao<UserView> {
    private static UserViewDaoImpl crudDAO;

    private UserViewDaoImpl(Class<UserView> type) {
        super(type);
    }

    public static synchronized UserViewDaoImpl getInstance() {
        if (crudDAO == null) {
            crudDAO = new UserViewDaoImpl(UserView.class);
        }
        return crudDAO;
    }
}
