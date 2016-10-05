package com.java.servlets.dao;

import com.java.servlets.model.Model;
import com.java.servlets.model.User;
import com.java.servlets.model.WorkTask;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by proton2 on 13.08.2016.
 */
public class DaoFactory {
    private static Map<Class<? extends Model>, ModelDao<?>> creators = null;
    
    private static ModelDao <?> getDao(Class<? extends Model> dtoClass) {
    	if (creators == null){
    		creators = new HashMap<>();
        	creators.put(User.class, new UserDao());
            creators.put(WorkTask.class, new WorkTaskDao());
    	}

		ModelDao<?> creator = creators.get(dtoClass);
        if (creator == null) {
            throw new Error("Dao object for " + dtoClass + " not found.");
        }
        return creator;
    }
    
    public static Model getById(Long id, boolean eager, Class <? extends Model> dtoClass, String... joinFields){
        return getDao(dtoClass).getById(id, eager, joinFields);
    }

    public static void delete(Long id, Class <? extends Model> dtoClass){
        getDao(dtoClass).delete(id);
    }
    
    public static void insert(Model item){
    	Long itemId = getDao(item.getClass()).insert(item);
    }
    
    public static void update(Model item){
    	getDao(item.getClass()).update(item);
    }

    public static List<? extends Model> getAll(Class <? extends Model> dtoClass, String... params){
        return getDao(dtoClass).getAll(true, params);
    }

    static List<? extends Model> getListById(Long id, Class <? extends Model> dtoClass, boolean eager, String... fields){
        return getDao(dtoClass).getListById(id, eager, fields);
    }
}