package com.java.servlets.dao;

import com.java.servlets.model.Model;
import com.java.servlets.model.UserView;
import com.java.servlets.util.DataSource;
import com.java.servlets.util.SqlXmlReader;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by proton2 on 23.10.2016.
 */
public class UserViewDao implements ModelDao<UserView> {

    private String getAllSql;

    public UserViewDao() {
        SqlXmlReader sl = new SqlXmlReader();
        getAllSql = sl.getQuerry("sql.xml", "UserViewDao", "getAllSql");
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
        Connection connection = DataSource.getInstance().getConnection();
        Statement st = null;
        ResultSet rs = null;
        List<UserView> users = new ArrayList<>();
        try {
            st = connection.createStatement();
            rs = st.executeQuery(getAllSql);
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
        } finally {
            if (rs != null) try {rs.close();} catch (SQLException e) {e.printStackTrace();}
            if (st != null) try {st.close();} catch (SQLException e) {e.printStackTrace();}
            try {connection.close();} catch (SQLException e) {e.printStackTrace();}
        }
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
    public int getNumOfRecoeds() {
        return 0;
    }
}