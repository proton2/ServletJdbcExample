package com.java.servlets.dao;

import com.java.servlets.model.Model;

import java.util.List;

/**
 * Created by proton2 on 06.08.2016.
 */
public interface ModelDao <T extends Model>{
    Long insert(T item);
    void update(T item);

    void delete(Long id);
    
    List<T> getAll(boolean eager, String... joinFields);
    List<T> getListById(Long itemId, boolean eager, String... fields);
    T getById(Long itemId, boolean eager, String... joinFields);
}
