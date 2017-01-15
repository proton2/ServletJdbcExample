package com.java.servlets.dao.Service;

import com.java.servlets.util.SysHelper;
import org.apache.commons.beanutils.BeanUtils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by proton2 on 01.01.2017.
 * Thank for idea
 * https://www.codeproject.com/tips/372152/mapping-jdbc-resultset-to-object-using-annotations
 */
public class ResultSetMapper<T> {

    private static Map<String, Object> copyResultSetToMap(ResultSet rs) {
        Map<String, Object> resultMap = new HashMap<>();
        try {
            ResultSetMetaData meta = rs.getMetaData();
            rs.previous();
            if (rs.next()) {
                for (int i = 1; i <= meta.getColumnCount(); i++) {
                    String key = meta.getColumnName(i);
                    resultMap.put(key, rs.getObject(key));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultMap;
    }

    private static List<Field> getReflectionFields(Class<?> type) {
        List<Field> fields = new ArrayList<>();
        fields.addAll(Arrays.asList(type.getDeclaredFields()));
        if (type.getSuperclass() != null && type.getSuperclass() != Object.class) {
            fields.addAll(getReflectionFields(type.getSuperclass()));
        }
        return fields;
    }

    private static boolean checkIsSubclass(Class<?>clazz){
        if (clazz.isEnum() ||
                clazz.isAssignableFrom(java.util.Date.class) ||
                (clazz.getSuperclass()!=null && clazz.getSuperclass().isAssignableFrom(Number.class)) || //Collection
                clazz.isAssignableFrom(String.class) ||
                clazz.isAssignableFrom(Character.class) ||
                clazz.isAssignableFrom(Boolean.class))
        {
            return false;
        }
        return true;
    }

    private static Map<String, Object> prepareSubclassQuerryFields(Field field, Map<String, Object> currMap) {
        Map<String, Object> newMap = new HashMap();
        for (Map.Entry<String, Object> entry : currMap.entrySet()) {
            String name = entry.getKey();
            if (SysHelper.getClassNameFromAlias(name).equalsIgnoreCase(field.getName())) {
                String newKey = SysHelper.getFileExt(field.getType().getName()) + "_" + SysHelper.getFieldNameFromAlias(name);
                newMap.put(newKey, entry.getValue());
            } else if (entry.getValue()!=null){
                newMap.put(name, entry.getValue());
            }
        }
        return newMap;
    }

    public List<T> mapRersultSetToList(ResultSet rs, Class<T> outputClass) {
        List<T> outputList = new ArrayList<T>();
        try {
            while (rs.next()){
                outputList.add(mapRersultSetToObject(rs, outputClass));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return outputList;
    }

    public T mapRersultSetToObject(ResultSet rs, Class<T> outputClass) {
        return mapRersultSetToObject(outputClass, copyResultSetToMap(rs), getReflectionFields(outputClass));
    }

    private T mapRersultSetToObject(Class outputClass, Map<String, Object> currMap, List<Field> fields) {
        T outputObject = null;
        try {
            if (!currMap.isEmpty()) {
                T bean = (T) outputClass.newInstance();
                for (Map.Entry<String, Object> entry : currMap.entrySet()) {
                    String columnName = entry.getKey();
                    Object columnValue = entry.getValue();
                    for (Field field : fields) {
                        if (SysHelper.getClassNameFromAlias(columnName).equalsIgnoreCase(SysHelper.getFileExt(outputClass.getName())) &&
                                SysHelper.getFieldNameFromAlias(columnName).equalsIgnoreCase(field.getName()) && columnValue != null &&
                                BeanUtils.getProperty(bean, field.getName()) == null)
                        {
                            BeanUtils.setProperty(bean, field.getName(), columnValue);
                            currMap.put(columnName, null);
                            break;
                        }
                        else if (field.getType().isEnum() && BeanUtils.getProperty(bean, field.getName()) == null &&
                                SysHelper.getClassNameFromAlias(columnName).equalsIgnoreCase(field.getName()) && columnValue != null)
                        {
                            Class clazz = field.getType();
                            Enum val = null;
                            if (columnValue.getClass().equals(Integer.class)) {
                                val = (Enum) clazz.getEnumConstants()[(Integer)columnValue];
                            } else if (columnValue.getClass().equals(String.class)) {
                                val = Enum.valueOf(clazz, (String)columnValue);
                            }
                            BeanUtils.setProperty(bean, field.getName(), val);
                            currMap.put(columnName, null);
                            break;
                        }
                        else if (checkIsSubclass(field.getType()) && BeanUtils.getProperty(bean, field.getName()) == null &&
                                SysHelper.getClassNameFromAlias(columnName).equalsIgnoreCase(field.getName()) && columnValue != null)
                        {
                            Map<String, Object> subclassResultSet = prepareSubclassQuerryFields(field, currMap);
                            if(!subclassResultSet.isEmpty()) {
                                List<Field> subclassFields = getReflectionFields(field.getType());
                                BeanUtils.setProperty(bean, field.getName(), mapRersultSetToObject(field.getType(), subclassResultSet, subclassFields));
                                currMap.put(columnName, null);
                                break;
                            }
                        }
                    }
                }
                outputObject = bean;
            }
        } catch (IllegalAccessException | InstantiationException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }
        return outputObject;
    }

    public static void putEntityToPreparedStatement(PreparedStatement ps, Object entity){
        List<Field> fields = getReflectionFields(entity.getClass());
        int i=0;
        try {
            for (Field field : fields) {
                i++;
                field.setAccessible(true);
                Object entityField = field.get(entity);
                if (entityField==null){
                    i--;
                    continue;
                }
                int type = ps.getParameterMetaData().getParameterType(i);
                if (checkIsSubclass(entityField.getClass())){
                    ps.setObject(i, Long.parseLong(BeanUtils.getProperty(entityField, "id")), type);
                } else {
                    ps.setObject(i, entityField, type);
                }
            }
        } catch (NoSuchMethodException | InvocationTargetException | SQLException | IllegalAccessException e) {
                e.printStackTrace();
        }
    }
}