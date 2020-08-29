package com.stock.info.service.mode.impl;


import com.alibaba.fastjson.JSON;
import com.stock.info.Util.DateOperation;
import com.stock.info.Util.ExcelUtil;
import com.stock.info.Util.PublicUtil;
import com.stock.info.Util.StringUtil;
import com.stock.info.constant.PublicConstant;
import com.stock.info.constant.enums.EarningModeTypeEnum;
import com.stock.info.constant.enums.FilterContionTypeEnum;
import com.stock.info.dao.StkStockAllMapper;
import com.stock.info.domain.entity.StkStockAll;
import com.stock.info.domain.queryObject.StkStockAllQueryObject;
import com.stock.info.service.mode.ModeFilterContionStrategy;
import com.stock.info.service.mode.StockEarningsModeService;
import com.stock.info.service.mode.TitleHandler;
import com.stock.info.service.mode.context.EarningsContext;
import com.stock.info.service.mode.context.ExcelLineRule;
import com.stock.info.service.mode.context.IndexMessage;
import com.stock.info.service.mode.context.ModeExcelCreateRule;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.text.ParseException;
import java.util.*;

@Service
@Repository
public class StockEarningsModeServiceImpl implements StockEarningsModeService, InitializingBean , ApplicationContextAware {

    private Logger logger = LoggerFactory.getLogger("WARN_FILE");

    /**容器*/
    private ApplicationContext applicationContext;
    private Map<String, ModeFilterContionStrategy>  strategyMap;


    /** 证券总mapper*/
    @Autowired
    private StkStockAllMapper stkStockAllMapper;

    private Map<String, TitleHandler> titleHandlerMap;

    @Override
    public boolean createModeExcel(EarningsContext context) {
        ModeExcelCreateRule rule = context.getRule();
        //第一步：执行筛选个股信息流程，
        List<StkStockAll> allList = getStockList(context);
        //      如果个股信息不存在则跳过执行，
        if(CollectionUtils.isEmpty(allList)){
            logger.error("createModeExcel 执行失败，查无匹配证券信息，如此那你数据为：" + context);
            return false;
        }

        for (StkStockAll stk :allList){
            try {
                //1、根据个股上市时间确定最新的计算时间(上市需公布之前三年财报数据,在上市日前3年与时间跨度取最近时间)
                Date startDate = getStartDate(rule, stk);
                Date endDate = DateOperation.addMinutes(DateOperation.getStartYear(new Date()),-1);  //当前年份开始（去年年报开始）

                //第三步：数据初始化，查询指定指标数据（eps，bps,年份总股本）
                Map<String,List<String>> indexMap = getIndexMessage(stk, rule, startDate, endDate);

                //第四步： 初始化excel，创建标题信息
                HSSFWorkbook book = ExcelUtil.getWorkbook();
                HSSFSheet sheet = ExcelUtil.createSheetWithName(book,rule.getSheetName());
                TitleHandler handler = titleHandlerMap.get(rule.getTitleHandler());
                if(handler != null){
                    book = handler.createTitle(stk, book, sheet,context,startDate,endDate);
                }

                //第五步：按照行写入数信息；
                writeRuleExcel(book,sheet,rule,indexMap);

                //第六步：导出excel
                ExcelUtil.saveExcelFile(book,
                        String.format("F://mode//%s//%s",rule.getTitleHandler(),
                                DateOperation.formatDate(new Date(),DateOperation.YEAR)),
//                        String.format("//%s_%s.xls",stk.getName(),queryTitleIndex(indexMap, rule)));
                        String.format("//%s.xls",stk.getName()));
            } catch (ParseException e) {
                logger.error(String.format("创建模式失败,证券名:%s",stk.getName()),e);
            }
        }
        return true;
    }

    protected String queryTitleIndex(Map<String, List<String>> indexMap, ModeExcelCreateRule rule) {
        return "";
    }

