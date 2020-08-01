package com.stock.info.service.mode.context;


import com.stock.info.constant.PublicConstant;
import com.stock.info.constant.enums.IndexMessageEnum;

/**
 * 指标信息
 */
public class IndexMessage {

    private IndexMessageEnum indexMessageEnum;

    //指标周期（支持年--YEAR /季度）
    private String period = PublicConstant.YEAR;

    public IndexMessage(IndexMessageEnum indexMessageEnum) {
        this.indexMessageEnum = indexMessageEnum;
    }

    //get and set
    public String getIndexCode() {
        if(indexMessageEnum == null){
            return "";
        }
        return indexMessageEnum.getCode();
    }

    public String getIndexTable()  {
        if(indexMessageEnum == null){
            return "";
        }
        return indexMessageEnum.getTableName();
    }

    public String getIndexColumnName() {
        if(indexMessageEnum == null){
            return "";
        }
        return indexMessageEnum.getColumnName();
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }
}
