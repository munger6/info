package com.stock.info.service.mode.context;


import java.util.HashMap;
import java.util.Map;

/**
 * 财报配置类
 */
public class EarningsConfig {

    private static Map<String, ModeExcelCreateRule> ruleMap;

    static {
        ruleMap = new HashMap<String, ModeExcelCreateRule>();


    }

    public static ModeExcelCreateRule getRuleByType(String type) {
        return ruleMap.get(type);
    }
}
