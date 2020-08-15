package com.stock.info.service.mode.strategy;

import com.stock.info.Util.ComparUtil;
import com.stock.info.Util.DateOperation;
import com.stock.info.domain.entity.StkStockAll;
import com.stock.info.service.mode.ModeFilterContionStrategy;
import org.apache.commons.collections.MapUtils;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 市场过滤条件策略
 *      市场过滤条件，市场包含其中还是不包含；
 */
@Service
@Repository
public class MarketFilterContionStrategy implements ModeFilterContionStrategy {

    @Override
    public List<StkStockAll> filter(List<StkStockAll> stkStockAlls, Map<String, Object> param) {
        List<StkStockAll> result = new ArrayList<>();
        String type = MapUtils.getString(param,comparType);
        String value = MapUtils.getString(param,comparValue);
        StkStockAll stkStockAll;
        for (int i = 0; i < stkStockAlls.size(); i++) {
            stkStockAll = stkStockAlls.get(i);
            if("!".equals(type) && !value.contains("," + stkStockAll.getMarket() + ",")){
                result.add(stkStockAll);
            }else if("&".equals(type) && value.contains("," + stkStockAll.getMarket() + ",")){
                result.add(stkStockAll);
            }
        }
        return result;
    }
}