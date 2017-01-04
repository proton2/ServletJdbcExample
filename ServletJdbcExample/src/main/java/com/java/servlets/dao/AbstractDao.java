package com.java.servlets.dao;

import com.java.servlets.model.Model;
import com.java.servlets.util.DataSource;
import com.java.servlets.util.EHCacheManger;
import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by proton2 on 24.12.2016.
 */
abstract public class AbstractDao implements ModelDao {
    private Long insertId;
    private Connection connection;

    abstract public void insertItem(Model item) throws SQLException;
    abstract public void updateItem(Model item) throws SQLException;

    @Override
    public Long insert(Model item) {
        try {
            insertItem(item);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return insertId;
    }

    @Override
    public void update(Model item) {
        try {
            updateItem(item);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void performDelete(Long itemId, String deleteSql){
        cacheRemove(itemId);

        Connection connection = DataSource.getInstance().getConnection();
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement(deleteSql);
            ps.setLong(1, itemId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (ps != null) try {ps.close();} catch (SQLException e) {e.printStackTrace();}
            if (connection != null) try {connection.close();} catch (SQLException e) {e.printStackTrace();}
        }
    }

    public PreparedStatement getPreparedStatement (String querry){
        connection = DataSource.getInstance().getConnection();
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement(querry);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ps;
    }

    public PreparedStatement getNavigablePreparedStatement (String querry){
        connection = DataSource.getInstance().getConnection();
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement(querry, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ps;
    }

    public boolean resultSetNext(ResultSet rs){
        try {
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void executeUpdate(PreparedStatement ps) {
        try {
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (ps != null) try {ps.close();} catch (SQLException e) {e.printStackTrace();}
            try {connection.close();} catch (SQLException e) {e.printStackTrace();}
        }
    }

    public ResultSet executeGetById(PreparedStatement ps, Long itemId){
        ResultSet rs = null;
        try {
            ps.setLong(1, itemId);
            rs = ps.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;
    }

    public ResultSet executeGetAll(PreparedStatement ps, int offcet, int limit){
        ResultSet rs = null;
        try {
            ps.setInt(1, offcet);
            ps.setInt(2, limit);
            rs = ps.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;
    }

    public ResultSet executeGetAll(PreparedStatement ps){
        ResultSet rs = null;
        try {
            rs = ps.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;
    }

    public void closeResultSet(PreparedStatement ps, ResultSet rs){
        if (rs != null) try {rs.close();} catch (SQLException e) {e.printStackTrace();}
        if (ps != null) try {ps.close();} catch (SQLException e) {e.printStackTrace();}
        if (connection != null) try {connection.close();} catch (SQLException e) {e.printStackTrace();}
    }

    public Long getInsertId(String tableName) {
        Connection connection = DataSource.getInstance().getConnection();
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT max(id) FROM " + tableName);
            while (resultSet.next()) {
                insertId = resultSet.getLong(1);
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

    public int getNumOfRecords(String tableName) {
        Connection connection = DataSource.getInstance().getConnection();
        Statement statement = null;
        ResultSet resultSet = null;
        int numOfRecords = -1;
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery("select count(*) from " + tableName);
            while (resultSet.next()) {
                numOfRecords = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (resultSet != null) try {resultSet.close();} catch (SQLException e) {e.printStackTrace();}
            if (statement != null) try {statement.close();} catch (SQLException e) {e.printStackTrace();}
            try {connection.close();} catch (SQLException e) {e.printStackTrace();}
        }
        return numOfRecords;
    }

    public void cachePut(Model item) {
        Cache cache = EHCacheManger.getCache();
        cache.put(new Element(item.getId(), item));
    }

    public void cacheRemove(Model item) {
        Cache cache = EHCacheManger.getCache();
        cache.remove(item.getId());
    }

    public void cacheRemove(Long itemId) {
        Cache cache = EHCacheManger.getCache();
        cache.remove(itemId);
    }

    public Model cacheGet(Long itemId){
        Cache cache = EHCacheManger.getCache();
        Element element = cache.get(itemId);
        if (element != null) {
            return (Model) element.getObjectValue();
        }
        return null;
    }
}
