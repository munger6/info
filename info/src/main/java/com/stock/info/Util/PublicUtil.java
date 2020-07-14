package com.stock.info.Util;

import com.stock.info.domain.ResultPage;

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


}
