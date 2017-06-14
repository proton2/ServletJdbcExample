package com.java.servlets.dao;

import com.java.servlets.model.WorkTask;
import com.java.servlets.dao.Service.DataSource;
import com.java.servlets.dao.Service.SqlXmlReader;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Collection;

/**
 * Created by proton2 on 04.01.2017.
 */
public class ImportDao {

    public ImportDao(){}

    public int importCollection(Collection<WorkTask> workTaskList){
        Connection connection = DataSource.getInstance().getConnection();
        PreparedStatement ps = null;
        int[] updateCounts = null;
        String insertSql = SqlXmlReader.getQuerryStr("WorkTask", "insert");
        if (workTaskList!=null && !workTaskList.isEmpty()) {
            try {
                connection.setAutoCommit(false);
                ps = connection.prepareStatement(insertSql);
                for (WorkTask workTask : workTaskList) {
                    ps.setLong(1, workTask.getTaskUser().getId());
                    ps.setString(2, workTask.getCaption());
                    ps.setString(3, workTask.getTaskContext());
                    ps.setDate(4, new java.sql.Date(workTask.getTaskDate().getTime()));
                    ps.setDate(5, new java.sql.Date(workTask.getDeadLine().getTime()));
                    ps.setString(6, workTask.getTaskStatus().name());
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
            }finally {
                if (ps != null) try {ps.close();} catch (SQLException e1) {e1.printStackTrace();}
                try {connection.close();} catch (SQLException e2) {e2.printStackTrace();}
            }
        }
        return updateCounts == null ? null : updateCounts.length;
    }
}
