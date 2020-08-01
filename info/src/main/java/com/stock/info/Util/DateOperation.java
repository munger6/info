package com.stock.info.Util;

import org.apache.commons.lang3.time.DateUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateOperation extends DateUtils {

    public final static String DAY = "yyyyMMdd";
    public final static String YEAR =  "yyyy";



    public static String formatDate(Date date, String format){
//        SimpleDateFormat f = new SimpleDateFormat("今天是 " + "yyyy 年 MM 月 dd 日 E HH 点 mm 分 ss 秒");
        SimpleDateFormat f = new SimpleDateFormat(format);
        return  f.format(date);
    }

    public static Date getStartYear(Date date) {
        if(date != null){
            date = setMonths(date,0);
            date = setDays(date,0);
            date = setHours(date,0);
            date = setMinutes(date,0);
            date = setMilliseconds(date,0);
            date = setSeconds(date,0);
        }
        return date;
    }
}
