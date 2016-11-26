package com.java.servlets.dao;

import com.java.servlets.model.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by proton2 on 13.08.2016.
 */
public class DaoFactory {
    private static Map<Class<? extends Model>, ModelDao<?>> creators = null;

    private static ModelDao<?> getDao(Class<? extends Model> dtoClass) {
        if (creators == null) {
            creators = new HashMap<>();
            creators.put(User.class, new UserDao());
            creators.put(WorkTask.class, new WorkTaskDao());
            creators.put(WorkTaskView.class, new WorkTaskViewDao());
            creators.put(UserView.class, new UserViewDao());
            creators.put(Attach.class, new AttachDao());
            creators.put(WorkNote.class, new WorkNoteDao());
        }

        ModelDao<?> creator = creators.get(dtoClass);
        if (creator == null) {
            throw new Error("Dao object for " + dtoClass + " not found.");
        }
        return creator;
    }

    public static Model getById(Long id, Class<? extends Model> dtoClass) {
        return getDao(dtoClass).getById(id);
    }

    public static void delete(Long id, Class<? extends Model> dtoClass) {
        getDao(dtoClass).delete(id);
    }

    public static void insert(Model item) {
        Long itemId = getDao(item.getClass()).insert(item);
    }

    public static void update(Model item) {
        getDao(item.getClass()).update(item);
    }

    public static List<? extends Model> getAll(Class<? extends Model> dtoClass) {
        return getDao(dtoClass).getAll();
    }

    public static List<? extends Model> getListById(Long id, Class<? extends Model> dtoClass) {
        return getDao(dtoClass).getListById(id);
    }
}