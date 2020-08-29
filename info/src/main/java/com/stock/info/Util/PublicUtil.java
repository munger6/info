package com.stock.info.Util;

import com.stock.info.domain.ResultPage;
import com.stock.info.domain.entity.StkStockAll;
import com.stock.info.domain.pojo.StkIndexValuePojo;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

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


    /**
     * 转换list对象为list<String>
     * @param lists
     * @param field  获取字段值
     * @return
     */
    public static  <E> List<String> converListToListStr(List<E> lists, String field) {
        List<String> result = new ArrayList<>();
        if(!CollectionUtils.isEmpty(lists)){
            E value;
            Map<String,Object> map;
            for (int i = 0; i < lists.size(); i++) {
                value = lists.get(i);
                if(value instanceof Map){
                    result.add(MapUtils.getString((Map) value,field));
                }else{
                    map = BeanUtil.convertToMap(value);
                    result.add(MapUtils.getString(map,field));
                }
            }
        }
        return result;
    }


    /**
     * 平方公用方法
     * @param speed   增速
     * @param count   平方次
     * @param bit  剩余位数
     * @return
     */
    public static Double powFormat(BigDecimal speed, int count, int bit) {
        return speed.pow(count).setScale(bit,BigDecimal.ROUND_HALF_UP).doubleValue();
    }


    /**
     * 格式化数值字符串
     * @param indexData   指标值
     * @param unit        单位类型
     * @return
     */
    public static String formatNumberStr(String indexData, int unit) {
        if(StringUtils.isEmpty(indexData) || !NumberUtils.isCreatable(indexData)){
            return indexData;
        }
        int divide = 1;
        if(unit == 1<<1){
            divide = 10000;
        }
        if(unit == 1<<2){
            divide = 1000000;
        }
        if(unit == 1<<3){
            divide = 100000000;
        }
        return new BigDecimal(indexData).divide(new BigDecimal(divide)).setScale(2,BigDecimal.ROUND_HALF_UP).toString();
    }
}
