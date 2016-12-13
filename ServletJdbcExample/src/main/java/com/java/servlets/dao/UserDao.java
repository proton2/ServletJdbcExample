package com.java.servlets.dao;

import com.java.servlets.model.Model;
import com.java.servlets.model.User;
import com.java.servlets.model.UserRole;
import com.java.servlets.util.DbUtil;
import com.java.servlets.util.EHCacheManger;
import com.java.servlets.util.SqlXmlReader;
import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;

import java.sql.*;
import java.util.List;

class UserDao implements ModelDao<User> {
    /*
    private String insertSql = "insert into usertable(firstname, lastname, caption, email, login, password, role_id) values (?, ?, ?, ?, ?, ?, ?)";
    private String deleteSql = "delete from usertable where id = ?";
    private String updateSql = "update usertable set firstname=?, lastname=?, caption=?, email=?, login=?, password=?, role_id=? where id=?";
    private String getUserSql = "select * from usertable where id = ?";
    */

    private Connection connection;
    String insertSql, deleteSql, updateSql, getUserSql;

    UserDao() {
        connection = DbUtil.getConnection();
        SqlXmlReader sl = new SqlXmlReader();
        insertSql = sl.getQuerry("UserDao", "insertSql");
        deleteSql = sl.getQuerry("UserDao", "deleteSql");
        updateSql = sl.getQuerry("UserDao", "updateSql");
        getUserSql = sl.getQuerry("UserDao", "getUserSql");
    }

    @Override
    public Long insert(Model item) {
        User user = (User) item;
        try {
            PreparedStatement ps = connection.prepareStatement(insertSql);
            ps.setString(1, user.getFirstName());
            ps.setString(2, user.getLastName());
            ps.setString(3, user.getCaption());
            ps.setString(4, user.getEmail());
            ps.setString(5, user.getLogin());
            ps.setString(6, user.getPassword());
            ps.setInt(7, user.getRole().ordinal());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        Long insertId = -1L;
        try {
            Statement select = connection.createStatement();
            ResultSet result = select.executeQuery("SELECT max(id) FROM usertable");
            while (result.next()) {
                insertId = result.getLong(1);
                user.setId(insertId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Cache cache = EHCacheManger.getCache();
        cache.put(new Element(insertId, user));

        return insertId;
    }

    @Override
    public void update(Model item) {
        Cache cache = EHCacheManger.getCache();
        cache.remove(item.getId());
        try {
            User user = (User) item;
            PreparedStatement ps = connection.prepareStatement(updateSql);
            ps.setString(1, user.getFirstName());
            ps.setString(2, user.getLastName());
            ps.setString(3, user.getCaption());
            ps.setString(4, user.getEmail());
            ps.setString(5, user.getLogin());
            ps.setString(6, user.getPassword());
            ps.setInt(7, user.getRole().ordinal());
            ps.setLong(8, user.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        cache.put(new Element(item.getId(), item));
    }

    @Override
    public void delete(Long userId) {
        Cache cache = EHCacheManger.getCache();
        cache.remove(userId);

        try {
            PreparedStatement ps = connection.prepareStatement(deleteSql);
            ps.setLong(1, userId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public User getById(Long userId) {
        Cache cache = EHCacheManger.getCache();
        Element element = cache.get(userId);
        if (element != null) {
            return (User) element.getObjectValue();
        }

        User user = new User();
        user.setId(userId);
        try {
            PreparedStatement ps = connection.prepareStatement(getUserSql);
            ps.setLong(1, userId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                user.setFirstName(rs.getString("firstname"));
                user.setLastName(rs.getString("lastname"));
                user.setCaption(rs.getString("caption"));
                user.setEmail(rs.getString("email"));
                user.setLogin(rs.getString("login"));
                user.setPassword(rs.getString("password"));

                int role_id = rs.getInt("role_id");
                user.setRole(role_id == 0 ? UserRole.admin : (role_id == 1 ? UserRole.boss : UserRole.user));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        cache.put(new Element(userId, user));
        return user;
    }

    @Override
    public int getNumOfRecoeds() {
        return 0;
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