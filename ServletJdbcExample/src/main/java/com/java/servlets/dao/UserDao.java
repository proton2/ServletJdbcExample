package com.java.servlets.dao;

import com.java.servlets.model.Model;
import com.java.servlets.model.User;
import com.java.servlets.model.UserRole;
import com.java.servlets.util.DataSource;
import com.java.servlets.util.EHCacheManger;
import com.java.servlets.util.SqlXmlReader;
import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

class UserDao implements ModelDao<User> {
    String insertSql, updateSql, deleteSql, getUserSql;

    UserDao() {
        SqlXmlReader sl = new SqlXmlReader();
        insertSql = sl.getQuerry("sql.xml", "UserDao", "insertSql");
        updateSql = sl.getQuerry("sql.xml", "UserDao", "updateSql");
        deleteSql = sl.getQuerry("sql.xml", "UserDao", "deleteSql");
        getUserSql = sl.getQuerry("sql.xml", "UserDao", "getUserSql");
    }

    @Override
    public Long insert(Model item) {
        User user = (User) item;
        Connection connection = DataSource.getInstance().getConnection();
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement(insertSql);
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
        } finally {
            if (ps != null) try {ps.close();} catch (SQLException e) {e.printStackTrace();}
        }

        Long insertId = -1L;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT max(id) FROM usertable");
            while (resultSet.next()) {
                insertId = resultSet.getLong(1);
                user.setId(insertId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (resultSet != null) try {resultSet.close();} catch (SQLException e) {e.printStackTrace();}
            if (statement != null) try {statement.close();} catch (SQLException e) {e.printStackTrace();}
            try {connection.close();} catch (SQLException e) {e.printStackTrace();}
        }

        Cache cache = EHCacheManger.getCache();
        cache.put(new Element(insertId, user));

        return insertId;
    }

    @Override
    public void update(Model item) {
        Cache cache = EHCacheManger.getCache();
        cache.remove(item.getId());

        User user = (User) item;
        Connection connection = DataSource.getInstance().getConnection();
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement(updateSql);
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
        } finally {
            if (ps != null) try {ps.close();} catch (SQLException e) {e.printStackTrace();}
            if (connection != null) try {connection.close();} catch (SQLException e) {e.printStackTrace();}
        }
        cache.put(new Element(item.getId(), user));
    }

    @Override
    public void delete(Long userId) {
        Cache cache = EHCacheManger.getCache();
        cache.remove(userId);

        Connection connection = DataSource.getInstance().getConnection();
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement(deleteSql);
            ps.setLong(1, userId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (ps != null) try {
                ps.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            if (connection != null) try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
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

        Connection connection = DataSource.getInstance().getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = connection.prepareStatement(getUserSql);
            ps.setLong(1, userId);
            rs = ps.executeQuery();
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
        } finally {
            if (rs != null) try {rs.close();} catch (SQLException e) {e.printStackTrace();}
            if (ps != null) try {ps.close();} catch (SQLException e) {e.printStackTrace();}
            if (connection != null) try {connection.close();} catch (SQLException e) {e.printStackTrace();}
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