package com.java.servlets.dao;

import com.java.servlets.model.Model;

import java.util.List;

/**
 * Created by proton2 on 06.08.2016.
 */
public interface ModelDao<T extends Model> {
    Long insert(Model item);

    void update(Model item);

    void delete(Long id);

    List<T> getAll(int offcet, int limit);

    List<T> getListById(Long itemId);

    T getById(Long itemId);

    int getNumOfRecords();
}
