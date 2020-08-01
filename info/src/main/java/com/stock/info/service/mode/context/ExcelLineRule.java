package com.stock.info.service.mode.context;

import java.util.TreeMap;

/**
 * excel行书写规则
 */
public class ExcelLineRule {

    /**
     * 列表信息
     */
    private String lineName;

    /**
     * 类型： index：为写入指标数据，按照指标的时间排序
     *        expression ： 表达式方式（ = B7 + B8）
     */
    private String type;

    /**
     * 类型为表达式的时候写入表达式函数；
     * 为指标的时候则写入指标值code获取指标值；
     */
    private String value;

    /**
     * 变量参数；   依赖指标key为 beforeIndex
     */
    private TreeMap<String,String> variableParam;


    public ExcelLineRule(String lineName, String type, String value) {
        this.lineName = lineName;
        this.type = type;
        this.value = value;
    }

    //get and set
    public TreeMap<String, String> getVariableParam() {
        return variableParam;
    }

    public void setVariableParam(TreeMap<String, String> variableParam) {
        this.variableParam = variableParam;
    }

    public String getLineName() {
        return lineName;
    }

    public String getType() {
        return type;
    }

    public String getValue() {
        return value;
    }

}
