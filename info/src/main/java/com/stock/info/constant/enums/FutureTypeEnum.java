package com.stock.info.constant.enums;

public enum  FutureTypeEnum {


    /**固定增速*/
    SPEED("speed","固定增速"),
    /**拟合增速*/
    FIT_SPEED("fit_speed","拟合增速"),
    /**固定增速*/
    SUM("sum","加总"),

    ;
    /**
     * 类型code
     */
    private String code;

    /**
     * 类型描述
     */
    private String desc;


    FutureTypeEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    //get方法
    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
