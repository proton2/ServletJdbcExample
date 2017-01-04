package com.java.servlets.dao;

import com.java.servlets.model.UserRole;
import com.java.servlets.util.DataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by proton2 on 29.10.2016.
 */
public class AuthorizationDao {

    public AuthorizationDao() {
    }

    public UserRole checkAccess(String login, String password) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection connection = DataSource.getInstance().getConnection();
        String checkUserSql = "select login, password, role_id from usertable where login = ?";
        try {
            ps = connection.prepareStatement(checkUserSql);
            ps.setString(1, login);
            rs = ps.executeQuery();
            if (rs.next()) {
                String role_id = rs.getString("role_id");
                if(login.equals(rs.getString("login")) && password.equals(rs.getString("password"))){
                    return role_id.equalsIgnoreCase("admin") ? UserRole.admin : (role_id.equalsIgnoreCase("boss") ? UserRole.boss : UserRole.user);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (rs != null) try {rs.close();} catch (SQLException e) {e.printStackTrace();}
            if (ps != null) try {ps.close();} catch (SQLException e) {e.printStackTrace();}
            try {connection.close();} catch (SQLException e) {e.printStackTrace();}
        }

        return null;
    }

    public Long getUserId(String login) {
        Long id = -1l;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection connection = DataSource.getInstance().getConnection();
        String getUserIdSql = "select id from usertable where login = ?";
        try {
            ps = connection.prepareStatement(getUserIdSql);
            ps.setString(1, login);
            rs = ps.executeQuery();
            if (rs.next()) {
                id = rs.getLong("id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (rs != null) try {rs.close();} catch (SQLException e) {e.printStackTrace();}
            if (ps != null) try {ps.close();} catch (SQLException e) {e.printStackTrace();}
            try {connection.close();} catch (SQLException e) {e.printStackTrace();}
        }
        return id;
    }
}