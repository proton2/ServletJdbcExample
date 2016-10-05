package com.java.servlets.dao;

import com.java.servlets.model.Model;
import com.java.servlets.model.User;
import com.java.servlets.model.WorkTask;
import com.java.servlets.util.DbUtil;
import com.java.servlets.util.EHCacheManger;
import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by proton2 on 06.08.2016.
 */
public class WorkTaskDao extends AbstractDao implements ModelDao<WorkTask> {
    private String insertSql = "insert into WorkTask(taskuser_id, caption, taskContext, taskDate, deadLine) values (?, ?, ?, ?, ?)";
    private String deleteSql = "delete from WorkTask where id = ?";
    private String updateSql = "update WorkTask set taskuser_id=?, caption=?, taskContext=?, taskDate=?, deadLine=? where id=?";
    private String getAllSql = "select * from WorkTask";
    private String getIdSql = "select id from WorkTask";
    private String getByIdSql = "select * from WorkTask where id = ?";

    private String getUserWorkTasks = "select * from WorkTask where taskuser_id = ?";
    private String getUserWorkTasksId = "select id from WorkTask where taskuser_id = ?";

    private Connection connection;

    public WorkTaskDao() {
        connection = DbUtil.getConnection();
    }

    @Override
    public Long insert(Model item) {
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

        Long insertId = -1L;
        try {
            Statement select = connection.createStatement();
            ResultSet result = select.executeQuery("SELECT max(id) FROM WorkTask");
            while (result.next()) {
                insertId = result.getLong(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Cache cache = EHCacheManger.getCache();
        cache.put(new Element(insertId, item));

        return insertId;
    }

    @Override
    public void update(Model item) {
        Cache cache = EHCacheManger.getCache();
        cache.remove(item.getId());
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
        cache.put(new Element(item.getId(), item));
    }

    @Override
    public void delete(Long id) {
        Cache cache = EHCacheManger.getCache();
        cache.remove(id);

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
        Boolean eagerUser = Arrays.stream(fields).filter(e -> e.equals("user")).findAny().isPresent();
        List<Long> listId = getListId(null, getIdSql);

        List<WorkTask> workTasks = new ArrayList<>();
        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(eager ? getAllSql : getIdSql);

            while (rs.next()) {
                WorkTask workTask = new WorkTask();
                workTask.setId(rs.getLong("id"));
                if (eager) {
                    workTask.setCaption(rs.getString("caption"));
                    workTask.setTaskContext(rs.getString("taskcontext"));
                    workTask.setTaskDate(rs.getDate("taskdate"));
                    workTask.setDeadLine(rs.getDate("deadline"));
                    workTask.setTaskUser((User) DaoFactory.getById(rs.getLong("taskuser_id"), eagerUser, User.class));
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
        WorkTask wt = null;
        Boolean eagerUser = Arrays.stream(joinFields).filter(e -> e.equals("user")).findAny().isPresent();

        Cache cache = EHCacheManger.getCache();
        Element element = cache.get(itemId);
        if (element != null){
            wt = (WorkTask) element.getObjectValue();
            wt.setTaskUser((User) DaoFactory.getById(wt.getTaskUser().getId(), eagerUser, User.class));
            return wt;
        }

        wt = new WorkTask();
        wt.setId(itemId);
        if (eager) {
            try {
                PreparedStatement ps = connection.prepareStatement(getByIdSql);
                ps.setLong(1, itemId);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    wt.setId(rs.getLong("id"));
                    wt.setCaption(rs.getString("caption"));
                    wt.setTaskContext(rs.getString("taskcontext"));
                    wt.setTaskDate(rs.getDate("taskdate"));
                    wt.setDeadLine(rs.getDate("deadline"));
                    wt.setTaskUser((User) DaoFactory.getById(rs.getLong("taskuser_id"), eagerUser, User.class));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        cache.put(new Element(itemId, wt));
        return wt;
    }

    @Override
    public List<WorkTask> getListById(Long itemId, boolean eager, String... fields) {
        Boolean eagerUser = Arrays.stream(fields).filter(e -> e.equals("user")).findAny().isPresent();
        List<Long> listId = getListId(itemId, getUserWorkTasksId);

        List<WorkTask> workTasks = new ArrayList<>();
        try {
            PreparedStatement ps = connection.prepareStatement(eager ? getUserWorkTasks : getUserWorkTasksId);
            ps.setLong(1, itemId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                WorkTask workTask = new WorkTask();
                workTask.setId(rs.getLong("id"));
                if (eager) {
                    workTask.setCaption(rs.getString("caption"));
                    workTask.setTaskContext(rs.getString("taskcontext"));
                    workTask.setTaskDate(rs.getDate("taskdate"));
                    workTask.setDeadLine(rs.getDate("deadline"));
                    workTask.setTaskUser((User) DaoFactory.getById(itemId, eagerUser, User.class));
                }
                workTasks.add(workTask);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return workTasks;
    }
}