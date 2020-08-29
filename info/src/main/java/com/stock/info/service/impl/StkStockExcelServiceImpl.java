package com.stock.info.service.impl;

import com.stock.info.service.StkStockExcelService;
import com.stock.info.service.mode.StockEarningsModeService;
import com.stock.info.service.mode.context.EarningsContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

/**
 * 证券建模Excel写入操作
 */
@Service
@Repository
public class StkStockExcelServiceImpl implements StkStockExcelService {
    private Logger logger = LoggerFactory.getLogger("WARN_FILE");


    @Autowired
    private StockEarningsModeService stockEarningsModeService;


    @Override
    public void createEarningsExcel(String code, String type, String timeLong) {
        //首先实现  创建普通财务报表

        //

    }

    @Override
    public boolean createEarningsModeExcel(String code, String type, String timeLong) {
        //初始化excel创建内容(根据type 获取计算配置类)
        EarningsContext context = EarningsContext.getContext(type,timeLong,code);
        if(context.isCanCreate()){
            return stockEarningsModeService.createModeExcel(context);
        }
        return false;
    }
}

