package com.stock.info.Util;

import com.stock.info.domain.ResultPage;
import com.stock.info.domain.pojo.StkIndexValuePojo;
import org.apache.commons.collections.MapUtils;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PublicUtil {

    /**
     * 初始化结果信息
     * @param isSuccess
     * @param msg
     * @param date
     * @return
     */
    public static ResultPage initResult(boolean isSuccess, String msg, Object date) {
        return new ResultPage(isSuccess, msg, date);
    }


    /**
     * 转换数字数组为字符串数组
     * @param data
     * @return
     */
    public static List<String> converNumberToString(List<BigDecimal> data) {
        List<String> result = new ArrayList<>();
        if(!CollectionUtils.isEmpty(data)){
            for (int i = 0; i < data.size(); i++) {
                result.add(data.get(i).toString());
            }
        }
        return result;
    }


    /**
     * 转换list集合为Map集合
     * @param lists     list集合
     * @param field     主键字段
     * @param <E>
     * @return
     */
    public static  <E>  Map<String, E>  converListToMap(List<E> lists, String field) {
        Map<String, E> result = new HashMap<>();
        if(!CollectionUtils.isEmpty(lists)){
            E value;
            Map<String,Object> map;
            for (int i = 0; i < lists.size(); i++) {
                value = lists.get(i);
                if(value instanceof Map){
                    result.put(MapUtils.getString((Map) value,field),value);
                }else{
                    map = BeanUtil.convertToMap(value);
                    result.put(MapUtils.getString(map,field),value);
                }
            }
        }
        return result;
    }
}
