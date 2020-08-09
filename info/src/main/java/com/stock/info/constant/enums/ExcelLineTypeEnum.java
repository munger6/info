package com.stock.info.constant.enums;

/**
 * excel行类型枚举
 */
public enum  ExcelLineTypeEnum {
    /**指标类型*/
    INDEX("index","指标类型"),
    /**表达式类型*/
    EXPRESSION("expression","表达式类型"),
    /**空白行*/
    BLANK("blank","空白行"),
    ;


    /**
     * 财务模型code
     */
    private String code;

    /**
     * 财务模型描述
     */
    private String desc;

    ExcelLineTypeEnum(String code, String desc) {
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
