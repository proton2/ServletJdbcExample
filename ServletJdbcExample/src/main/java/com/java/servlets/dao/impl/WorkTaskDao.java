package com.java.servlets.dao.impl;

import com.java.servlets.dao.AbstractDao;
import com.java.servlets.dao.Service.ResultSetMapper;
import com.java.servlets.model.Model;
import com.java.servlets.model.WorkTask;
import com.java.servlets.util.SqlXmlReader;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by proton2 on 06.08.2016.
 */
public class WorkTaskDao extends AbstractDao {
    private String insertSql, updateSql, deleteSql, getById;

    public WorkTaskDao() {
        SqlXmlReader sl = new SqlXmlReader();
        insertSql = sl.getQuerry("sql.xml", "WorkTaskDao", "insertSql");
        updateSql = sl.getQuerry("sql.xml", "WorkTaskDao", "updateSql");
        deleteSql = sl.getQuerry("sql.xml", "WorkTaskDao", "deleteSql");
        getById = sl.getQuerry("sql.xml", "WorkTaskDao", "getById");
    }

    @Override
    public void insertItem(Model item) throws SQLException {
        WorkTask workTask = (WorkTask) item;

        PreparedStatement ps = getPreparedStatement(insertSql);
        ps.setLong(1, workTask.getTaskUser().getId());
        ps.setString(2, workTask.getCaption());
        ps.setString(3, workTask.getTaskContext());
        ps.setDate(4, new java.sql.Date(workTask.getTaskDate().getTime()));
        ps.setDate(5, new java.sql.Date(workTask.getDeadLine().getTime()));
        ps.setString(6, workTask.getTaskStatus().name());
        executeUpdate(ps);

        workTask.setId(getInsertId("WorkTask"));
        cachePut(workTask);
    }

    @Override
    public void updateItem(Model item) throws SQLException{
        cacheRemove(item);
        WorkTask wt = (WorkTask) item;

        PreparedStatement ps = getPreparedStatement(updateSql);
        ps.setLong(1, wt.getTaskUser().getId());
        ps.setString(2, wt.getCaption());
        ps.setString(3, wt.getTaskContext());
        ps.setDate(4, new java.sql.Date(wt.getTaskDate().getTime()));
        ps.setDate(5, new java.sql.Date(wt.getDeadLine().getTime()));
        ps.setString(6, wt.getTaskStatus().name());
        ps.setLong(7, item.getId());

        executeUpdate(ps);
        cachePut(wt);
    }

    @Override
    public void delete(Long id) {
        performDelete(id, deleteSql);
    }

    @Override
    public WorkTask getById(Long itemId) {
        WorkTask workTask = (WorkTask) cacheGet(itemId);
        if(workTask != null) return workTask;

        PreparedStatement ps = getNavigablePreparedStatement(getById);
        ResultSet rs = executeGetById(ps, itemId);
        workTask = (WorkTask) ResultSetMapper.staticMapRersultSetToObject(rs, WorkTask.class);
        closeResultSet(ps, rs);

        cachePut(workTask);
        return workTask;
    }

    @Override
    public int getNumOfRecords() {
        return 0;
    }

    @Override
    public List<WorkTask> getListById(Long itemId) {
        return null;
    }

    @Override
    public List<WorkTask> getAll(int offcet, int limit) {
        return null;
    }
}