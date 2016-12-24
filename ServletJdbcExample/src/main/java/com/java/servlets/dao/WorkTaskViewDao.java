package com.java.servlets.dao;

import com.java.servlets.model.Model;
import com.java.servlets.model.WorkTaskView;
import com.java.servlets.util.DataSource;
import com.java.servlets.util.SqlXmlReader;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by proton2 on 23.10.2016.
 */
public class WorkTaskViewDao implements ModelDao<WorkTaskView> {
    private int numOfRdcords;
    private String getAllSql, getUserWorkTasks;

    public WorkTaskViewDao() {
        SqlXmlReader sl = new SqlXmlReader();
        getAllSql = sl.getQuerry("sql.xml", "WorkTaskViewDao", "getall");
        getUserWorkTasks = sl.getQuerry("sql.xml", "WorkTaskViewDao", "getUserWorkTasks");
    }

    @Override
    public List<WorkTaskView> getAll(int offset, int limit) {
        Connection connection = DataSource.getInstance().getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<WorkTaskView> workTasks = new ArrayList<>();
        try {
            ps = connection.prepareStatement(getAllSql);
            ps.setInt(1, offset);
            ps.setInt(2, limit);
            rs = ps.executeQuery();
            while (rs.next()) {
                WorkTaskView workTask = new WorkTaskView();
                workTask.setId(rs.getLong("id"));
                workTask.setCaption(rs.getString("caption"));
                workTask.setTaskDate(rs.getDate("taskdate"));
                workTask.setDeadLine(rs.getDate("deadline"));
                int t_stat = rs.getInt("taskstatus_id");
                workTask.setTaskStatus(t_stat == 0 ? "NEW" : (t_stat == 1 ? "CLOSED" : "ACTUAL"));
                workTask.setTaskUser(rs.getString("firstName") + " " + rs.getString("lastName"));
                workTasks.add(workTask);
            }
            ps = connection.prepareStatement("select count(*) from worktask");
            rs = ps.executeQuery();
            if (rs.next()) {this.numOfRdcords = rs.getInt(1);}
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (rs != null) try {rs.close();} catch (SQLException e) {e.printStackTrace();}
            if (ps != null) try {ps.close();} catch (SQLException e) {e.printStackTrace();}
            try {connection.close();} catch (SQLException e) {e.printStackTrace();}
        }

        return workTasks;
    }

    @Override
    public List<WorkTaskView> getListById(Long itemId) {
        Connection connection = DataSource.getInstance().getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<WorkTaskView> workTasks = new ArrayList<>();
        try {
            ps = connection.prepareStatement(getUserWorkTasks);
            ps.setLong(1, itemId);
            rs = ps.executeQuery();
            while (rs.next()) {
                WorkTaskView workTask = new WorkTaskView();
                workTask.setId(rs.getLong("id"));
                workTask.setCaption(rs.getString("caption"));
                workTask.setTaskDate(rs.getDate("taskdate"));
                workTask.setDeadLine(rs.getDate("deadline"));
                int t_stat = rs.getInt("taskstatus_id");
                workTask.setTaskStatus(t_stat == 0 ? "NEW" : (t_stat == 1 ? "CLOSED" : "ACTUAL"));

                workTasks.add(workTask);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (rs != null) try {rs.close();} catch (SQLException e) {e.printStackTrace();}
            if (ps != null) try {ps.close();} catch (SQLException e) {e.printStackTrace();}
            try {connection.close();} catch (SQLException e) {e.printStackTrace();}
        }

        return workTasks;
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
    public WorkTaskView getById(Long itemId) {
        return null;
    }

    @Override
    public int getNumOfRecoeds() {
        return numOfRdcords;
    }
}