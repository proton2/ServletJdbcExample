package com.java.servlets.dao.impl;

import com.java.servlets.dao.AbstractDao;
import com.java.servlets.model.WorkTask;

/**
 * Created by proton2 on 03.06.2017.
 */
public class WorkTaskDaoImpl extends AbstractDao<WorkTask> {

    private static WorkTaskDaoImpl crudDAO;

    private WorkTaskDaoImpl(Class<WorkTask> type) {
        super(type);
    }

    public static synchronized WorkTaskDaoImpl getInstance() {
        if (crudDAO == null) {
            crudDAO = new WorkTaskDaoImpl(WorkTask.class);
        }
        return crudDAO;
    }
}
