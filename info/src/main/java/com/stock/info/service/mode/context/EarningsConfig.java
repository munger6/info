package com.stock.info.service.mode.context;


import com.stock.info.constant.enums.ExcelLineTypeEnum;
import com.stock.info.constant.enums.FilterContionTypeEnum;
import com.stock.info.constant.enums.IndexMessageEnum;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 财报配置类
 */
public class EarningsConfig {

    private static Map<String, ModeExcelCreateRule> ruleMap;

    private EarningsConfig() {
    }

    static {
        ruleMap = new HashMap<String, ModeExcelCreateRule>();
        {
            /**涛涛现金流折现估值模型： 数据追溯5年，  后面预测3年，加工数据行开始在第5行**/
            //证券筛选：条件限制：  上市超十年   roe年均 > 15%
            List<Map<String, Object>> stockFilterConditions = new ArrayList<>();
            Map<String, Object> condition = new HashMap<>();
            condition.put("index", FilterContionTypeEnum.LISTING_TIME_TOTAL.getCode());
            condition.put("comparFormula","{index} >= 10");
            stockFilterConditions.add(condition);
            condition = new HashMap<>();
            condition.put("index", FilterContionTypeEnum.ROE_AVG_TEN.getCode());
            condition.put("comparFormula","{index} >= 15");
            stockFilterConditions.add(condition);

            //指标信息汇总(年净利润  、 年净资产)
            List<IndexMessage> indexList = new ArrayList<>();
            indexList.add(new IndexMessage(IndexMessageEnum.INCOME));
            indexList.add(new IndexMessage(IndexMessageEnum.EQUITY));
            indexList.add(new IndexMessage(IndexMessageEnum.ROE));

            //行信息汇总
            List<ExcelLineRule> lineRules = new ArrayList<>();
            lineRules.add(new ExcelLineRule("净利润", ExcelLineTypeEnum.INDEX.getCode(),IndexMessageEnum.INCOME.getCode()));
            lineRules.add(new ExcelLineRule("净资产", ExcelLineTypeEnum.INDEX.getCode(),IndexMessageEnum.EQUITY.getCode()));
            lineRules.add(new ExcelLineRule("ROE(加权平均)", ExcelLineTypeEnum.INDEX.getCode(),IndexMessageEnum.ROE.getCode()));



            ModeExcelCreateRule taotaoModeRule = new ModeExcelCreateRule(stockFilterConditions,indexList,lineRules );
            taotaoModeRule.setDataTimeLong(5);
            taotaoModeRule.setSheetName("估值表（5年）");
            taotaoModeRule.setStartLine(5);
            taotaoModeRule.setTitleHandler("taotaoMode");
            taotaoModeRule.setForecastTimeLong(3);
            ruleMap.put("taotao", taotaoModeRule);
        }




    }

    public static ModeExcelCreateRule getRuleByType(String type) {
        return ruleMap.get(type);
    }
}
