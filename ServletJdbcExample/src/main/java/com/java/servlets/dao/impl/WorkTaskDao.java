package com.java.servlets.dao.impl;

import com.java.servlets.dao.AbstractDao;
import com.java.servlets.dao.Service.ResultSetMapper;
import com.java.servlets.model.Model;
import com.java.servlets.model.WorkTask;
import com.java.servlets.dao.Service.SqlXmlReader;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by proton2 on 06.08.2016.
 */
public class WorkTaskDao extends AbstractDao {

    public WorkTaskDao() {}

    @Override
    public void insertItem(Model item) throws SQLException {
        WorkTask workTask = (WorkTask) item;

        String insertSql = SqlXmlReader.getQuerryStr("sql.xml", "WorkTaskDao", "insertSql");
        PreparedStatement ps = getPreparedStatement(insertSql);
        ResultSetMapper.putEntityToPreparedStatement(ps, workTask);
        executeUpdate(ps);

        workTask.setId(getInsertId("WorkTask"));
        cachePut(workTask);
    }

    @Override
    public void updateItem(Model item) throws SQLException {
        cacheRemove(item);
        WorkTask workTask = (WorkTask) item;

        String updateSql = SqlXmlReader.getQuerryStr("sql.xml", "WorkTaskDao", "updateSql");
        PreparedStatement ps = getPreparedStatement(updateSql);
        ResultSetMapper.putEntityToPreparedStatement(ps, workTask);

        executeUpdate(ps);
        cachePut(workTask);
    }

    @Override
    public void delete(Long id) {
        String deleteSql = SqlXmlReader.getQuerryStr("sql.xml", "WorkTaskDao", "deleteSql");
        performDelete(id, deleteSql);
    }

    @Override
    public WorkTask getById(Long itemId) {
        WorkTask workTask = (WorkTask) cacheGet(itemId);
        if (workTask != null) return workTask;

        String getById = SqlXmlReader.getQuerryStr("sql.xml", "WorkTaskDao", "getById");
        PreparedStatement ps = getNavigablePreparedStatement(getById);
        ResultSet rs = executeGetById(ps, itemId);

        ResultSetMapper<WorkTask> resultSetMapper = new ResultSetMapper<>();
        workTask = resultSetMapper.mapRersultSetToObject(rs, WorkTask.class);

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