    /**
     * 写入规则excel
     * @param book
     * @param rule
     * @param indexMap
     */
    private String writeRuleExcel(HSSFWorkbook book, HSSFSheet sheet, ModeExcelCreateRule rule, Map<String, List<String>> indexMap) {
        List<ExcelLineRule> lineRules = rule.getLineRules();
        ExcelLineRule lineRule;
        List<String> data;
        for (int i = 0; i < lineRules.size(); i++) {
            data = new ArrayList<>();
            lineRule = lineRules.get(i);
            if("index".equals(lineRule.getType())){
                data.addAll(indexMap.get(lineRule.getValue()));
                ExcelUtil.createRow(book,sheet,lineRule, data,"", rule.getStartLine() + i);
            }else if("expression".equals(lineRule.getType())){
                //根据指标计算行 按照指定的表达式写入数据；
                data.addAll(converExpression(lineRule, indexMap));
                ExcelUtil.createRow(book,sheet,lineRule,data,lineRule.getType(), rule.getStartLine() + i);
            }else{
                logger.warn("暂不支持该行数据操作类型");
            }
        }
        return "excel文件名";
    }


    /**
     *  转换表达式
     * @param  lineRule   行规则
     * @param  indexMap   指标map集合
     * @return
     */
    private List<String> converExpression(ExcelLineRule lineRule,  Map<String, List<String>>  indexMap) {
        String expersion = lineRule.getValue();
        List<String> variableParam = lineRule.getVariableParam();
        int length = lineRule.getMaxLineLength();
        List<String> result = new ArrayList<>();

        if(!CollectionUtils.isEmpty(variableParam)){
            String tempExpression;
            for (int i = 0; i < length; i++) {
                tempExpression = "";
                Map<String,String> tempVariable = new HashMap<>();
                for (String value : variableParam) {
                    if(StringUtils.isNotBlank(value)){
                        tempVariable.put("param_" + value, StringUtil.addAsciiCode(value,i));
                        tempExpression = StringUtil.replaceAll(expersion,tempVariable);
                    }
                }
                result.add(tempExpression);
            }
        }
        return result;
    }


    /**
     * 查询指标信息
     * @param stk   证券代码
     * @param rule
     * @param startDate
     * @param endDate
     * @return
     */
    private Map<String, List<String>> getIndexMessage(StkStockAll stk, ModeExcelCreateRule rule, Date startDate, Date endDate) {
        Map<String,List<String>> indexMap = new HashMap<>();
        List<IndexMessage> indexList = rule.getIndexList();
        if(!CollectionUtils.isEmpty(indexList)){
            List<String> data ;
            List<Map<String,Object>> result;
            Map<String, Object> param;
            for (IndexMessage indexMessage : indexList){
                List<String> dateList = getAllDate(indexMessage, startDate, endDate);
                if(!CollectionUtils.isEmpty(dateList)){
                    data = new ArrayList<>();
                    param = new HashMap<>();
                    param.put("tsCode",stk.getTsCode());
                    param.put("indexColumnName",indexMessage.getIndexColumnName());
                    param.put("indexTable", indexMessage.getIndexTable());
                    param.put("dateList",dateList);
//                  param.put("contion",);  此处暂时不用条件入参
                    result = stkStockAllMapper.selectIndexMessage(param);
                    Map<String,Map<String,Object>> resultMap = PublicUtil.converListToMap(result,"indexDate");
                    for (int i = 0; i < dateList.size(); i++) {
                        if(resultMap.containsKey(dateList.get(i))){
                            if(indexMessage.isUnitRate()){
                                data.add(PublicUtil.formatNumberStr(MapUtils.getString(resultMap.get(dateList.get(i)),"indexData"),
                                        rule.getUnit()));
                            }else {
                                data.add(PublicUtil.formatNumberStr(MapUtils.getString(resultMap.get(dateList.get(i)),"indexData"),1));
                            }
                        }else {
                            data.add("");
                            logger.error(String.format("查询指标数据不存在，指标规则%s,指标日期：%s",indexMessage,dateList.get(i)));
                        }
                    }
                }else{
                    data = new ArrayList<>();
                    logger.warn(String.format("【模型引擎步骤2】查询依赖指标%s不存在,时间集合为%s",
                            indexMessage.getIndexCode(), JSON.toJSONString(dateList) ));
                    Assert.isTrue(true,String.format("【模型引擎步骤2】查询依赖指标%s不存在,时间集合为%s",
                            indexMessage.getIndexCode(), JSON.toJSONString(dateList) ));
                }
                indexMap.put(indexMessage.getIndexCode(),data);
            }
        }
        return indexMap;
    }


