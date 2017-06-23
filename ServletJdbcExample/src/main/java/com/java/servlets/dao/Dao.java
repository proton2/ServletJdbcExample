package com.java.servlets.dao;

import com.java.servlets.model.Model;

import java.io.IOException;
import java.util.List;

/**
 * Created by proton2 on 03.06.2017.
 */
public interface Dao <T extends Model> {
    List<T> getAll(int offcet, int limit) throws IOException;
    T getById(Long id) throws IOException;
    void insert (T entity) throws IOException;
    void delete (Long id) throws IOException;
    void update (T entity) throws IOException;
    int getNumOfRecords(String tableName) throws IOException;
}
