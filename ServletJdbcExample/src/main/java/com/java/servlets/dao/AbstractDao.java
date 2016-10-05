package com.java.servlets.dao;

import com.java.servlets.util.DbUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by proton2 on 05.10.2016.
 */
public abstract class AbstractDao {

    public List<Long> getListId(Long itemId, String querry){
        List<Long> listId = new ArrayList<>();
        try {
            PreparedStatement ps = DbUtil.getConnection().prepareStatement(querry);
            if (itemId!=null) {
                ps.setLong(1, itemId);
            }
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                listId.add(rs.getLong("id"));

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listId;
    }
}
