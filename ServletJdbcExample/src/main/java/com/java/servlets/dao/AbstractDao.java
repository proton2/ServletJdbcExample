package com.java.servlets.dao;

import com.java.servlets.dao.Service.DataSource;
import com.java.servlets.dao.Service.ResultSetMapper;
import com.java.servlets.dao.Service.SqlXmlReader;
import com.java.servlets.model.Model;
import com.java.servlets.util.EHCacheManger;
import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

/**
 * Created by proton2 on 03.06.2017.
 */
public class AbstractDao<T extends Model> implements Dao<T> {

    private Class<T> type;
    private DataSource dataSource;

    public Class<T> getType() {
        return type;
    }

    public DataSource getDataSource() {
        return dataSource;
    }

    public AbstractDao(Class<T> type) {
        this.type = type;
        dataSource = DataSource.getInstance();
    }

    @Override
    public List<T> getAll(int offcet, int limit) {
        String getAllSql = SqlXmlReader.getQuerryStr(type.getSimpleName(), "getAll");
        List<T> result = null;
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(getAllSql, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);) {
            ps.setInt(1, offcet);
            ps.setInt(2, limit);
            ResultSet rs = ps.executeQuery();
            ResultSetMapper<T> resultSetMapper = new ResultSetMapper<>();
            result = resultSetMapper.mapRersultSetToList(rs, type);
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public T getById(Long itemId) {
        T item = cacheGet(itemId);
        if (item != null) return item;

        String getSql = SqlXmlReader.getQuerryStr(type.getSimpleName(), "getById");

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(getSql, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);) {
            ps.setLong(1, itemId);
            ResultSet rs = ps.executeQuery();
            ResultSetMapper<T> resultSetMapper = new ResultSetMapper<>();
            item = resultSetMapper.mapRersultSetToObject(rs, type);
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        cachePut(item);
        return item;
    }

    @Override
    public void insert(T entity) {
        String insertSql = SqlXmlReader.getQuerryStr(type.getSimpleName(), "insert");
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS);) {
            ResultSetMapper.putEntityToPreparedStatement(ps, entity);
            ps.executeUpdate();

            ResultSet generatedKeys = ps.getGeneratedKeys();
            if (generatedKeys.next()) {
                entity.setId(generatedKeys.getLong(1));
            } else {
                throw new SQLException("Creating user failed, no ID obtained.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        cachePut(entity);
    }

    @Override
    public void update(T entity) {
        String updateSql = SqlXmlReader.getQuerryStr(type.getSimpleName(), "update");
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(updateSql);) {
            ResultSetMapper.putEntityToPreparedStatement(ps, entity);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        cachePut(entity);
    }

    @Override
    public void delete(Long id) {
        String deleteSql = SqlXmlReader.getQuerryStr(type.getSimpleName(), "delete");
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(deleteSql);) {
            ps.setLong(1, id);
            ps.executeUpdate();
            cacheRemove(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void cachePut(T item) {
        Cache cache = EHCacheManger.getCache();
        cache.put(new Element(item.getId(), item));
    }

    private void cacheRemove(Long itemId) {
        Cache cache = EHCacheManger.getCache();
        cache.remove(itemId);
    }

    private T cacheGet(Long itemId) {
        Cache cache = EHCacheManger.getCache();
        Element element = cache.get(itemId);
        if (element != null) {
            return (T) element.getObjectValue();
        }
        return null;
    }

    @Override
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
}
