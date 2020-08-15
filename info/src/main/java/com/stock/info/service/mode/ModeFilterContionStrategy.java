package com.stock.info.service.mode;

import com.stock.info.domain.entity.StkStockAll;

import java.util.List;
import java.util.Map;

/**
 * 模型计算过滤证券条件策略
 */
public interface ModeFilterContionStrategy {

    String comparType = "comparType";

    String comparValue = "comparValue";

    /**
     * 过滤证券代码信息
     * @param stkStockAlls   证券集合
     * @param param   参数条件之
     * @return
     */
    List<StkStockAll> filter(List<StkStockAll> stkStockAlls , Map<String,Object> param);
}
