package com.stock.info.service.mode;

import com.stock.info.domain.entity.StkStockAll;

import java.util.List;

public interface ModeFilterContionStrategy {

    /**
     * 过滤证券代码信息
     * @param stkStockAlls
     * @return
     */
    List<StkStockAll> filter(List<StkStockAll> stkStockAlls);
}
