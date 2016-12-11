package com.java.servlets.dao;

import com.java.servlets.model.Attach;
import com.java.servlets.model.Model;
import com.java.servlets.model.WorkTask;
import com.java.servlets.util.DbUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by proton2 on 05.11.2016.
 */
public class AttachDao implements ModelDao<Attach>{
    private String insertSql = "insert into attach(fileName, caption, worktask_id) values (?, ?, ?)";
    private String deleteSql = "delete from attach where id = ?";
    private String updateSql = "update attach set fileName=?, caption=?, worktask_id=? where id=?";
    private String getByIdSql = "select * from attach where id = ?";
    private String getListById = "select id, filename, caption from attach where worktask_id = ?";

    private Connection connection;

    AttachDao() {
        connection = DbUtil.getConnection();
    }

    @Override
    public Long insert(Model item) {
        Attach attach = (Attach) item;
        try {
            PreparedStatement ps = connection.prepareStatement(insertSql);
            ps.setString(1, attach.getFileName());
            ps.setString(2, attach.getCaption());
            ps.setLong(3, attach.getWorkTask().getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        Long insertId = -1L;
        try {
            Statement select = connection.createStatement();
            ResultSet result = select.executeQuery("SELECT max(id) FROM attach");
            while (result.next()) {
                insertId = result.getLong(1);
                attach.setId(insertId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return insertId;
    }

    @Override
    public void update(Model item) {
        try {
            Attach attach = (Attach) item;
            PreparedStatement ps = connection.prepareStatement(updateSql);
            ps.setString(1, attach.getFileName());
            ps.setString(2, attach.getCaption());
            ps.setLong(3, attach.getWorkTask().getId());
            ps.setLong(4, attach.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Long id) {
        try {
            PreparedStatement ps = connection.prepareStatement(deleteSql);
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Attach> getAll(int offcet, int limit) {
        return null;
    }

    @Override
    public List<Attach> getListById(Long itemId) {
        List<Attach> attaches = new ArrayList<>();
        try {
            PreparedStatement ps = connection.prepareStatement(getListById);
            ps.setLong(1, itemId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Attach attach = new Attach();
                attach.setId(rs.getLong("id"));
                attach.setFileName(rs.getString("filename"));
                attach.setCaption(rs.getString("caption"));
                attaches.add(attach);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return attaches;
    }

    @Override
    public Attach getById(Long itemId) {
        Attach attach = new Attach();
        attach.setId(itemId);
        try {
            PreparedStatement ps = connection.prepareStatement(getByIdSql);
            ps.setLong(1, itemId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                attach.setFileName(rs.getString("filename"));
                attach.setCaption(rs.getString("caption"));
                WorkTask wt = new WorkTask();
                wt.setId(rs.getLong("worktask_id"));
                attach.setWorkTask(wt);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return attach;
    }

    @Override
    public int getNumOfRecoeds() {
        return 0;
    }
}
