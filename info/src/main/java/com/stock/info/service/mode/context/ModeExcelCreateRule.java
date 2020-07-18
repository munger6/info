package com.stock.info.service.mode.context;


import java.util.List;

/**
 * 模型excel创建规则
 */
public class ModeExcelCreateRule {

    /**
     * 证券过滤条件集合
     */
    private List<String> stockFilterConditions;

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
    private String setTitleHandler;
}
