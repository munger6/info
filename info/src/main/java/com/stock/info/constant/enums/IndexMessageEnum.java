package com.stock.info.constant.enums;

public enum  IndexMessageEnum {
    ////////////////////////////////////////利润表//////////////////////////////////////////
    /**净利润(含少数股东损益)*/
    INCOME("income","净利润(含少数股东损益)","stk_finance_income", "n_income"),


    ////////////////////////////////////////资产负债表//////////////////////////////////////////
    /**股东权益合计(含少数股东权益)*/
    EQUITY("equity","股东权益合计(含少数股东权益)","stk_finance_banlancesheet","total_hldr_eqy_inc_min_int"),

    /**   实收资本（或股本）- 期末总股本*/
    TOTAL_SHARE("TOTAL_SHARE","实收资本（或股本）","stk_finance_banlancesheet","total_share"),

    ////////////////////////////////////////指标表//////////////////////////////////////////
    /**加权平均净资产收益率 :    roe 可能需要采用去年净资产计算口径， 而不是加权平均*/
    ROE("roe","加权平均净资产收益率","stk_finance_indicator","roe_waa"),

    ////////////////////////////////////////月线行情表//////////////////////////////////////////
    /**年度收盘价  ： 接口号： monthly   输入参数：ts_code / trade_date / start_date  /  end_date  格式： YYYYMMDD*/
    PRICE_M("price_month","年度收盘价","stk_finance_monthly","close"),

    ////////////////////////////////////////周线行情表//////////////////////////////////////////
    /**年度收盘价  ： 接口号： weekly   输入参数：ts_code / trade_date / start_date  /  end_date  格式： YYYYMMDD
     *      主要指数代码：zs000001 - 上证指数；    1399001 -深圳成指  ；  zs399006 - 创业板；  zs399005 - 中小板
     * */
    PRICE_W("price_week","年度收盘价","stk_finance_weekly","close"),

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
