package com.java.servlets.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.java.servlets.model.Model;
import com.java.servlets.model.User;
import com.java.servlets.model.WorkTask;

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
    
    public static Model getById(Long id, Class <? extends Model> dtoClass){
    	return getDao(dtoClass).getById(id);
    }
    
    public static List<? extends Model> getAll(Class <? extends Model> dtoClass){
    	return getDao(dtoClass).getAll();
    }
    
    public static void delete(Long id, Class <? extends Model> dtoClass){
    	getDao(dtoClass).delete(id);
    }
    
    public static void insert(Model item, Class <? extends Model> dtoClass){
    	getDao(dtoClass).insert(item);
    }
    
    public static void update(Model item, Class <? extends Model> dtoClass){
    	getDao(dtoClass).update(item);
    }
}
