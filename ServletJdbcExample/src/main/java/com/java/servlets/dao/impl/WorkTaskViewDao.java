package com.java.servlets.dao.impl;

import com.java.servlets.dao.AbstractDao;
import com.java.servlets.dao.Service.ResultSetMapper;
import com.java.servlets.model.Model;
import com.java.servlets.model.WorkTaskView;
import com.java.servlets.util.SqlXmlReader;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by proton2 on 23.10.2016.
 */
public class WorkTaskViewDao extends AbstractDao {
    private String getAllSql, getUserWorkTasks;

    public WorkTaskViewDao() {
        SqlXmlReader sl = new SqlXmlReader();
        getAllSql = sl.getQuerry("sql.xml", "WorkTaskViewDao", "getall");
        getUserWorkTasks = sl.getQuerry("sql.xml", "WorkTaskViewDao", "getUserWorkTasks");
    }

    @Override
    public List<WorkTaskView> getAll(int offset, int limit) {
        PreparedStatement ps = getNavigablePreparedStatement(getAllSql);
        ResultSet rs = executeGetAll(ps, offset, limit);

        ResultSetMapper<WorkTaskView> resultSetMapper = new ResultSetMapper<>();
        List<WorkTaskView> workTaskList = resultSetMapper.mapRersultSetToList(rs, WorkTaskView.class);

        closeResultSet(ps, rs);
        return workTaskList;
    }

    @Override
    public List<WorkTaskView> getListById(Long itemId) {
        PreparedStatement ps = getNavigablePreparedStatement(getUserWorkTasks);
        ResultSet rs = executeGetById(ps, itemId);

        ResultSetMapper<WorkTaskView> resultSetMapper = new ResultSetMapper<>();
        List<WorkTaskView> workTaskList = resultSetMapper.mapRersultSetToList(rs, WorkTaskView.class);

        closeResultSet(ps, rs);
        return workTaskList;
    }

    @Override
    public void insertItem(Model item) throws SQLException {}

    @Override
    public void updateItem(Model item) throws SQLException {}

    @Override
    public Long insert(Model item) {
        return null;
    }

    @Override
    public void update(Model item) {}

    @Override
    public void delete(Long id) {}

    @Override
    public WorkTaskView getById(Long itemId) {
        return null;
    }

    @Override
    public int getNumOfRecords() {
        return super.getNumOfRecords("worktask");
    }
}