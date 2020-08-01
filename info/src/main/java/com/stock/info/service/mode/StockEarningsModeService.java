package com.stock.info.service.mode;

import com.stock.info.service.mode.context.EarningsContext;

/**
 * 证券财务模型服务
 */
public interface StockEarningsModeService {


    /**
     * 创建模型excel
     * @param context
     * @return
     */
    boolean createModeExcel(EarningsContext context);





}
