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
     * 行扩展规则（增速扩展（固定增速扩展/增速拟合扩展）、    汇总扩展sum()）
     */
    private ExtendLineRule extendLineRule;


    /**
     * 查询指标信息类构造函数
     * @param lineName
     * @param type
     * @param value
     */
    public ExcelLineRule(String lineName, String type, String value,Integer maxLineLength) {
        this.lineName = lineName;
        this.type = type;
        this.value = value;
        this.isFirstBlank = false;
        if(maxLineLength == null){
            this.maxLineLength = 10;
        }else{
            this.maxLineLength = maxLineLength;
        }
    }

    /**
     * 指标查询类 + 扩展规则 类构造函数
     * @param lineName
     * @param type
     * @param value
     * @param extendLineRule
     */
    public ExcelLineRule(String lineName, String type, String value,Integer maxLineLength,ExtendLineRule extendLineRule) {
        this.lineName = lineName;
        this.type = type;
        this.value = value;
        this.extendLineRule = extendLineRule;
        this.isFirstBlank = false;
        if(maxLineLength == null){
            this.maxLineLength = 10;
        }else{
            this.maxLineLength = maxLineLength;
        }
    }


    /***
     *
     * @param lineName
     * @param type
     * @param value
     * @param isFirstBlank
     */
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

    public ExtendLineRule getExtendLineRule() {
        return extendLineRule;
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
