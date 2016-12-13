package com.java.servlets.dao;

import com.java.servlets.model.Model;
import com.java.servlets.model.WorkTaskView;
import com.java.servlets.util.DbUtil;
import com.java.servlets.util.SqlXmlReader;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by proton2 on 23.10.2016.
 */
public class WorkTaskViewDao implements ModelDao<WorkTaskView> {
    /*
    private String getAllSql = "select w.id, w.caption, w.taskDate, w.deadLine, w.taskstatus_id, u.firstName, u.lastName\n" +
            "from WorkTask w\n" +
            "join usertable u on w.taskuser_id = u.id\n" +
            "offset ? limit ?";

    private String getUserWorkTasks = "select id, caption, taskDate, deadLine, taskstatus_id\n" +
            "from WorkTask where taskuser_id = ?";
    */
    private Connection connection;
    private int numOfRdcords;
    String getAllSql, getUserWorkTasks;

    public WorkTaskViewDao() {
        connection = DbUtil.getConnection();

        SqlXmlReader sl = new SqlXmlReader();
        getAllSql = sl.getQuerry("WorkTaskViewDao", "getall");
        getUserWorkTasks = sl.getQuerry("WorkTaskViewDao", "getUserWorkTasks");
    }

    @Override
    public List<WorkTaskView> getAll(int offset, int limit) {
        List<WorkTaskView> workTasks = new ArrayList<>();
        try {
            PreparedStatement ps = connection.prepareStatement(getAllSql);
            ps.setInt(1, offset);
            ps.setInt(2, limit);
            ResultSet rs = ps.executeQuery();

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
        }

        return workTasks;
    }

    @Override
    public List<WorkTaskView> getListById(Long itemId) {
        List<WorkTaskView> workTasks = new ArrayList<>();
        try {
            PreparedStatement ps = connection.prepareStatement(getUserWorkTasks);
            ps.setLong(1, itemId);
            ResultSet rs = ps.executeQuery();
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
