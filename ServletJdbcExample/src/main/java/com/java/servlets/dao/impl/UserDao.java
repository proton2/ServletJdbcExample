package com.java.servlets.dao.impl;

import com.java.servlets.dao.AbstractDao;
import com.java.servlets.dao.Service.ResultSetMapper;
import com.java.servlets.model.Model;
import com.java.servlets.model.User;
import com.java.servlets.util.SqlXmlReader;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class UserDao extends AbstractDao {
    private String insertSql, updateSql, deleteSql, getUserSql;

    public UserDao() {
        SqlXmlReader sl = new SqlXmlReader();
        insertSql = sl.getQuerry("sql.xml", "UserDao", "insertSql");
        updateSql = sl.getQuerry("sql.xml", "UserDao", "updateSql");
        deleteSql = sl.getQuerry("sql.xml", "UserDao", "deleteSql");
        getUserSql = sl.getQuerry("sql.xml", "UserDao", "getUserSql");
    }

    @Override
    public void insertItem(Model item) throws SQLException {
        User user = (User) item;
        PreparedStatement ps = getPreparedStatement(insertSql);
        ps.setString(1, user.getFirstName());
        ps.setString(2, user.getLastName());
        ps.setString(3, user.getCaption());
        ps.setString(4, user.getEmail());
        ps.setString(5, user.getLogin());
        ps.setString(6, user.getPassword());
        ps.setString(7, user.getRole().name());
        executeUpdate(ps);

        user.setId(getInsertId("usertable"));
        cachePut(user);
    }

    @Override
    public void updateItem(Model item) throws SQLException {
        cacheRemove(item);
        User user = (User) item;

        PreparedStatement ps = getPreparedStatement(updateSql);
        ps.setString(1, user.getFirstName());
        ps.setString(2, user.getLastName());
        ps.setString(3, user.getCaption());
        ps.setString(4, user.getEmail());
        ps.setString(5, user.getLogin());
        ps.setString(6, user.getPassword());
        ps.setString(7, user.getRole().name());
        ps.setLong(8, user.getId());

        executeUpdate(ps);
        cachePut(user);
    }

    @Override
    public void delete(Long id) {
        performDelete(id, deleteSql);
    }

    @Override
    public User getById(Long userId) {
        User user = (User) cacheGet(userId);
        if (user != null) return user;

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