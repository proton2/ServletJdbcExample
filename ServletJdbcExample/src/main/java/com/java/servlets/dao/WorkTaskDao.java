package com.java.servlets.dao;

import com.java.servlets.model.Model;
import com.java.servlets.model.User;
import com.java.servlets.model.WorkTask;
import com.java.servlets.util.DbUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by proton2 on 06.08.2016.
 */
public class WorkTaskDao implements ModelDao<WorkTask> {
    private String insertSql = "insert into WorkTask(taskuser_id, caption, taskContext, taskDate, deadLine) values (?, ?, ?, ?, ?)";
    private String deleteSql = "delete from WorkTask where id = ?";
    private String updateSql = "update WorkTask set taskuser_id=?, caption=?, taskContext=?, taskDate=?, deadLine=? where id=?";
    private String getAllSql = "select * from WorkTask";
    private String getByIdSql = "select * from WorkTask where id = ?";

    private String getUserWorkTasks = "select * from WorkTask where taskuser_id = ?";
    private String getUserWorkTasksId = "select id from WorkTask where taskuser_id = ?";

    private Connection connection;

    public WorkTaskDao() {
        connection = DbUtil.getConnection();
    }

    @Override
    public <V extends Model> Long insert(V item) {
        try {
            WorkTask wt = (WorkTask)item;
            PreparedStatement ps = connection.prepareStatement(insertSql);
            ps.setLong(1, wt.getTaskUser().getId());
            ps.setString(2, wt.getCaption());
            ps.setString(3, wt.getTaskContext());
            ps.setDate(4, new java.sql.Date(wt.getTaskDate().getTime()));
            ps.setDate(5, new java.sql.Date(wt.getDeadLine().getTime()));
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        Long insertCount = -1L;
        try {
            Statement select = connection.createStatement();
            ResultSet result = select.executeQuery("SELECT max(id) FROM WorkTask");
            while (result.next()) {
                insertCount = result.getLong(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return insertCount;
    }

    @Override
    public <V extends Model> void update(V item) {
        try {
            WorkTask wt = (WorkTask)item;
            PreparedStatement ps = connection.prepareStatement(updateSql);
            ps.setLong(1, wt.getTaskUser().getId());
            ps.setString(2, wt.getCaption());
            ps.setString(3, wt.getTaskContext());
            ps.setDate(4, new java.sql.Date(wt.getTaskDate().getTime()));
            ps.setDate(5, new java.sql.Date(wt.getDeadLine().getTime()));
            ps.setLong(6, item.getId());
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
    public List<WorkTask> getAll(boolean eager, String... fields) {
        List<WorkTask> workTasks = new ArrayList<>();
        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(getAllSql);

            Boolean eagerUser = Arrays.stream(fields).filter(e -> e.equals("user")).findAny().isPresent();

            while (rs.next()) {
                WorkTask workTask = new WorkTask();
                workTask.setId(rs.getLong("id"));
                if (eager) {
                    workTask.setCaption(rs.getString("caption"));
                    workTask.setTaskContext(rs.getString("taskcontext"));
                    workTask.setTaskDate(rs.getDate("taskdate"));
                    workTask.setDeadLine(rs.getDate("deadline"));
                    if (eagerUser) {
                        workTask.setTaskUser((User) DaoFactory.getById(rs.getLong("taskuser_id"), true, User.class));
                    } else {
                        workTask.setTaskUser(new User(rs.getLong("taskuser_id")));
                    }
                }
                workTasks.add(workTask);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return workTasks;
    }

    @Override
    public WorkTask getById(Long itemId, boolean eager, String... joinFields) {
        WorkTask wt = new WorkTask();
        try {
            PreparedStatement ps = connection.prepareStatement(getByIdSql);
            ps.setLong(1, itemId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                wt.setId(rs.getLong("id"));
                if (eager) {
                    Boolean eagerUser = Arrays.stream(joinFields).filter(e -> e.equals("user")).findAny().isPresent();
                    if (eagerUser) {
                        wt.setTaskUser((User) DaoFactory.getById(rs.getLong("taskuser_id"), true, User.class));
                    } else {
                        wt.setTaskUser(new User(rs.getLong("taskuser_id")));
                    }
                    wt.setCaption(rs.getString("caption"));
                    wt.setTaskContext(rs.getString("taskcontext"));
                    wt.setTaskDate(rs.getDate("taskdate"));
                    wt.setDeadLine(rs.getDate("deadline"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return wt;
    }

    @Override
    public List<WorkTask> getListById(Long itemId, boolean eager, String... fields) {
        List<WorkTask> workTasks = new ArrayList<>();
        try {
            PreparedStatement ps = connection.prepareStatement(eager ? getUserWorkTasks : getUserWorkTasksId);
            ps.setLong(1, itemId);
            ResultSet rs = ps.executeQuery();
            Boolean eagerUser = Arrays.stream(fields).filter(e -> e.equals("user")).findAny().isPresent();
            while (rs.next()) {
                WorkTask workTask = new WorkTask();
                workTask.setId(rs.getLong("id"));
                if (eager) {
                    workTask.setCaption(rs.getString("caption"));
                    workTask.setTaskContext(rs.getString("taskcontext"));
                    workTask.setTaskDate(rs.getDate("taskdate"));
                    workTask.setDeadLine(rs.getDate("deadline"));
                    if(eagerUser) {
                        workTask.setTaskUser((User) DaoFactory.getById(itemId, true, User.class));
                    }
                    else {
                        workTask.setTaskUser((User) DaoFactory.getById(itemId, false, User.class));
                    }
                }
                workTasks.add(workTask);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return workTasks;
    }
}