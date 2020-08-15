package com.stock.info.Util;

public class ComparUtil {

    /**大于等于*/
    public static String ge = ">=";
    /**大于等于*/
    public static String gt = ">";
    /**大于等于*/
    public static String le = "<=";
    /**大于等于*/
    public static String lt = "<";


    /**
     * 按照类型对比
     * @param type
     * @param value1
     * @param vaule2
     * @return
     */
    public static Boolean compar(String type, int value1, int vaule2){
        if(ge.equals(type)){
            return value1 >= vaule2;
        }else if(gt.equals(type)){
            return value1 > vaule2;
        }else if(gt.equals(type)){
            return value1 <= vaule2;
        }else if(gt.equals(type)){
            return value1 < vaule2;
        }
        return null;
    }


    /**
     * 按照类型对比
     * @param type
     * @param value1
     * @param vaule2
     * @return
     */
    public static Boolean compar(String type, double value1, double vaule2){
        if(ge.equals(type)){
            return value1 >= vaule2;
        }else if(gt.equals(type)){
            return value1 > vaule2;
        }else if(gt.equals(type)){
            return value1 <= vaule2;
        }else if(gt.equals(type)){
            return value1 < vaule2;
        }
        return null;
    }


}
