package com.stock.info.Util;

import com.stock.info.constant.PublicConstant;

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

}
