package com.stock.info.service.mode.context;


/**
 * 指标信息
 */
public class IndexMessage {

    // 指标名
    private String indexCode;

    // 指标表
    private String indexTable;

    //指标列名
    private String indexColumnName;

    //指标周期（支持年--YEAR /季度）
    private String period = "YEAR";



    //get and set
    public String getIndexCode() {
        return indexCode;
    }

    public void setIndexCode(String indexCode) {
        this.indexCode = indexCode;
    }

    public String getIndexTable() {
        return indexTable;
    }

    public void setIndexTable(String indexTable) {
        this.indexTable = indexTable;
    }

    public String getIndexColumnName() {
        return indexColumnName;
    }

    public void setIndexColumnName(String indexColumnName) {
        this.indexColumnName = indexColumnName;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }
}
