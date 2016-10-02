package com.java.servlets.dao;

import com.java.servlets.model.Model;
import com.java.servlets.model.User;
import com.java.servlets.model.WorkTask;
import com.java.servlets.util.EHCacheManger;
import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by proton2 on 13.08.2016.
 */
public class DaoFactory {
    private static Map<Class<? extends Model>, ModelDao<?>> creators = null;
    
    public static <T extends ModelDao> T getDao(Class<? extends Model> dtoClass) {
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
    
    public static Model getById(Long id, boolean eager, Class <? extends Model> dtoClass, String... joinFields){

        Model model = null;
        Cache cache = EHCacheManger.getCache();
        Element element = cache.get(new Long(id));
        if (element == null){
            System.out.println("cache miss");
            model = getDao(dtoClass).getById(id, eager, joinFields);
            cache.put(new Element(id, model));
        } else {
            model = (Model) element.getObjectValue();
        }

        return model;
    }

    public static void delete(Long id, Class <? extends Model> dtoClass){
        Cache cache = EHCacheManger.getCache();
        cache.remove(id);
        getDao(dtoClass).delete(id);
    }
    
    public static void insert(Model item){
    	Long itemId = getDao(item.getClass()).insert(item);
        Cache cache = EHCacheManger.getCache();
        cache.put(new Element(itemId, item));
    }
    
    public static <T extends Model> void  update(T item){
        Cache cache = EHCacheManger.getCache();
        cache.remove(item.getId());

    	getDao(item.getClass()).update(item);
        cache.put(new Element(item.getId(), item));
    }

    public static List<? extends Model> getAll(Class <? extends Model> dtoClass, String... params){
        return getDao(dtoClass).getAll(true, params);
    }

    public static List<? extends Model> getListById(Long id, Class <? extends Model> dtoClass, boolean eager, String... fields){
        return getDao(dtoClass).getListById(id, eager, fields);
    }
}