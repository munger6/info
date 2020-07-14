package com.stock.info.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.stock.info.Util.BeanUtil;
import com.stock.info.Util.HttpUtil;
import com.stock.info.Util.StringUtil;
import com.stock.info.dao.*;
import com.stock.info.domain.StkStockDO;
import com.stock.info.domain.entity.*;
import com.stock.info.service.StkStockEarningsService;
import org.apache.commons.collections.MapUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Repository
public class StkStockEarningsServiceImpl implements StkStockEarningsService {

    @jdk.nashorn.internal.runtime.logging.Logger
    private Logger logger = LoggerFactory.getLogger("WARN_FILE");

    @Autowired
    private StockMapper stockMapper;

    @Autowired
    private StkFinanceIncomeMapper stkFinanceIncomeMapper;

    @Autowired
    private StkFinanceBanlancesheetMapper stkFinanceBanlancesheetMapper;

    @Autowired
    private StkFinanceCashflowMapper stkFinanceCashflowMapper;

    @Autowired
    private StkFinanceIndicatorMapper stkFinanceIndicatorMapper;

    @Autowired
    private StkFinanceMainbzMapper stkFinanceMainbzMapper;

    @Autowired
    private StkStockAllMapper stkStockAllMapper;

    //证券基础表
    protected static String testApiName = "stock_basic";
    //利润表
    protected static String apiIncome = "income";
    //资产负债表
    protected static String apiBalance = "balancesheet";
    //资产负债表
    protected static String apiCashflow = "cashflow";

    @Override
    public Map<String, Object> query() {
        StkStockDO stkStockDO = stockMapper.query();
        return BeanUtil.convertToMap(stkStockDO);
    }

    @Override
    public Map<String, Object> queryStockListByContion(String isHs, String listStatus, String exchange) {
        Map<String, Object> paramMap = HttpUtil.initParam(testApiName);
        Map<String,Object> params = new HashMap<>();
        params.put("is_hs",isHs);
        params.put("list_stauts",listStatus);
        params.put("exchange",exchange);
        paramMap.put("params",params);
        String result = HttpUtil.doPost(HttpUtil.urlLinke,paramMap,null);
        return JSON.parseObject(result);
    }


    //本接口仅适用于第一次全量数据初始化下载（）
    @Override
    public void downloadFinanceFromTushare(String code) {
        try {
            if(StringUtils.isEmpty(code)){
                List<StkStockAll> stockAlls = stkStockAllMapper.selectAll();
                stockAlls.stream().forEach(stkStockAll->{
                    String code1 =  stkStockAll.getTsCode();
                    downloadBalanceFromTushare(code1,"","","","","");
                    //会有重复数据很奇怪
                    downloadIncomeFromTushare(code1,"","","","","");
                    downloadCashflowFromTushare(code1,"","","","","");
                    downloadIndicatorFromTushare(code1,"","","","");
                    downloadMainbzFromTushare(code1,"","","","");
                });
            }else{
                downloadBalanceFromTushare(code,"","","","","");
                //会有重复数据很奇怪
                downloadIncomeFromTushare(code,"","","","","");
                downloadCashflowFromTushare(code,"","","","","");
                downloadIndicatorFromTushare(code,"","","","");
                downloadMainbzFromTushare(code,"","","","");
            }
        } catch (Exception e) {
            logger.warn("加载证券失败，证券代码：" + code,  e);
        }
    }


