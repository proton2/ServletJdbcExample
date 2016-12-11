package com.java.servlets.dao;

import com.java.servlets.model.Model;
import com.java.servlets.model.TaskStatus;
import com.java.servlets.model.User;
import com.java.servlets.model.WorkTask;
import com.java.servlets.util.DbUtil;
import com.java.servlets.util.EHCacheManger;
import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;
import java.util.List;

/**
 * Created by proton2 on 06.08.2016.
 */
public class WorkTaskDao implements ModelDao<WorkTask> {
    private String insertSql = "insert into WorkTask(taskuser_id, caption, taskContext, taskDate, deadLine, taskstatus_id) values (?, ?, ?, ?, ?, ?)";
    private String deleteSql = "delete from WorkTask where id = ?";
    private String updateSql = "update WorkTask set taskuser_id=?, caption=?, taskContext=?, taskDate=?, deadLine=?, taskstatus_id=?  where id=?";
    private String getById = "select w.id, w.caption, w.taskContext, w.taskDate, w.deadLine, w.taskstatus_id, w.taskuser_id, u.firstName, u.lastName\n" +
            "from WorkTask w join usertable u on w.taskuser_id = u.id\n" +
            "where w.id = ?";

    private Connection connection;

    public WorkTaskDao() {
        connection = DbUtil.getConnection();
    }

    @Override
    public Long insert(Model item) {
        try {
            WorkTask wt = (WorkTask) item;
            PreparedStatement ps = connection.prepareStatement(insertSql);
            ps.setLong(1, wt.getTaskUser().getId());
            ps.setString(2, wt.getCaption());
            ps.setString(3, wt.getTaskContext());
            ps.setDate(4, new java.sql.Date(wt.getTaskDate().getTime()));
            ps.setDate(5, new java.sql.Date(wt.getDeadLine().getTime()));
            ps.setInt(6, wt.getTaskStatus().ordinal());
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

    public int importCollection(Collection<WorkTask> workTaskList){
        int[] updateCounts = null;
        if (workTaskList!=null && !workTaskList.isEmpty()) {

            try {
                connection.setAutoCommit(false);

                PreparedStatement ps = connection.prepareStatement(insertSql);
                for (WorkTask workTask : workTaskList) {
                    ps.setLong(1, workTask.getTaskUser().getId());
                    ps.setString(2, workTask.getCaption());
                    ps.setString(3, workTask.getTaskContext());
                    ps.setDate(4, new java.sql.Date(workTask.getTaskDate().getTime()));
                    ps.setDate(5, new java.sql.Date(workTask.getDeadLine().getTime()));
                    ps.setInt(6, workTask.getTaskStatus().ordinal());

                    ps.addBatch();
                }
                updateCounts = ps.executeBatch();

                connection.commit();
            }  catch (SQLException e) {
                try {
                    connection.rollback();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
                e.printStackTrace();
            }
        }
        return updateCounts == null ? null : updateCounts.length;
    }

    @Override
    public void update(Model item) {
        Cache cache = EHCacheManger.getCache();
        cache.remove(item.getId());
        try {
            WorkTask wt = (WorkTask) item;
            PreparedStatement ps = connection.prepareStatement(updateSql);
            ps.setLong(1, wt.getTaskUser().getId());
            ps.setString(2, wt.getCaption());
            ps.setString(3, wt.getTaskContext());
            ps.setDate(4, new java.sql.Date(wt.getTaskDate().getTime()));
            ps.setDate(5, new java.sql.Date(wt.getDeadLine().getTime()));
            ps.setInt(6, wt.getTaskStatus().ordinal());
            ps.setLong(7, item.getId());
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
    public WorkTask getById(Long itemId) {
        WorkTask wt = null;
        Cache cache = EHCacheManger.getCache();
        Element element = cache.get(itemId);
        if (element != null) {
            wt = (WorkTask) element.getObjectValue();
            return wt;
        }

        wt = new WorkTask();
        wt.setId(itemId);
        try {
            PreparedStatement ps = connection.prepareStatement(getById);
            ps.setLong(1, itemId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                wt.setCaption(rs.getString("caption"));
                wt.setTaskContext(rs.getString("taskcontext"));
                wt.setTaskDate(rs.getDate("taskdate"));
                wt.setDeadLine(rs.getDate("deadline"));

                int t_stat = rs.getInt("taskstatus_id");
                wt.setTaskStatus(t_stat == 0 ? TaskStatus.NEW : (t_stat == 1 ? TaskStatus.CLOSED : TaskStatus.ACTUAL));

                User user = new User();
                user.setId(rs.getLong("taskuser_id"));
                user.setFirstName(rs.getString("firstname"));
                user.setLastName(rs.getString("lastname"));
                wt.setTaskUser(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        cache.put(new Element(itemId, wt));
        return wt;
    }

    @Override
    public int getNumOfRecoeds() {
        return 0;
    }

    @Override
    public List<WorkTask> getListById(Long itemId) {
        return null;
    }

    @Override
    public List<WorkTask> getAll(int offcet, int limit) {
        return null;
    }
}