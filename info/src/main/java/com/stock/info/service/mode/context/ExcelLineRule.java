package com.stock.info.service.mode.context;

import java.util.List;

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
     * 是否首行空白
     */
    private boolean isFirstBlank;

     /**
     * 最大行长度
     */
    private int maxLineLength;

    /**
     * 变量参数；   依赖指标key为 beforeIndex
     */
    private List<String> variableParam;


    /**
     * 未来行信息处理规则
     */
    private ExcelLineRule futureLineRule;


      public ExcelLineRule(String lineName, String type, String value) {
        this.lineName = lineName;
        this.type = type;
        this.value = value;
        this.isFirstBlank = false;
        this.maxLineLength = 10;
    }

    public ExcelLineRule(String lineName, String type, String value,boolean isFirstBlank) {
        this.lineName = lineName;
        this.type = type;
        this.value = value;
        this.isFirstBlank = isFirstBlank;
    }


    //get and set
    public int getMaxLineLength() {
        return maxLineLength;
    }

    public void setMaxLineLength(int maxLineLength) {
        this.maxLineLength = maxLineLength;
    }

    public ExcelLineRule getFutureLineRule() {
        return futureLineRule;
    }

    public void setFutureLineRule(ExcelLineRule futureLineRule) {
        this.futureLineRule = futureLineRule;
    }

    public List<String> getVariableParam() {
        return variableParam;
    }

    public void setVariableParam(List<String> variableParam) {
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

    public boolean isFirstBlank() {
        return isFirstBlank;
    }
}
