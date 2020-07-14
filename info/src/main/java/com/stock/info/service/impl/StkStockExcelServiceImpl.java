package com.stock.info.service.impl;

import com.stock.info.service.StkStockExcelService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Service
@Repository
public class StkStockExcelServiceImpl implements StkStockExcelService {
    @jdk.nashorn.internal.runtime.logging.Logger
    private Logger logger = LoggerFactory.getLogger("WARN_FILE");


    @Override
    public void createEarningsExcel(String code, String type, String timeLong) {
        //首先实现  创建普通财务报表

        //

    }
}