    /**
     *   导入所有证券
     * @param isHs    是否沪深港通标的，N否 H沪股通 S深股通
     * @param listStatus   上市状态： L上市 D退市 P暂停上市，默认L
     * @param exchange    交易所 SSE上交所 SZSE深交所 HKEX港交所(未上线)
     */
    @Override
    @Transactional(rollbackFor = {RuntimeException.class, Error.class})
    public String downloadStockListFromTushare(String isHs, String listStatus, String exchange) {
        Map<String, Object> paramMap = HttpUtil.initParam(testApiName);
        Map<String,Object> params = new HashMap<>();
        params.put("is_hs",isHs);
        params.put("list_stauts",listStatus);
        params.put("exchange",exchange);
        paramMap.put("params",params);
        String result = HttpUtil.doPost(HttpUtil.urlLinke,paramMap,null);
        if(!StringUtils.isEmpty(result)){
            JSONObject incomeData = JSON.parseObject(result);
            if(incomeData != null && incomeData.containsKey("data")){
                JSONArray array = incomeData.getJSONObject("data").getJSONArray("fields");
                for (int i = 0; i < array.size(); i++) {
                    array.set(i, StringUtil.convertSqlCodeToHump(array.getString(i)));
                }
                JSONArray items = incomeData.getJSONObject("data").getJSONArray("items");
                List<StkStockAll> incomes = new ArrayList<>(items.size());
                JSONArray data;
                Map<String, Object> dataMap;
                StkStockAll stkStockAll;
                StkStockAll stkStockAll1 = new StkStockAll();
                if(!StringUtils.isEmpty(exchange)){
                    stkStockAll1.setExchange(exchange);
                }
                stkStockAllMapper.deleteAll(stkStockAll1);
                for (int i = 0; i < items.size(); i++) {
                    data = items.getJSONArray(i);
                    dataMap = HttpUtil.processDataToMap(data, array);
                    if(MapUtils.isNotEmpty(dataMap)){
                        stkStockAll = BeanUtil.mapToBean(dataMap, StkStockAll.class);
                        if(stkStockAll != null){
                            incomes.add(stkStockAll);
                            stkStockAllMapper.insert(stkStockAll);
                        }
                    }
                }
            }
        }
        return result;
    }


    /**
     *   导入利润表数据
     *   todo  外层改造为增加 startDate 形式，并且按照查询回来的证券代码循环遍历插入数据
     * @param code       证券代码
     * @param date       公告日期
     * @param startDate  开始时间
     * @param endDate    结束时间
     * @param period     报告期
     * @param reportType 报告类型
     * @return
     */
    @Override
    @Transactional(rollbackFor = {RuntimeException.class, Error.class})
    public String downloadIncomeFromTushare(String code, String date, String startDate, String endDate, String period, String reportType) {
        Map<String, Object> paramMap = HttpUtil.initParam(apiIncome);
        Map<String,Object> params = new HashMap<>();
        params.put("ts_code",code);
        params.put("ann_date",date);
        params.put("start_date",startDate);
        params.put("end_date",endDate);
        params.put("period",period);
        params.put("report_type",reportType);
        paramMap.put("params",params);
        logger.warn("#####财报同步####downloadIncomeFromTushare（）查询条件" + paramMap);
        String result = HttpUtil.doPost(HttpUtil.urlLinke,paramMap,null);
        logger.warn("#####财报同步####downloadIncomeFromTushare（）查询结果" + result);
        if(!StringUtils.isEmpty(result)){
            JSONObject incomeData = JSON.parseObject(result);
            if(incomeData != null && incomeData.containsKey("data")){
                JSONArray array = incomeData.getJSONObject("data").getJSONArray("fields");
                for (int i = 0; i < array.size(); i++) {
                    array.set(i, StringUtil.convertSqlCodeToHump(array.getString(i)));
                }
                JSONArray items = incomeData.getJSONObject("data").getJSONArray("items");
                List<StkFinanceIncome> incomes = new ArrayList<>(items.size());
                JSONArray data;
                Map<String, Object> dataMap;
                StkFinanceIncome income;
                stkFinanceIncomeMapper.deleteByPrimaryKey(code);
                for (int i = 0; i < items.size(); i++) {
                    data = items.getJSONArray(i);
                    dataMap = HttpUtil.processDataToMap(data, array);
                    if(MapUtils.isNotEmpty(dataMap)){
                        income = BeanUtil.mapToBean(dataMap, StkFinanceIncome.class);
                        if(income != null){
                            incomes.add(income);
                            income.setUpdateFlag("0");
                            stkFinanceIncomeMapper.insert(income);
                        }
                    }
                }
            }
        }
        return result;
    }