    /**
     *
     * @param indexMessage
     * @param startDate
     * @param endDate
     * @return
     */
    private List<String> getAllDate(IndexMessage indexMessage, Date startDate, Date endDate) {
        List<String> dateList = new ArrayList<>();
        String period = indexMessage.getPeriod();
        String year ="";
        for (int i = 0; endDate.after(startDate); i++) {
            year = DateOperation.formatDate(startDate,DateOperation.YEAR);
            if(PublicConstant.YEAR.equals(period)){
                dateList.add(year + "1231");
            }else if(PublicConstant.QUARTER.equals(period)){
                dateList.add(year + "0331");
                dateList.add(year + "0630");
                dateList.add(year + "0930");
                dateList.add(year + "1231");
            }
            startDate = DateOperation.addYears(startDate,1);
        }
        return dateList;
    }



    /**
     * 获取证券计算开始时间
     * @param rule  计算规则
     * @param stk   证券代码
     * @return
     */
    private Date getStartDate(ModeExcelCreateRule rule, StkStockAll stk) throws ParseException {
        Date startDate = DateOperation.addYears(new Date(), -1 - rule.getDataTimeLong() );
        Date date = DateOperation.getStartYear(
                DateOperation.addYears(DateOperation.parseDate(stk.getListDate(),DateOperation.DAY),-3));
        return startDate.before(date) ? date : startDate;
    }


    /**
     * 获取证券集合（当存在证券过滤条件，则按照过滤条件获取的证券来处理，
     *                  可以做多层过滤，取所有过滤证券的交集）
     * @param context  内容对象
     * @return
     */
    private List<StkStockAll> getStockList(EarningsContext context) {
        List<StkStockAll> allList = new ArrayList<>();
        ModeExcelCreateRule rule = context.getRule();
        String code = context.getTsCode();
        StkStockAllQueryObject queryObject = new StkStockAllQueryObject();
        if(StringUtils.isNotBlank(code)){
            queryObject.setTsCodeList(Arrays.asList(code.split(PublicConstant.COMMA)));
        }
        allList = stkStockAllMapper.selectAllByContion(queryObject);
        if(!CollectionUtils.isEmpty(rule.getStockFilterConditions())){
            for (Map<String,Object> param:rule.getStockFilterConditions()){
                ModeFilterContionStrategy contionStrategy = strategyMap.get(MapUtils.getString(param,"index"));
                if(contionStrategy != null){
                    allList = contionStrategy.filter(allList,param);
                }
            }
        }
        return allList;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        strategyMap = new HashMap<>();
        strategyMap.put(FilterContionTypeEnum.ROE_AVG_TEN.getCode(),
                (ModeFilterContionStrategy)applicationContext.getBean("roeAvgFilterContionStrategy"));
        strategyMap.put(FilterContionTypeEnum.LISTING_TIME_TOTAL.getCode(),
                (ModeFilterContionStrategy)applicationContext.getBean("listingTimeFilterContionStrategy"));
        strategyMap.put(FilterContionTypeEnum.MARKET.getCode(),
                (ModeFilterContionStrategy)applicationContext.getBean("marketFilterContionStrategy"));

        titleHandlerMap = new HashMap<>();
        titleHandlerMap.put(EarningModeTypeEnum.TAO_MODE.getCode(),(TitleHandler)applicationContext.getBean("taoTaoTitleHandler"));
    }


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
