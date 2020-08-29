package com.stock.info.Util;

import com.stock.info.constant.PublicConstant;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.math.NumberUtils;

import java.util.Map;

public class StringUtil {


    /**
     * 转换sqlcode码为驼峰命名code
     * @param sqlCode
     * @return
     */
    public static String convertSqlCodeToHump(String sqlCode) {
        String[] sqlCodes = sqlCode.split(PublicConstant.overbar);
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < sqlCodes.length; i++) {
            if(i> 0){
                buffer = buffer.append(sqlCodes[i].substring(0,1).toUpperCase()).append(sqlCodes[i].substring(1));
            }else{
                buffer = buffer.append(sqlCodes[i]);
            }
        }
        return buffer.toString();
    }


    /**
     * 转换字母为ascii码,然后进位
     * @param val
     * @param i
     */
    public static String addAsciiCode(String val, int i) {
        if(NumberUtils.isCreatable(val)){
            //兼容数字形式的增加（超过9之后）
            Integer index = Integer.valueOf(val);
            index += i;
            return index + "";
        }
        char c = val.charAt(0);
        int number = (int) c;
        number += i;
        return String.valueOf((char) number);
    }

    /**
     * 循环替代所有的参数
     * @param expersion
     * @param tempVariable
     * @return
     */
    public static String replaceAll(String expersion, Map<String, String> tempVariable) {
        if(MapUtils.isNotEmpty(tempVariable)){
            for (String key : tempVariable.keySet()){
                expersion = expersion.replaceAll("\\{" + key + "\\}",tempVariable.get(key));
            }
        }
        return expersion;
    }
}