    /**
     *   导入资产负债表数据
     * @param code       证券代码
     * @param date       公告日期
     * @param startDate  开始时间
     * @param endDate    结束时间
     * @param period     报告期
     * @param reportType 报告类型
     * @return
     */
    @Override
    @Transactional(rollbackFor = {RuntimeException.class, Error.class})
    public String downloadBalanceFromTushare(String code, String date, String startDate, String endDate, String period, String reportType) {
        Map<String, Object> paramMap = HttpUtil.initParam(apiBalance);
        Map<String,Object> params = new HashMap<>();
        params.put("ts_code",code);
        params.put("ann_date",date);
        params.put("start_date",startDate);
        params.put("end_date",endDate);
        params.put("period",period);
        params.put("report_type",reportType);
        paramMap.put("params",params);
        logger.warn("#####财报同步####downloadBalanceFromTushare（）查询条件" + paramMap);
        String result = HttpUtil.doPost(HttpUtil.urlLinke,paramMap,null);
        logger.warn("#####财报同步####downloadBalanceFromTushare（）查询结果" + result);
        if(!StringUtils.isEmpty(result)){
            JSONObject incomeData = JSON.parseObject(result);
            if(incomeData != null && incomeData.containsKey("data")){
                JSONArray array = incomeData.getJSONObject("data").getJSONArray("fields");
                for (int i = 0; i < array.size(); i++) {
                    array.set(i, StringUtil.convertSqlCodeToHump(array.getString(i)));
                }
                JSONArray items = incomeData.getJSONObject("data").getJSONArray("items");
                List<StkFinanceBanlancesheet> incomes = new ArrayList<>(items.size());
                JSONArray data;
                Map<String, Object> dataMap;
                StkFinanceBanlancesheet banlancesheet;
                stkFinanceBanlancesheetMapper.deleteByCode(code);
                for (int i = 0; i < items.size(); i++) {
                    data = items.getJSONArray(i);
                    dataMap = HttpUtil.processDataToMap(data, array);
                    if(MapUtils.isNotEmpty(dataMap)){
                        banlancesheet = BeanUtil.mapToBean(dataMap,StkFinanceBanlancesheet.class);
                        if(banlancesheet != null){
                            incomes.add(banlancesheet);
                            banlancesheet.setUpdateFlag("0");
                            stkFinanceBanlancesheetMapper.insert(banlancesheet);
                        }
                    }
                }
            }
        }
        return result;
    }

    /**
     *   导入现金流量表数据
     * @param code       证券代码
     * @param date       公告日期
     * @param startDate  开始时间
     * @param endDate    结束时间
     * @param period     报告期
     * @param reportType 报告类型
     * @return
     */
    @Override
    @Transactional(rollbackFor = {RuntimeException.class, Error.class})
    public String downloadCashflowFromTushare(String code, String date, String startDate, String endDate, String period, String reportType) {
        Map<String, Object> paramMap = HttpUtil.initParam(apiCashflow);
        Map<String,Object> params = new HashMap<>();
        params.put("ts_code",code);
        params.put("ann_date",date);
        params.put("start_date",startDate);
        params.put("end_date",endDate);
        params.put("period",period);
        params.put("report_type",reportType);
        paramMap.put("params",params);
        logger.warn("#####财报同步####downloadBalanceFromTushare（）查询条件" + paramMap);
        String result = HttpUtil.doPost(HttpUtil.urlLinke,paramMap,null);
        logger.warn("#####财报同步####downloadBalanceFromTushare（）查询结果" + result);
        if(!StringUtils.isEmpty(result)){
            JSONObject incomeData = JSON.parseObject(result);
            if(incomeData != null && incomeData.containsKey("data")){
                JSONArray array = incomeData.getJSONObject("data").getJSONArray("fields");
                for (int i = 0; i < array.size(); i++) {
                    array.set(i, StringUtil.convertSqlCodeToHump(array.getString(i)));
                }
                JSONArray items = incomeData.getJSONObject("data").getJSONArray("items");
                List<StkFinanceCashflow> incomes = new ArrayList<>(items.size());
                JSONArray data;
                Map<String, Object> dataMap;
                StkFinanceCashflow cashflow;
                stkFinanceCashflowMapper.deleteByCode(code);
                for (int i = 0; i < items.size(); i++) {
                    data = items.getJSONArray(i);
                    dataMap = HttpUtil.processDataToMap(data, array);
                    if(MapUtils.isNotEmpty(dataMap)){
                        cashflow = BeanUtil.mapToBean(dataMap,StkFinanceCashflow.class);
                        if(cashflow != null){
                            incomes.add(cashflow);
                            cashflow.setUpdateFlag("0");
                            stkFinanceCashflowMapper.insert(cashflow);
                        }
                    }
                }
            }
        }
        return result;
    }


