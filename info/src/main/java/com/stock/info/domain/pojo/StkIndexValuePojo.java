package com.stock.info.domain.pojo;

public class StkIndexValuePojo {
    /**证券代码*/
    private String tsCode;
    /**指标值*/
    private Double indexValue;

    //get and set
    public String getTsCode() {
        return tsCode;
    }

    public void setTsCode(String tsCode) {
        this.tsCode = tsCode;
    }

    public Double getIndexValue() {
        return indexValue;
    }

    public void setIndexValue(Double indexValue) {
        this.indexValue = indexValue;
    }

    @Override
    public String toString() {
        return "StkIndexValuePojo{" +
                "tsCode='" + tsCode + '\'' +
                ", indexValue=" + indexValue +
                '}';
    }
}
