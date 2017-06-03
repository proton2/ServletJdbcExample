package com.java.servlets.dao;

import com.java.servlets.model.Model;

import java.util.List;

/**
 * Created by proton2 on 03.06.2017.
 */
public interface Dao <T extends Model> {
    List<T> getAll(int offcet, int limit);
    T getById(Long id);
    void insert (T entity);
    void delete (Long id);
    void update (T entity);
    int getNumOfRecords(String tableName);
}
