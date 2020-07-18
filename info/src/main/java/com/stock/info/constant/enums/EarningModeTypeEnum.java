package com.stock.info.constant.enums;

import org.springframework.util.StringUtils;

/**
 * 财务模型类型枚举值
 */
public enum  EarningModeTypeEnum {
    /**折现因子模型*/
    TAO_MODE("1","涛哥折现财务模型"),
    /**roce模型*/
    LIN_MODE("2","杨林ROIC财务模型"),
    ;

    /**
     * 财务模型code
     */
    private String code;

    /**
     * 财务模型描述
     */
    private String desc;

    EarningModeTypeEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    /**
     * 根据code获取财务模型枚举
     * @param code
     * @return
     */
    public static EarningModeTypeEnum getEnumByCode(String code){
        if(!StringUtils.isEmpty(code)){
            EarningModeTypeEnum[] values = EarningModeTypeEnum.values();
            for (int i = 0; i < values.length; i++) {
                if(values[i].getCode().equals(code)){
                    return values[i];
                }
            }
        }
        return null;
    }

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
    }
}
