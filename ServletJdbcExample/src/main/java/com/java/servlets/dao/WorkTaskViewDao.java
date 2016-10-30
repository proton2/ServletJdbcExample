package com.java.servlets.dao;

import com.java.servlets.model.Model;
import com.java.servlets.model.WorkTaskView;
import com.java.servlets.util.DbUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by proton2 on 23.10.2016.
 */
public class WorkTaskViewDao implements ModelDao<WorkTaskView> {
    private String getAllSql = "select w.id, w.caption, w.taskDate, w.deadLine, w.taskstatus_id, u.firstName, u.lastName\n" +
            "from WorkTask w\n" +
            "join usertable u on w.taskuser_id = u.id\n";
    private String getUserWorkTasks = "select id, caption, taskDate, deadLine, taskstatus_id\n" +
            "from WorkTask where taskuser_id = ?";

    private Connection connection;

    public WorkTaskViewDao() {
        connection = DbUtil.getConnection();
    }

    @Override
    public List<WorkTaskView> getAll() {
        List<WorkTaskView> workTasks = new ArrayList<>();
        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(getAllSql);

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
}
