package com.java.servlets.dao.impl;

import com.java.servlets.dao.AbstractDao;
import com.java.servlets.dao.Service.ResultSetMapper;
import com.java.servlets.dao.Service.SqlXmlReader;
import com.java.servlets.model.Attach;
import com.java.servlets.model.Model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by proton2 on 05.11.2016.
 */
public class AttachDao extends AbstractDao {
    public AttachDao() {}

    @Override
    public void insertItem(Model item) throws SQLException {
        Attach attach = (Attach) item;
        String insertSql = SqlXmlReader.getQuerryStr("sql.xml", "AttachDao", "insertSql");
        PreparedStatement ps = getPreparedStatement(insertSql);
        ResultSetMapper.putEntityToPreparedStatement(ps, attach);
        executeUpdate(ps);
        attach.setId(getInsertId("attach"));
    }

    @Override
    public void updateItem(Model item) throws SQLException {
        Attach attach = (Attach) item;
        String updateSql = SqlXmlReader.getQuerryStr("sql.xml", "AttachDao", "updateSql");
        PreparedStatement ps = getPreparedStatement(updateSql);
        ResultSetMapper.putEntityToPreparedStatement(ps, attach);
        executeUpdate(ps);
    }

    @Override
    public void delete(Long id) {
        String deleteSql = SqlXmlReader.getQuerryStr("sql.xml", "AttachDao", "deleteSql");
        performDelete(id, deleteSql);
    }

    @Override
    public List<Attach> getAll(int offcet, int limit) {
        return null;
    }

    @Override
    public List<Attach> getListById(Long itemId) {
        String getListById = SqlXmlReader.getQuerryStr("sql.xml", "AttachDao", "getListById");
        PreparedStatement ps = getNavigablePreparedStatement(getListById);
        ResultSet rs = executeGetById(ps, itemId);

        ResultSetMapper<Attach> resultSetMapper = new ResultSetMapper<>();
        List<Attach> attaches = resultSetMapper.mapRersultSetToList(rs, Attach.class);

        closeResultSet(ps, rs);
        return attaches;
    }

    @Override
    public Attach getById(Long itemId) {
        String getByIdSql = SqlXmlReader.getQuerryStr("sql.xml", "AttachDao", "getByIdSql");
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
