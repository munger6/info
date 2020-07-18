package com.stock.info.service;


public interface StkStockExcelService {


    void createEarningsExcel(String code, String type, String timeLong);


    /**
     * 创建财务模型excel文件
     * @param code   股票代码
     * @param type   创建模板类型
     * @param timeLong  财务数据时长（默认十年）
     * @return
     */
    boolean createEarningsModeExcel(String code, String type, String timeLong);
}
