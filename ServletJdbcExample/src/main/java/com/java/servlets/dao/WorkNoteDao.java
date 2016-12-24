package com.java.servlets.dao;

import com.java.servlets.model.Model;
import com.java.servlets.model.User;
import com.java.servlets.model.WorkNote;
import com.java.servlets.model.WorkTask;
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
import java.util.ArrayList;
import java.util.List;

/**
 * Created by proton2 on 21.11.2016.
 */
public class WorkNoteDao implements ModelDao<WorkNote> {
    private String insertSql, updateSql, deleteSql, getByIdSql, getListById;

    WorkNoteDao() {
        SqlXmlReader sl = new SqlXmlReader();
        insertSql = sl.getQuerry("sql.xml", "WorkNoteDao", "insertSql");
        updateSql = sl.getQuerry("sql.xml", "WorkNoteDao", "updateSql");
        deleteSql = sl.getQuerry("sql.xml", "WorkNoteDao", "deleteSql");
        getByIdSql = sl.getQuerry("sql.xml", "WorkNoteDao", "getByIdSql");
        getListById = sl.getQuerry("sql.xml", "WorkNoteDao", "getListById");
    }

    @Override
    public Long insert(Model item) {
        WorkNote workNote = (WorkNote) item;
        Connection connection = DataSource.getInstance().getConnection();
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement(insertSql);
            ps.setString(1, workNote.getCaption());
            ps.setDate(2, new java.sql.Date(workNote.getNoteDate().getTime()));
            ps.setString(3, workNote.getDescription());
            ps.setLong(4, workNote.getSubject().getId());
            ps.setLong(5, workNote.getNoteUser().getId());
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
            resultSet = statement.executeQuery("SELECT max(id) FROM WorkNote");
            while (resultSet.next()) {
                insertId = resultSet.getLong(1);
                workNote.setId(insertId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (resultSet != null) try {resultSet.close();} catch (SQLException e) {e.printStackTrace();}
            if (statement != null) try {statement.close();} catch (SQLException e) {e.printStackTrace();}
            try {connection.close();} catch (SQLException e) {e.printStackTrace();}
        }
        Cache cache = EHCacheManger.getCache();
        cache.put(new Element(insertId, workNote));

        return insertId;
    }

    @Override
    public void update(Model item) {
        Cache cache = EHCacheManger.getCache();
        cache.remove(item.getId());

        WorkNote workNote = (WorkNote) item;
        Connection connection = DataSource.getInstance().getConnection();
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement(updateSql);
            ps.setString(1, workNote.getCaption());
            ps.setDate(2, new java.sql.Date(workNote.getNoteDate().getTime()));
            ps.setString(3, workNote.getDescription());
            ps.setLong(4, workNote.getSubject().getId());
            ps.setLong(5, workNote.getNoteUser().getId());
            ps.setLong(6, item.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            if (ps != null) try {ps.close();} catch (SQLException e) {e.printStackTrace();}
            if (connection != null) try {connection.close();} catch (SQLException e) {e.printStackTrace();}
        }
        cache.put(new Element(item.getId(), workNote));
    }

    @Override
    public void delete(Long id) {
        Cache cache = EHCacheManger.getCache();
        cache.remove(id);

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
    public List<WorkNote> getAll(int offcet, int limit) {
        return null;
    }

    @Override
    public WorkNote getById(Long itemId) {
        WorkNote wn = null;
        Cache cache = EHCacheManger.getCache();
        Element element = cache.get(itemId);
        if (element != null) {
            wn = (WorkNote) element.getObjectValue();
            return wn;
        }

        wn = new WorkNote();
        wn.setId(itemId);
        Connection connection = DataSource.getInstance().getConnection();
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement(getByIdSql);
            ps.setLong(1, itemId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                wn.setCaption(rs.getString("caption"));
                wn.setNoteDate(rs.getDate("noteDate"));
                wn.setDescription(rs.getString("description"));

                WorkTask wt = new WorkTask();
                wt.setId(rs.getLong("model_id"));
                wt.setCaption(rs.getString("caption"));
                wn.setSubject(wt);

                User user = new User();
                user.setId(rs.getLong("user_id"));
                user.setFirstName(rs.getString("firstName"));
                user.setLastName(rs.getString("lastName"));
                wn.setNoteUser(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (ps != null) try {ps.close();} catch (SQLException e) {e.printStackTrace();}
            if (connection != null) try {connection.close();} catch (SQLException e) {e.printStackTrace();}
        }

        cache.put(new Element(itemId, wn));
        return wn;
    }

    @Override
    public int getNumOfRecoeds() {
        return 0;
    }

    @Override
    public List<WorkNote> getListById(Long itemId) {
        List<WorkNote> workNotes = new ArrayList<>();
        Connection connection = DataSource.getInstance().getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = connection.prepareStatement(getListById);
            ps.setLong(1, itemId);
            rs = ps.executeQuery();
            while (rs.next()) {
                WorkNote wn = new WorkNote();
                wn.setId(rs.getLong("id"));
                wn.setCaption(rs.getString("caption"));
                wn.setNoteDate(rs.getDate("noteDate"));
                User user = new User();
                user.setId(rs.getLong("user_id"));
                user.setFirstName(rs.getString("firstName"));
                user.setLastName(rs.getString("lastName"));
                wn.setNoteUser(user);
                workNotes.add(wn);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (rs != null) try {rs.close();} catch (SQLException e) {e.printStackTrace();}
            if (ps != null) try {ps.close();} catch (SQLException e) {e.printStackTrace();}
            try {connection.close();} catch (SQLException e) {e.printStackTrace();}
        }

        return workNotes;
    }
}
