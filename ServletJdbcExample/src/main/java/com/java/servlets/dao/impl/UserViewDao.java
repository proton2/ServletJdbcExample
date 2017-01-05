package com.java.servlets.dao.impl;

import com.java.servlets.dao.AbstractDao;
import com.java.servlets.dao.Service.ResultSetMapper;
import com.java.servlets.model.Model;
import com.java.servlets.model.UserView;
import com.java.servlets.dao.Service.SqlXmlReader;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by proton2 on 23.10.2016.
 */
public class UserViewDao extends AbstractDao {

    public UserViewDao() {}

    @Override
    public void insertItem(Model item) throws SQLException {
    }

    @Override
    public void updateItem(Model item) throws SQLException {
    }

    @Override
    public Long insert(Model item) {
        return null;
    }

    @Override
    public void update(Model item) {
    }

    @Override
    public void delete(Long id) {
    }

    @Override
    public List<UserView> getAll(int offcet, int limit) {
        String getAllSql = SqlXmlReader.getQuerryStr("sql.xml", "UserViewDao", "getAllSql");
        PreparedStatement ps = getNavigablePreparedStatement(getAllSql);
        ResultSet rs = executeGetAll(ps);

        ResultSetMapper<UserView> resultSetMapper = new ResultSetMapper<>();
        List<UserView> users = resultSetMapper.mapRersultSetToList(rs, UserView.class);

        closeResultSet(ps, rs);
        return users;
    }

    @Override
    public List<UserView> getListById(Long itemId) {
        return null;
    }

    @Override
    public UserView getById(Long userId) {
        return null;
    }

    @Override
    public int getNumOfRecords() {
        return 0;
    }
}