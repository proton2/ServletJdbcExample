package com.java.servlets.dao.impl;

import com.java.servlets.dao.AbstractDao;
import com.java.servlets.dao.Service.ResultSetMapper;
import com.java.servlets.model.Model;
import com.java.servlets.model.WorkNote;
import com.java.servlets.dao.Service.SqlXmlReader;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by proton2 on 21.11.2016.
 */
public class WorkNoteDao extends AbstractDao {

    public WorkNoteDao() {}

    @Override
    public void insertItem(Model item) throws SQLException {
        WorkNote workNote = (WorkNote) item;

        String insertSql = SqlXmlReader.getQuerryStr("sql.xml", "WorkNoteDao", "insertSql");
        PreparedStatement ps = getPreparedStatement(insertSql);
        ResultSetMapper.putEntityToPreparedStatement(ps, workNote);
        executeUpdate(ps);

        workNote.setId(getInsertId("WorkNote"));
        cachePut(workNote);
    }

    @Override
    public void updateItem(Model item) throws SQLException {
        cacheRemove(item);
        WorkNote workNote = (WorkNote) item;
        String updateSql = SqlXmlReader.getQuerryStr("sql.xml", "WorkNoteDao", "updateSql");
        PreparedStatement ps = getPreparedStatement(updateSql);
        ResultSetMapper.putEntityToPreparedStatement(ps, workNote);
        executeUpdate(ps);
        cachePut(workNote);
    }

    @Override
    public void delete(Long id) {
        String deleteSql = SqlXmlReader.getQuerryStr("sql.xml", "WorkNoteDao", "deleteSql");
        performDelete(id, deleteSql);
    }

    @Override
    public List<WorkNote> getAll(int offcet, int limit) {
        return null;
    }

    @Override
    public WorkNote getById(Long itemId) {
        WorkNote wn = (WorkNote) cacheGet(itemId);
        if (wn != null) return wn;

        String getByIdSql = SqlXmlReader.getQuerryStr("sql.xml", "WorkNoteDao", "getByIdSql");
        PreparedStatement ps = getNavigablePreparedStatement(getByIdSql);
        ResultSet rs = executeGetById(ps, itemId);

        ResultSetMapper<WorkNote> resultSetMapper = new ResultSetMapper<>();
        wn = resultSetMapper.mapRersultSetToObject(rs, WorkNote.class);

        closeResultSet(ps, rs);
        cachePut(wn);
        return wn;
    }

    @Override
    public int getNumOfRecords() {
        return 0;
    }

    @Override
    public List<WorkNote> getListById(Long itemId) {
        String getListById = SqlXmlReader.getQuerryStr("sql.xml", "WorkNoteDao", "getListById");
        PreparedStatement ps = getNavigablePreparedStatement(getListById);
        ResultSet rs = executeGetById(ps, itemId);

        ResultSetMapper<WorkNote> resultSetMapper = new ResultSetMapper<>();
        List<WorkNote> workNotes = resultSetMapper.mapRersultSetToList(rs, WorkNote.class);

        closeResultSet(ps, rs);
        return workNotes;
    }
}
