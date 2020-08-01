package com.stock.info.constant.enums;

/***
 * 过滤条件类型枚举
 */
public enum  FilterContionTypeEnum {
    /**上市总时间*/
    LISTING_TIME_TOTAL("listingTimeTotal","上市总时间"),
    /**十年roe平均值 低于十年则采用所有时间的平均数*/
    ROE_AVG_TEN("roeAvgTen","十年roe平均值"),

    ;
    /**
     * 财务模型code
     */
    private String code;

    /**
     * 财务模型描述
     */
    private String desc;

    FilterContionTypeEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    //get and set

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }}
