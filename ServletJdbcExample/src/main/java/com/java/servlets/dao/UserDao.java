package com.java.servlets.dao;

import com.java.servlets.model.Model;
import com.java.servlets.model.User;
import com.java.servlets.model.UserRole;
import com.java.servlets.util.DbUtil;
import com.java.servlets.util.EHCacheManger;
import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;

import java.sql.*;
import java.util.List;

class UserDao implements ModelDao<User> {
    private String insertSql = "insert into usertable(firstname, lastname, caption, email, login, password, role) values (?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private String deleteSql = "delete from usertable where id = ?";
    private String updateSql = "update usertable set firstname=?, lastname=?, caption=?, email=?, login=?, password=?, role=? where id=?";
    private String getUserSql = "select * from usertable where id = ?";

    private Connection connection;

    UserDao() {
        connection = DbUtil.getConnection();
    }

    @Override
    public Long insert(Model item) {
        try {
            User user = (User) item;
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
            ResultSet result = select.executeQuery("SELECT max(id) FROM User");
            while (result.next()) {
                insertId = result.getLong(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Cache cache = EHCacheManger.getCache();
        cache.put(new Element(insertId, item));

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
    public List<User> getListById(Long itemId) {
        return null;
    }

    @Override
    public List<User> getAll() {
        return null;
    }
}