package com.java.servlets.dao.impl;

import com.java.servlets.dao.AbstractDao;
import com.java.servlets.dao.Service.ResultSetMapper;
import com.java.servlets.model.Attach;
import com.java.servlets.model.Model;
import com.java.servlets.util.SqlXmlReader;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by proton2 on 05.11.2016.
 */
public class AttachDao extends AbstractDao {
    private String insertSql, updateSql, deleteSql, getListById, getByIdSql;

    public AttachDao() {
        SqlXmlReader sl = new SqlXmlReader();
        insertSql = sl.getQuerry("sql.xml", "AttachDao", "insertSql");
        updateSql = sl.getQuerry("sql.xml", "AttachDao", "updateSql");
        deleteSql = sl.getQuerry("sql.xml", "AttachDao", "deleteSql");
        getListById = sl.getQuerry("sql.xml", "AttachDao", "getListById");
        getByIdSql = sl.getQuerry("sql.xml", "AttachDao", "getByIdSql");
    }

    @Override
    public void insertItem(Model item) throws SQLException {
        Attach attach = (Attach) item;
        PreparedStatement ps = getPreparedStatement(insertSql);
        ps.setString(1, attach.getFileName());
        ps.setString(2, attach.getCaption());
        ps.setLong(3, attach.getWorkTask().getId());
        executeUpdate(ps);
        attach.setId(getInsertId("attach"));
    }

    @Override
    public void updateItem(Model item) throws SQLException {
        Attach attach = (Attach) item;

        PreparedStatement ps = getPreparedStatement(updateSql);
        ps.setString(1, attach.getFileName());
        ps.setString(2, attach.getCaption());
        ps.setLong(3, attach.getWorkTask().getId());
        ps.setLong(4, attach.getId());

        executeUpdate(ps);
    }

    @Override
    public void delete(Long id) {
        performDelete(id, deleteSql);
    }

    @Override
    public List<Attach> getAll(int offcet, int limit) {
        return null;
    }

    @Override
    public List<Attach> getListById(Long itemId) {
        PreparedStatement ps = getNavigablePreparedStatement(getListById);
        ResultSet rs = executeGetById(ps, itemId);

        ResultSetMapper<Attach> resultSetMapper = new ResultSetMapper<>();
        List<Attach> attaches = resultSetMapper.mapRersultSetToList(rs, Attach.class);

        closeResultSet(ps, rs);
        return attaches;
    }

    @Override
    public Attach getById(Long itemId) {
        PreparedStatement ps = getNavigablePreparedStatement(getByIdSql);
        ResultSet rs = executeGetById(ps, itemId);

        ResultSetMapper<Attach> resultSetMapper = new ResultSetMapper<>();
        Attach attach = resultSetMapper.mapRersultSetToObject(rs, Attach.class);

        closeResultSet(ps, rs);
        return attach;
    }

    @Override
    public int getNumOfRecords() {
        return 0;
    }
}
