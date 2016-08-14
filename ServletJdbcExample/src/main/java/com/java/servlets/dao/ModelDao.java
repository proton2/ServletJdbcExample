package com.java.servlets.dao;

import com.java.servlets.model.Model;

import java.util.List;

/**
 * Created by proton2 on 06.08.2016.
 */
public interface ModelDao <T extends Model>{
    void insert(T item);
    void update(T item);

    void delete(Long id);
    List<T> getAll();
    T getById(Long itemId);
}
