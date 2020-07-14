package com.stock.info.service;

import org.springframework.stereotype.Service;

import java.util.Map;

public interface StkStockEarningsService {

    /**
     * 查询
     * @return
     */
    public Map<String, Object> query();


    /**
     * 下载所有的个股
     * @param isHs    是否沪深港通标的，N否 H沪股通 S深股通
     * @param listStatus   上市状态： L上市 D退市 P暂停上市，默认L
     * @param exchange    交易所 SSE上交所 SZSE深交所 HKEX港交所(未上线)
     * @return
     */
    public String downloadStockListFromTushare(String isHs, String listStatus, String exchange);


    /**
     * 查询股票列表
     * @param isHs  是否沪深港通标的，N否 H沪股通 S深股通
     * @param listStatus  上市状态： L上市 D退市 P暂停上市，默认L
     * @param exchange  交易所 SSE上交所 SZSE深交所 HKEX港交所(未上线)
     * @return  查询结果
     */
    public Map<String, Object> queryStockListByContion(String isHs, String listStatus, String exchange);


    /**
     * 下载指定个股的所有财报信息
     *      （资产负债表，利润表，现金流量表，财务指标表，主营业务表）
     * @param code
     * @return
     */
    public void downloadFinanceFromTushare(String code);


    /**
     * 从tushare下载利润表数据
     * @param code       证券代码
     * @param date       公告日期
     * @param startDate  开始时间
     * @param endDate    结束时间
     * @param period     报告期
     * @param reportType 报告类型
     */
    public String downloadIncomeFromTushare(String code, String date, String startDate, String endDate, String period, String reportType);


    /**
     * 从tushare下载资产负债表
     * @param code       证券代码
     * @param date       公告日期
     * @param startDate  开始时间
     * @param endDate    结束时间
     * @param period     报告期
     * @param reportType 报告类型
     */
    public String downloadBalanceFromTushare(String code, String date, String startDate, String endDate, String period, String reportType);

    /**
     * 从tushare下载自由现金流
     * @param code       证券代码
     * @param date       公告日期
     * @param startDate  开始时间
     * @param endDate    结束时间
     * @param period     报告期
     * @param reportType 报告类型
     */
    public String downloadCashflowFromTushare(String code, String date, String startDate, String endDate, String period, String reportType);


    /**
     * 从tushare下载财务指标数据
     * @param code       证券代码
     * @param date       公告日期
     * @param startDate  开始时间
     * @param endDate    结束时间
     * @param period     报告期
     */
    public String downloadIndicatorFromTushare(String code, String date, String startDate, String endDate, String period);


  /**
     * 从tushare下载主营业务
     * @param code       证券代码
     * @param type       类型：P按产品 D按地区（请输入大写字母P或者D）
     * @param startDate  开始时间
     * @param endDate    结束时间
     * @param period     报告期(每个季度最后一天的日期,比如20171231表示年报)
     */
    public String downloadMainbzFromTushare(String code, String type, String startDate, String endDate, String period);


}
