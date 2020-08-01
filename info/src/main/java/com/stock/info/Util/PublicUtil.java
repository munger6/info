package com.stock.info.Util;

import com.stock.info.domain.ResultPage;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

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
}
