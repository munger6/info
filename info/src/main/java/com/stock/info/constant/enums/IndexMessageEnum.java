package com.stock.info.constant.enums;

public enum  IndexMessageEnum {
    ////////////////////////////////////////利润表//////////////////////////////////////////
    /**净利润(含少数股东损益)*/
    INCOME("income","净利润(含少数股东损益)","stk_finance_income", "n_income"),



    ////////////////////////////////////////资产负债表//////////////////////////////////////////
    /**股东权益合计(含少数股东权益)*/
    EQUITY("equity","股东权益合计(含少数股东权益)","stk_finance_banlancesheet","total_hldr_eqy_inc_min_int"),

    ////////////////////////////////////////资产负债表//////////////////////////////////////////
    /**加权平均净资产收益率*/
    ROE("roe","加权平均净资产收益率","roe_waa","stk_finance_indicator"),

    ;
    /**
     * 财务模型code
     */
    private String code;

    /**
     * 财务模型描述
     */
    private String desc;

    /**
     * 所属表名
     */
    private String tableName;

    /**
     * 所属列明
     */
    private String columnName;

    IndexMessageEnum(String code, String desc, String tableName, String columnName) {
        this.code = code;
        this.desc = desc;
        this.tableName = tableName;
        this.columnName = columnName;
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
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }
}
