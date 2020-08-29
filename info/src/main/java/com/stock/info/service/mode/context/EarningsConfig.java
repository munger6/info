package com.stock.info.service.mode.context;


import com.stock.info.Util.ComparUtil;
import com.stock.info.constant.PublicConstant;
import com.stock.info.constant.enums.*;

import java.util.*;

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
            condition.put("comparType", ComparUtil.ge);
            condition.put("comparValue",7);
            stockFilterConditions.add(condition);
            condition = new HashMap<>();
            condition.put("index", FilterContionTypeEnum.ROE_AVG_TEN.getCode());
            condition.put("comparType",ComparUtil.ge);
            condition.put("comparValue",15);
            stockFilterConditions.add(condition);

            //指标信息汇总(年净利润  、 年净资产)
            List<IndexMessage> indexList = new ArrayList<>();
            indexList.add(new IndexMessage(IndexMessageEnum.INCOME, true));
            indexList.add(new IndexMessage(IndexMessageEnum.EQUITY,true));
            indexList.add(new IndexMessage(IndexMessageEnum.ROE,false));
            indexList.add(new IndexMessage(IndexMessageEnum.TOTAL_SHARE,true));

            /**行信息汇总 line5- 净利润；  line6-总股本；   line7-净资产；   line8-ROE（加权平均）；  line9-空白***/
            /**扩展行规则：  增速形式，  复制方式，*/
            List<ExcelLineRule> lineRules = new ArrayList<>();
            Map<String,Object> futureCompuateMap = new HashMap<>();
            futureCompuateMap.put("speed",1.1);
            ExtendLineRule extendLineRule = new ExtendLineRule(3, FutureTypeEnum.SPEED.getCode(),futureCompuateMap);
            lineRules.add(new ExcelLineRule("净利润", ExcelLineTypeEnum.INDEX.getCode(),IndexMessageEnum.INCOME.getCode(),null,extendLineRule));

            futureCompuateMap = new HashMap<>();
            futureCompuateMap.put("speed",1);
            extendLineRule = new ExtendLineRule(3, FutureTypeEnum.SPEED.getCode(),futureCompuateMap);
            lineRules.add(new ExcelLineRule("总股本", ExcelLineTypeEnum.INDEX.getCode(),IndexMessageEnum.TOTAL_SHARE.getCode(),null,extendLineRule));

            //  净资产增加扩展行表达式： 前一年净资产 + 当年净利润
            futureCompuateMap = new HashMap<>();
            futureCompuateMap.put( ExcelLineTypeEnum.EXPRESSION.getCode(),"{param_L}7 + {param_M}5");
            futureCompuateMap.put("index", Arrays.asList("M,L".split(PublicConstant.COMMA)));
            extendLineRule = new ExtendLineRule(3, FutureTypeEnum.EXPRESSION.getCode(),futureCompuateMap);
            lineRules.add(new ExcelLineRule("净资产", ExcelLineTypeEnum.INDEX.getCode(),IndexMessageEnum.EQUITY.getCode(),null,extendLineRule));

            //  ROE(加权平均)增加扩展行表达式： 当年净利润 / 前一年净资产
            futureCompuateMap = new HashMap<>();
            futureCompuateMap.put( ExcelLineTypeEnum.EXPRESSION.getCode(),"100 * {param_M}5/{param_L}7");
            futureCompuateMap.put("index", Arrays.asList("M,L".split(PublicConstant.COMMA)));
            extendLineRule = new ExtendLineRule(3, FutureTypeEnum.EXPRESSION.getCode(),futureCompuateMap);
            lineRules.add(new ExcelLineRule("ROE(加权平均)", ExcelLineTypeEnum.INDEX.getCode(),IndexMessageEnum.ROE.getCode(),null,extendLineRule));

            /*******************************************空行换行***********************************************/
            lineRules.add(new ExcelLineRule("", ExcelLineTypeEnum.BLANK.getCode(),"",null));

            // line10 - RE
            ExcelLineRule lineRule = new ExcelLineRule("RE", ExcelLineTypeEnum.EXPRESSION.getCode(),"{param_B}7*(({param_C}8/100)-$C$2)",true);
            List<String> variableParam = new ArrayList<>();
            variableParam.add("B");
            variableParam.add("C");
            lineRule.setVariableParam(variableParam);
            lineRule.setMaxLineLength(13);
            lineRules.add(lineRule);

            // line11 - 折现因子
            lineRule = new ExcelLineRule("折现因子", ExcelLineTypeEnum.EXPRESSION.getCode(),"(1+$C$2)^{param_1}",true);
            variableParam = new ArrayList<>();
            variableParam.add("1");
            lineRule.setVariableParam(variableParam);
            lineRule.setMaxLineLength(13);
            lineRules.add(lineRule);

             //line12 - re现值折现
            lineRule = new ExcelLineRule("RE现值", ExcelLineTypeEnum.EXPRESSION.getCode(),"{param_C}10/{param_C}11",true);
            variableParam = new ArrayList<>();
            variableParam.add("C");
            lineRule.setVariableParam(variableParam);
            lineRule.setMaxLineLength(13);
            lineRules.add(lineRule);

           //line13 - re总现值 = sum（RE现值）* (1+ 资本成本)^ n
            lineRule = new ExcelLineRule("RE总现值", ExcelLineTypeEnum.EXPRESSION.getCode(),"SUM({param_D}12:$K12)*(1+$C$2)^{param_1}",true);
            variableParam = new ArrayList<>();
            variableParam.add("D");
            variableParam.add("1");
            lineRule.setVariableParam(variableParam);
            lineRule.setMaxLineLength(13);
            lineRules.add(lineRule);

           //line14 - 持续价值 N10*(1+G2)/(C2-G2)      RE * （1 + 增速）/ （资本回报率 - 增速）
            lineRule = new ExcelLineRule("持续价值", ExcelLineTypeEnum.EXPRESSION.getCode(),"{param_C}10*(1+$G$2)/($C$2-$G$2)",true);
            variableParam = new ArrayList<>();
            variableParam.add("C");
            lineRule.setVariableParam(variableParam);
            lineRule.setMaxLineLength(13);
            lineRules.add(lineRule);

            lineRules.add(new ExcelLineRule("", ExcelLineTypeEnum.BLANK.getCode(),"",1));
            //line15 - 当前价值 =   永续价值  +  未来五年的折现  +  净资产 + 当年利润 -当年分红 + 5年剩余价值
            lineRule = new ExcelLineRule("总价值", ExcelLineTypeEnum.EXPRESSION.getCode(),"({param_C}7 + {param_C}13 + {param_C}14)/{param_C}6",true);
            variableParam = new ArrayList<>();
            variableParam.add("C");
            lineRule.setVariableParam(variableParam);
            lineRule.setMaxLineLength(13);
            lineRules.add(lineRule);

//            lineRules.add(new ExcelLineRule("年末股价", ExcelLineTypeEnum.INDEX.getCode(),IndexMessageEnum.PRICE_M.getCode(),10));

            ModeExcelCreateRule taotaoModeRule = new ModeExcelCreateRule(stockFilterConditions,indexList,lineRules );
            taotaoModeRule.setDataTimeLong(10);
            taotaoModeRule.setSheetName("估值表（5年）");
            taotaoModeRule.setStartLine(4);
            taotaoModeRule.setTitleHandler("taotaoMode");
            taotaoModeRule.setForecastTimeLong(3);
            taotaoModeRule.setUnit(1<<1);  //万元
            ruleMap.put(EarningModeTypeEnum.TAO_MODE.getCode(), taotaoModeRule);
        }




    }

    public static ModeExcelCreateRule getRuleByType(String type) {
        return ruleMap.get(type);
    }
}
