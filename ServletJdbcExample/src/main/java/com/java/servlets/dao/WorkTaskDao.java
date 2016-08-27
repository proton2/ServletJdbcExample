package com.java.servlets.dao;

import com.java.servlets.model.User;
import com.java.servlets.model.WorkTask;
import com.java.servlets.util.DbUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by proton2 on 06.08.2016.
 */
public class WorkTaskDao implements ModelDao<WorkTask>{
    private String insertSql = "insert into WorkTask(taskuser_id, caption, taskContext, taskDate, deadLine) values (?, ?, ?, ?, ?)";
    private String deleteSql = "delete from WorkTask where id = ?";
    private String updateSql = "update WorkTask set taskuser_id=?, caption=?, taskContext=?, taskDate=?, deadLine=? where id=?";
    private String getAllSql = "select * from WorkTask";
    private String getByIdSql = "select * from WorkTask where id = ?";

    private Connection connection;

    public WorkTaskDao() {
        connection = DbUtil.getConnection();
    }

    @Override
    public void insert(WorkTask item) {
        try{
            PreparedStatement ps = connection.prepareStatement(insertSql);
            ps.setLong(1, item.getTaskUser().getId());
            ps.setString(2, item.getCaption());
            ps.setString(3, item.getTaskContext());
            ps.setDate(4, new java.sql.Date(item.getTaskDate().getTime()));
            ps.setDate(5, new java.sql.Date(item.getDeadLine().getTime()));
            ps.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(WorkTask item) {
        try{
            PreparedStatement ps = connection.prepareStatement(updateSql);
            ps.setLong(1, item.getTaskUser().getId());
            ps.setString(2, item.getCaption());
            ps.setString(3, item.getTaskContext());
            ps.setDate(4, new java.sql.Date(item.getTaskDate().getTime()));
            ps.setDate(5, new java.sql.Date(item.getDeadLine().getTime()));
            ps.setLong(6, item.getId());
            ps.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Long id) {
        try{
            PreparedStatement ps = connection.prepareStatement(deleteSql);
            ps.setLong(1, id);
            ps.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<WorkTask> getAll() {
        List<WorkTask> workTasks = new ArrayList<>();
        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(getAllSql);
            while(rs.next()){
                WorkTask workTask = new WorkTask();
                workTask.setId(rs.getLong("id"));

                //User user = new User(rs.getLong("taskuser_id"));
                //User user = DaoFactory.getUserDao().getById(rs.getLong("taskuser_id"));
                User user = (User) DaoFactory.getById(rs.getLong("taskuser_id"), User.class);
                
                workTask.setTaskUser(user);

                workTask.setCaption(rs.getString("caption"));
                workTask.setTaskContext(rs.getString("taskcontext"));
                workTask.setTaskDate(rs.getDate("taskdate"));
                workTask.setDeadLine(rs.getDate("deadline"));
                workTasks.add(workTask);
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }

        return workTasks;
    }

    @Override
    public WorkTask getById(Long itemId) {
        WorkTask wt = new WorkTask();
        try{
            PreparedStatement ps = connection.prepareStatement(getByIdSql);
            ps.setLong(1, itemId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()){
                wt.setId(rs.getLong("id"));
                //User user = new User(rs.getLong("taskuser_id"));
                User user = (User) DaoFactory.getById(rs.getLong("taskuser_id"), User.class);
                wt.setTaskUser(user);
                wt.setCaption(rs.getString("caption"));
                wt.setTaskContext(rs.getString("taskcontext"));
                wt.setTaskDate(rs.getDate("taskdate"));
                wt.setDeadLine(rs.getDate("deadline"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return wt;
    }
}
