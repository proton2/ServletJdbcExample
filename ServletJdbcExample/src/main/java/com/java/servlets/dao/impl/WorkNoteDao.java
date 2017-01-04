package com.java.servlets.dao.impl;

import com.java.servlets.dao.AbstractDao;
import com.java.servlets.dao.Service.ResultSetMapper;
import com.java.servlets.model.Model;
import com.java.servlets.model.WorkNote;
import com.java.servlets.util.SqlXmlReader;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by proton2 on 21.11.2016.
 */
public class WorkNoteDao extends AbstractDao {
    private String insertSql, updateSql, deleteSql, getByIdSql, getListById;

    public WorkNoteDao() {
        SqlXmlReader sl = new SqlXmlReader();
        insertSql = sl.getQuerry("sql.xml", "WorkNoteDao", "insertSql");
        updateSql = sl.getQuerry("sql.xml", "WorkNoteDao", "updateSql");
        deleteSql = sl.getQuerry("sql.xml", "WorkNoteDao", "deleteSql");
        getByIdSql = sl.getQuerry("sql.xml", "WorkNoteDao", "getByIdSql");
        getListById = sl.getQuerry("sql.xml", "WorkNoteDao", "getListById");
    }

    @Override
    public void insertItem(Model item) throws SQLException {
        WorkNote workNote = (WorkNote) item;

        PreparedStatement ps = getPreparedStatement(insertSql);
        ps.setString(1, workNote.getCaption());
        ps.setDate(2, new java.sql.Date(workNote.getNoteDate().getTime()));
        ps.setString(3, workNote.getDescription());
        ps.setLong(4, workNote.getSubject().getId());
        ps.setLong(5, workNote.getNoteUser().getId());
        executeUpdate(ps);

        workNote.setId(getInsertId("WorkNote"));
        cachePut(workNote);
    }

    @Override
    public void updateItem(Model item) throws SQLException {
        cacheRemove(item);
        WorkNote workNote = (WorkNote) item;

        PreparedStatement ps = getPreparedStatement(updateSql);
        ps.setString(1, workNote.getCaption());
        ps.setDate(2, new java.sql.Date(workNote.getNoteDate().getTime()));
        ps.setString(3, workNote.getDescription());
        ps.setLong(4, workNote.getSubject().getId());
        ps.setLong(5, workNote.getNoteUser().getId());
        ps.setLong(6, item.getId());

        executeUpdate(ps);
        cachePut(workNote);
    }

    @Override
    public void delete(Long id) {
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
        PreparedStatement ps = getNavigablePreparedStatement(getListById);
        ResultSet rs = executeGetById(ps, itemId);

        ResultSetMapper<WorkNote> resultSetMapper = new ResultSetMapper<>();
        List<WorkNote> workNotes = resultSetMapper.mapRersultSetToList(rs, WorkNote.class);

        closeResultSet(ps, rs);
        return workNotes;
    }
}
