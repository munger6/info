package com.stock.info.domain.pojo;

public class StkIndexValuePojo {
    /**证券代码*/
    private String tsCode;
    /**指标值*/
    private Double value;

    //get and set
    public String getTsCode() {
        return tsCode;
    }

    public void setTsCode(String tsCode) {
        this.tsCode = tsCode;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }




    @Override
    public String toString() {
        return "StkIndexValuePojo{" +
                "tsCode='" + tsCode + '\'' +
                ", value=" + value +
                '}';
    }
}
