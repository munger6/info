package com.stock.info.service.mode.context;


import java.util.List;
import java.util.Map;

/**
 * 模型excel创建规则
 */
public class ModeExcelCreateRule {

    /**
     * 证券过滤条件集合
     */
    private List<Map<String,Object>> stockFilterConditions;

    /**
     * 基础指标信息获取
     */
    private List<IndexMessage>  indexList;


    /**
     * excel行数据信息
     */
    private List<ExcelLineRule> lineRules;

    /**
     * 设置标题处理类信息
     */
    private String titleHandler;
    //sheetName 信息
    private String sheetName;
    //开始行号
    private int startLine;
    //数据时长
    private Integer dataTimeLong;
    //预测时长
    private Integer forecastTimeLong;
    //计价单位：亿元 1<<3    /百万 1<<2   /万元 1<<1   /元  1
    private int unit;

    public ModeExcelCreateRule(List<Map<String, Object>> stockFilterConditions, List<IndexMessage> indexList, List<ExcelLineRule> lineRules) {
        this.stockFilterConditions = stockFilterConditions;
        this.indexList = indexList;
        this.lineRules = lineRules;
    }

    //get and set

    public int getUnit() {
        return unit;
    }

    public void setUnit(int unit) {
        this.unit = unit;
    }

    public int getStartLine() {
        return startLine;
    }

    public void setStartLine(int startLine) {
        this.startLine = startLine;
    }

    public String getSheetName() {
        return sheetName;
    }

    public void setSheetName(String sheetName) {
        this.sheetName = sheetName;
    }

    public String getTitleHandler() {
        return titleHandler;
    }

    public void setTitleHandler(String titleHandler) {
        this.titleHandler = titleHandler;
    }

    public Integer getForecastTimeLong() {
        return forecastTimeLong;
    }

    public void setForecastTimeLong(Integer forecastTimeLong) {
        this.forecastTimeLong = forecastTimeLong;
    }

    public Integer getDataTimeLong() {
        return dataTimeLong;
    }

    public void setDataTimeLong(Integer dataTimeLong) {
        this.dataTimeLong = dataTimeLong;
    }

    public List<Map<String, Object>> getStockFilterConditions() {
        return stockFilterConditions;
    }

    public List<IndexMessage> getIndexList() {
        return indexList;
    }

    public List<ExcelLineRule> getLineRules() {
        return lineRules;
    }
}
