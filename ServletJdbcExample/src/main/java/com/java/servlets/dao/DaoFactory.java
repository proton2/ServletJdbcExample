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

        Model model = null;
        Cache cache = EHCacheManger.getCache();
        Element element = cache.get(id);
        if (element == null){
            model = getDao(dtoClass).getById(id, eager, joinFields);
            System.out.println("db loading " + model.toString());
            cache.put(new Element(id, model));
        } else {
            model = (Model) element.getObjectValue();
            System.out.println("cache loading " + model.toString());
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
        System.out.println("cache put " + item.toString());
    }
    
    public static void update(Model item){
        Cache cache = EHCacheManger.getCache();
        cache.remove(item.getId());

    	getDao(item.getClass()).update(item);
        cache.put(new Element(item.getId(), item));
        System.out.println("cache update " + item.toString());
    }

    public static List<? extends Model> getAll(Class <? extends Model> dtoClass, String... params){
        return getDao(dtoClass).getAll(true, params);
    }

    static List<? extends Model> getListById(Long id, Class <? extends Model> dtoClass, boolean eager, String... fields){
        return getDao(dtoClass).getListById(id, eager, fields);
    }
}