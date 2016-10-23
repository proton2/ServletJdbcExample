package com.java.servlets.dao;

import com.java.servlets.model.Model;
import com.java.servlets.model.UserView;
import com.java.servlets.util.DbUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by proton2 on 23.10.2016.
 */
public class UserViewDao implements ModelDao<UserView> {
    private String getAllSql = "select id, firstname, lastname, caption, email from usertable";
    private String getUserSql = "select firstname, lastname from usertable where id = ?";

    private Connection connection;

    public UserViewDao() {
        connection = DbUtil.getConnection();
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
    public List<UserView> getAll() {
        List<UserView> users = new ArrayList<>();
        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(getAllSql);
            while (rs.next()) {
                UserView user = new UserView();
                user.setId(rs.getLong("id"));
                user.setFirstName(rs.getString("firstname"));
                user.setLastName(rs.getString("lastname"));
                user.setCaption(rs.getString("caption"));
                user.setEmail(rs.getString("email"));
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return users;
    }

    @Override
    public List<UserView> getListById(Long itemId) {
        return null;
    }


    @Override
    public UserView getById(Long userId) {
        UserView user = new UserView();
        user.setId(userId);
        try {
            PreparedStatement ps = connection.prepareStatement(getUserSql);
            ps.setLong(1, userId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                user.setFirstName(rs.getString("firstname"));
                user.setLastName(rs.getString("lastname"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return user;
    }
}
