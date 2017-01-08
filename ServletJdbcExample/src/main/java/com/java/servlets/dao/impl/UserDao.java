package com.java.servlets.dao.impl;

import com.java.servlets.dao.AbstractDao;
import com.java.servlets.dao.Service.ResultSetMapper;
import com.java.servlets.dao.Service.SqlXmlReader;
import com.java.servlets.model.Model;
import com.java.servlets.model.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class UserDao extends AbstractDao {

    public UserDao() {}

    @Override
    public void insertItem(Model item) throws SQLException {
        User user = (User) item;
        String insertSql = SqlXmlReader.getQuerryStr("sql.xml", "UserDao", "insertSql");
        PreparedStatement ps = getPreparedStatement(insertSql);
        ResultSetMapper.putEntityToPreparedStatement(ps, user);
        executeUpdate(ps);

        user.setId(getInsertId("usertable"));
        cachePut(user);
    }

    @Override
    public void updateItem(Model item) throws SQLException {
        cacheRemove(item);
        User user = (User) item;
        String updateSql = SqlXmlReader.getQuerryStr("sql.xml", "UserDao", "updateSql");
        PreparedStatement ps = getPreparedStatement(updateSql);
        ResultSetMapper.putEntityToPreparedStatement(ps, user);
        executeUpdate(ps);
        cachePut(user);
    }

    @Override
    public void delete(Long id) {
        String deleteSql = SqlXmlReader.getQuerryStr("sql.xml", "UserDao", "deleteSql");
        performDelete(id, deleteSql);
    }

    @Override
    public User getById(Long userId) {
        User user = (User) cacheGet(userId);
        if (user != null) return user;

        String getUserSql = SqlXmlReader.getQuerryStr("sql.xml", "UserDao", "getUserSql");
        PreparedStatement ps = getNavigablePreparedStatement(getUserSql);
        ResultSet rs = executeGetById(ps, userId);

        ResultSetMapper<User> resultSetMapper = new ResultSetMapper<>();
        user = resultSetMapper.mapRersultSetToObject(rs, User.class);

        closeResultSet(ps, rs);
        cachePut(user);
        return user;
    }

    @Override
    public int getNumOfRecords() {
        return -1;
    }

    @Override
    public List<User> getListById(Long itemId) {
        return null;
    }

    @Override
    public List<User> getAll(int offcet, int limit) {
        return null;
    }
}