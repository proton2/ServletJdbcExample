package com.java.servlets.dao;

import com.java.servlets.util.DbUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by proton2 on 29.10.2016.
 */
public class AuthorizationDao {
    private String checkUserSql = "select login, password from usertable where login = ?";
    private Connection connection;

    public AuthorizationDao() {
        connection = DbUtil.getConnection();
    }

    public boolean checkAccess(String login, String password) {
        try {
            PreparedStatement ps = connection.prepareStatement(checkUserSql);
            ps.setString(1, login);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                if(login.equals(rs.getString("login")) && password.equals(rs.getString("password"))){
                    return true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
}
