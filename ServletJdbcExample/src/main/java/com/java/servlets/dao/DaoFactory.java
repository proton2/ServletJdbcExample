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
    
    private static <T extends ModelDao> T getDao(Class<? extends Model> dtoClass) {
    	if (creators == null){
    		creators = new HashMap<>();
        	creators.put(User.class, new UserDao());
            creators.put(WorkTask.class, new WorkTaskDao());
    	}
    	@SuppressWarnings("unchecked")
		T creator = (T) creators.get(dtoClass);
        if (creator == null) {
            throw new Error("Dao object for " + dtoClass + " not found.");
        }
        return creator;
    }
    
    public static Model getById(Long id, Class <? extends Model> dtoClass, String... joinFields){
    	return getDao(dtoClass).getById(id, joinFields);
    }
    
    public static List<? extends Model> getAll(Class <? extends Model> dtoClass, String... params){
        return getDao(dtoClass).getAll(params);
    }
    
    public static void delete(Long id, Class <? extends Model> dtoClass){
    	getDao(dtoClass).delete(id);
    }
    
    public static void insert(Model item){
    	getDao(item.getClass()).insert(item);
    }
    
    public static <T extends Model> void  update(T item){
    	getDao(item.getClass()).update(item);
    }
/*
    public static List<WorkTask> getUserWorkTasks(User user){
        UserDao userDao = (UserDao) creators.get(User.class);
        return userDao.getUserWorkTasks(user);
    }
    */
}
