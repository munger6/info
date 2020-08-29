package com.stock.info.service.mode.strategy;

import com.stock.info.Util.ComparUtil;
import com.stock.info.Util.DateOperation;
import com.stock.info.Util.PublicUtil;
import com.stock.info.dao.StkFinanceIndicatorMapper;
import com.stock.info.domain.entity.StkStockAll;
import com.stock.info.domain.pojo.StkIndexValuePojo;
import com.stock.info.domain.queryObject.StkStockAllQueryObject;
import com.stock.info.service.mode.ModeFilterContionStrategy;
import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 年均roe 过滤条件策略
 *      上市公司平均市净率指标计算()
 */
@Service
@Repository
public class RoeAvgFilterContionStrategy implements ModeFilterContionStrategy {

    @Autowired
    private StkFinanceIndicatorMapper stkFinanceIndicatorMapper;

    @Override
    public List<StkStockAll> filter(List<StkStockAll> stkStockAlls, Map<String, Object> param) {
        List<StkStockAll> result = new ArrayList<>();
        String type = MapUtils.getString(param,comparType);
        double value = MapUtils.getInteger(param,comparValue);
        StkStockAll stkStockAll;
        StkStockAllQueryObject queryObject = new StkStockAllQueryObject();
        if(stkStockAlls.size() < 1000){
            queryObject.setTsCodeList(PublicUtil.converListToListStr(stkStockAlls, "tsCode"));
        }
        List<StkIndexValuePojo> stkIndexValuePojos = stkFinanceIndicatorMapper.selectRoeAvg(queryObject);
        Map<String, StkIndexValuePojo> valuePojoMap = PublicUtil.converListToMap(stkIndexValuePojos,"tsCode");
        for (int i = 0; i < stkStockAlls.size(); i++) {
            stkStockAll = stkStockAlls.get(i);
            if(valuePojoMap.containsKey(stkStockAll.getTsCode())
                    && ComparUtil.compar(type,valuePojoMap.get(stkStockAll.getTsCode()).getIndexValue(),value)){
                result.add(stkStockAll);
            }
        }
        return result;
    }
}
