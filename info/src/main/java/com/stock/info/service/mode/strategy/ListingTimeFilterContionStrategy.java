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
 * 上市条件过滤策略
 *      当上市时间与当前时间的差值 比配置值 满足配置公式则进行计算
 *      例如 涛涛模型：    >= 7 年   上市7年 + 上市前3年财报 = 10年；
 */
@Service
@Repository
public class ListingTimeFilterContionStrategy implements ModeFilterContionStrategy {

    @Override
    public List<StkStockAll> filter(List<StkStockAll> stkStockAlls, Map<String, Object> param) {
        List<StkStockAll> result = new ArrayList<>();
        String type = MapUtils.getString(param,comparType);
        int value = MapUtils.getInteger(param,comparValue);
        String year = DateOperation.formatDate(new Date(),DateOperation.YEAR);
        BigDecimal now = new BigDecimal(year);
        StkStockAll stkStockAll;
        String listYear;
        for (int i = 0; i < stkStockAlls.size(); i++) {
            stkStockAll = stkStockAlls.get(i);
            listYear = stkStockAll.getListDate().substring(0,4);
            if(ComparUtil.compar(type, now.subtract(new BigDecimal(listYear)).intValue() ,value)){
                result.add(stkStockAll);
            }
        }
        return result;
    }
}