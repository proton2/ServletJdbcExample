package com.java.servlets.dao;

import com.java.servlets.model.Attach;
import com.java.servlets.model.Model;
import com.java.servlets.model.WorkTask;
import com.java.servlets.util.DataSource;
import com.java.servlets.util.SqlXmlReader;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by proton2 on 05.11.2016.
 */
public class AttachDao implements ModelDao<Attach> {
    private String insertSql, updateSql, deleteSql, getListById, getByIdSql;

    AttachDao() {
        SqlXmlReader sl = new SqlXmlReader();
        insertSql = sl.getQuerry("sql.xml", "AttachDao", "insertSql");
        updateSql = sl.getQuerry("sql.xml", "AttachDao", "updateSql");
        deleteSql = sl.getQuerry("sql.xml", "AttachDao", "deleteSql");
        getListById = sl.getQuerry("sql.xml", "AttachDao", "getListById");
        getByIdSql = sl.getQuerry("sql.xml", "AttachDao", "getByIdSql");
    }

    @Override
    public Long insert(Model item) {
        Attach attach = (Attach) item;
        Connection connection = DataSource.getInstance().getConnection();
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement(insertSql);
            ps.setString(1, attach.getFileName());
            ps.setString(2, attach.getCaption());
            ps.setLong(3, attach.getWorkTask().getId());
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
            resultSet = statement.executeQuery("SELECT max(id) FROM attach");
            while (resultSet.next()) {
                insertId = resultSet.getLong(1);
                attach.setId(insertId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (resultSet != null) try {resultSet.close();} catch (SQLException e) {e.printStackTrace();}
            if (statement != null) try {statement.close();} catch (SQLException e) {e.printStackTrace();}
            try {connection.close();} catch (SQLException e) {e.printStackTrace();}
        }

        return insertId;
    }

    @Override
    public void update(Model item) {
        Connection connection = DataSource.getInstance().getConnection();
        PreparedStatement ps = null;
        try {
            Attach attach = (Attach) item;
            ps = connection.prepareStatement(updateSql);
            ps.setString(1, attach.getFileName());
            ps.setString(2, attach.getCaption());
            ps.setLong(3, attach.getWorkTask().getId());
            ps.setLong(4, attach.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (ps != null) try {ps.close();} catch (SQLException e) {e.printStackTrace();}
            if (connection != null) try {connection.close();} catch (SQLException e) {e.printStackTrace();}
        }
    }

    @Override
    public void delete(Long id) {
        Connection connection = DataSource.getInstance().getConnection();
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement(deleteSql);
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (ps != null) try {ps.close();} catch (SQLException e) {e.printStackTrace();}
            if (connection != null) try {connection.close();} catch (SQLException e) {e.printStackTrace();}
        }
    }

    @Override
    public List<Attach> getAll(int offcet, int limit) {
        return null;
    }

    @Override
    public List<Attach> getListById(Long itemId) {
        List<Attach> attaches = new ArrayList<>();
        Connection connection = DataSource.getInstance().getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = connection.prepareStatement(getListById);
            ps.setLong(1, itemId);
            rs = ps.executeQuery();
            while (rs.next()) {
                Attach attach = new Attach();
                attach.setId(rs.getLong("id"));
                attach.setFileName(rs.getString("filename"));
                attach.setCaption(rs.getString("caption"));
                attaches.add(attach);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (rs != null) try {rs.close();} catch (SQLException e) {e.printStackTrace();}
            if (ps != null) try {ps.close();} catch (SQLException e) {e.printStackTrace();}
            if (connection != null) try {connection.close();} catch (SQLException e) {e.printStackTrace();}
        }

        return attaches;
    }

    @Override
    public Attach getById(Long itemId) {
        Attach attach = new Attach();
        attach.setId(itemId);
        Connection connection = DataSource.getInstance().getConnection();
        ResultSet rs = null;
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement(getByIdSql);
            ps.setLong(1, itemId);
            rs = ps.executeQuery();
            if (rs.next()) {
                attach.setFileName(rs.getString("filename"));
                attach.setCaption(rs.getString("caption"));
                WorkTask wt = new WorkTask();
                wt.setId(rs.getLong("worktask_id"));
                attach.setWorkTask(wt);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (rs != null) try {rs.close();} catch (SQLException e) {e.printStackTrace();}
            if (ps != null) try {ps.close();} catch (SQLException e) {e.printStackTrace();}
            if (connection != null) try {connection.close();} catch (SQLException e) {e.printStackTrace();}
        }

        return attach;
    }

    @Override
    public int getNumOfRecoeds() {
        return 0;
    }
}