    @Override
    @Transactional(rollbackFor = {RuntimeException.class, Error.class})
    public String downloadIndicatorFromTushare(String code, String date, String startDate, String endDate, String period) {
        Map<String, Object> paramMap = HttpUtil.initParam("fina_indicator");
        Map<String,Object> params = new HashMap<>();
        params.put("ts_code",code);
        params.put("ann_date",date);
        params.put("start_date",startDate);
        params.put("end_date",endDate);
        params.put("period",period);
        paramMap.put("params",params);
        logger.warn("#####财报同步####downloadBalanceFromTushare（）查询条件" + paramMap);
        String result = HttpUtil.doPost(HttpUtil.urlLinke,paramMap,null);
        logger.warn("#####财报同步####downloadBalanceFromTushare（）查询结果" + result);
        if(!StringUtils.isEmpty(result)){
            JSONObject incomeData = JSON.parseObject(result);
            if(incomeData != null && incomeData.containsKey("data")){
                JSONArray array = incomeData.getJSONObject("data").getJSONArray("fields");
                for (int i = 0; i < array.size(); i++) {
                    array.set(i, StringUtil.convertSqlCodeToHump(array.getString(i)));
                }
                JSONArray items = incomeData.getJSONObject("data").getJSONArray("items");
                List<StkFinanceIndicator> indicators = new ArrayList<>(items.size());
                JSONArray data;
                Map<String, Object> dataMap;
                StkFinanceIndicator indicator;
                stkFinanceIndicatorMapper.deleteByCode(code);
                for (int i = 0; i < items.size(); i++) {
                    data = items.getJSONArray(i);
                    dataMap = HttpUtil.processDataToMap(data, array);
                    if(MapUtils.isNotEmpty(dataMap)){
                        indicator = BeanUtil.mapToBean(dataMap,StkFinanceIndicator.class);
                        if(indicator != null){
                            indicators.add(indicator);
                            indicator.setUpdateFlag("0");
                            stkFinanceIndicatorMapper.insert(indicator);
                        }
                    }
                }
            }
        }
        return result;
    }


    /***
     * 查询主营业务信息（北区，东区，南区，西区，总行）  其他数据不需要，每次最多100条数据不全
     * @param code       证券代码
     * @param type       类型：P按产品 D按地区（请输入大写字母P或者D）
     * @param startDate  开始时间
     * @param endDate    结束时间
     * @param period     报告期(每个季度最后一天的日期,比如20171231表示年报)
     * @return
     */
    @Override
    @Transactional(rollbackFor = {RuntimeException.class, Error.class})
    public String downloadMainbzFromTushare(String code, String type, String startDate, String endDate, String period) {
        Map<String, Object> paramMap = HttpUtil.initParam("fina_mainbz");
        Map<String,Object> params = new HashMap<>();
        params.put("ts_code",code);
        params.put("start_date",startDate);
        params.put("end_date",endDate);
        params.put("period",period);
        params.put("type",type);
        paramMap.put("params",params);
        logger.warn("#####财报同步####downloadBalanceFromTushare（）查询条件" + paramMap);
        String result = HttpUtil.doPost(HttpUtil.urlLinke,paramMap,null);
        logger.warn("#####财报同步####downloadBalanceFromTushare（）查询结果" + result);
        if(!StringUtils.isEmpty(result)){
            JSONObject incomeData = JSON.parseObject(result);
            if(incomeData != null && incomeData.containsKey("data")){
                JSONArray array = incomeData.getJSONObject("data").getJSONArray("fields");
                for (int i = 0; i < array.size(); i++) {
                    array.set(i, StringUtil.convertSqlCodeToHump(array.getString(i)));
                }
                JSONArray items = incomeData.getJSONObject("data").getJSONArray("items");
                List<StkFinanceMainbz> mainbzs = new ArrayList<>(items.size());
                JSONArray data;
                Map<String, Object> dataMap;
                StkFinanceMainbz mainbz;
                stkFinanceMainbzMapper.deleteByCode(code);
                for (int i = 0; i < items.size(); i++) {
                    data = items.getJSONArray(i);
                    dataMap = HttpUtil.processDataToMap(data, array);
                    if(MapUtils.isNotEmpty(dataMap)){
                        mainbz = BeanUtil.mapToBean(dataMap,StkFinanceMainbz.class);
                        if(mainbz != null){
                            mainbzs.add(mainbz);
                            mainbz.setUpdateFlag("0");
                            stkFinanceMainbzMapper.insert(mainbz);
                        }
                    }
                }
            }
        }
        return result;
    }

}
