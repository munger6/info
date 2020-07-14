package com.stock.info.Util;

import org.apache.log4j.spi.LoggerFactory;
import org.springframework.cglib.beans.BeanMap;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;

public class BeanUtil {
//    private Logger logger = LoggerFactory

    public static Map<String, Object> convertToMap(Object obj) {
        return convertToMap(obj, false);
    }

    public static Map<String, Object> convertToMap(Object obj, boolean convertNull) {
        if(obj == null){
            return null;
        }

        Map<String, Object> map = new HashMap<>();

        Field[] declaredFields = obj.getClass().getDeclaredFields();
        for (Field field : declaredFields) {
            field.setAccessible(true);
            try {
                Object o = field.get(obj);
                if (Objects.nonNull(o)){
                    if (o instanceof Collection) {
                        if (((Collection)o).size()==0){
                            continue;
                        }
                    } else if (o instanceof Map) {
                        if (((Map) o).size() == 0) {
                            continue;
                        }
                    } else if (o instanceof Enum) {
                        o = ((Enum) o).name();
                    }
                    map.put(field.getName(), o);
                }
                else if(convertNull) {
                    map.put(field.getName(), null);
                }

            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        return map;
    }



    /**
     * 将map集合中的数据转化为指定对象的同名属性中
     */
    public static <T> T mapToBean(Map<String, Object> map, Class<T> clazz){
        try {
            T bean = clazz.newInstance();
            BeanMap beanMap = BeanMap.create(bean);
            beanMap.putAll(map);
            return bean;
        } catch (Exception e) {
            return null;
        }
    }
}
