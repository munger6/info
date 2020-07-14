package com.stock.info.controller;

import com.stock.info.domain.ResultPage;
import com.stock.info.service.StkStockEarningsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 证券财报（专注财报的下载工作）
 */
@RestController
@RequestMapping(value = "earnings")
public class StockEarningsController {

    @Autowired
    StkStockEarningsService stkStockEarningsService;

    @RequestMapping(value = "query")
    public Map<String,Object> query(){
        return stkStockEarningsService.query();
    }


    /**
     * 查询基础测试表信息
     *
     *   【备注】:
     *       1合并报表 (上市公司最新报表-默认)
     *       2单季合并 （但一季度的合并报表）
     *       3调整单季合并表 （调整后的单季度合并报表）
     *       4调整合并报表 （本年度公布上年同期的财务报表数据，  报告期为上年度）
     *       5调整前合并报表 （数据发生变更，将原数据进行保留--调整前原数据）
     *       6母公司报表 （母公司的报表）
     *       7母公司单季表 8 母公司调整单季表
     *       9母公司调整表 10母公司调整前报表
     *       11调整前合并报表（调整之前合并报表原数据）
     *       12母公司调整前报表（母公司报表发生变更前保留的原数据）
     * @return
     */
    @RequestMapping(value = "queryProfitStatementByContion")
    public Map<String,Object> queryProfitStatementByContion(){
        return stkStockEarningsService.queryStockListByContion("", "L","SSE");
    }


    /**
     * 重新下载个股信息
     *
     * @return
     */
//    @RequestMapping(value = "downloadStockListFromTushare")
//    public void downloadStockListFromTushare(){
//        stkStockEarningsService.downloadStockListFromTushare("", "L","");
//    }


    /**
     * 复制利润表数据根据条件
     *
     * @param date   公告日期
     * @param code   股票代码，必填
     * @param startDate   公告开始时间
     * @param endDate     公告结束时间
     * @param period      报告期（每个季度最后一天的日期）
     * @param reportType   报告类型 ：1合并报表 2单季合并 3调整单季合并表 4调整合并报表
     *                          5调整前合并报表 6母公司报表 7母公司单季表 8 母公司调整单季表
     *                          9母公司调整表 10母公司调整前报表 11调整前合并报表 12母公司调整前报表
     * @param compType     公司类型： 1，一般工商业  2-银行 3- 保险 4- 证券
     *   【备注】:
     *       1合并报表 (上市公司最新报表-默认)
     *       2单季合并 （但一季度的合并报表）
     *       3调整单季合并表 （调整后的单季度合并报表）
     *       4调整合并报表 （本年度公布上年同期的财务报表数据，  报告期为上年度）
     *       5调整前合并报表 （数据发生变更，将原数据进行保留--调整前原数据）
     *       6母公司报表 （母公司的报表）
     *       7母公司单季表 8 母公司调整单季表
     *       9母公司调整表 10母公司调整前报表
     *       11调整前合并报表（调整之前合并报表原数据）
     *       12母公司调整前报表（母公司报表发生变更前保留的原数据）
     * @return
     */
    @RequestMapping(value = "downloadIncomeFromTushare" , method = RequestMethod.POST)
    public ResultPage downloadIncomeFromTushare(@RequestParam(value = "") String date, @RequestParam String code
            , @RequestParam(value = "") String startDate, @RequestParam(value = "") String endDate
            , @RequestParam(value = "") String period, @RequestParam(value = "") String reportType
            , @RequestParam(value = "") String compType){
        try {
            Assert.isTrue(!StringUtils.isEmpty(code),"请传入有效证券代码");
            //暂时无需使用公司类型（compType）
//            String data = stkStockEarningsService.downloadIncomeFromTushare(code, date, startDate, endDate, period,reportType);
            String data = stkStockEarningsService.downloadMainbzFromTushare(code, "", "", "", "");
            ResultPage page = new ResultPage(true,"更新成功",data);
            return  page;
        }catch (Exception e){
            ResultPage page = new ResultPage(false,"更新失败，异常信息："+ e.getMessage(),code);
            return  page;
        }
    }


    /**
     * 更新指定代码下的所有财报数据
     * @param code
     * @return
     */
    @RequestMapping(value = "downloadFinanceFromTushare" , method = RequestMethod.POST)
    public ResultPage downloadFinanceFromTushare(@RequestParam( required = false , defaultValue = "") String code){
        try {
            stkStockEarningsService.downloadFinanceFromTushare(code);
            ResultPage page = new ResultPage(true,"更新成功",code);
            return  page;
        }catch (Exception e){
            ResultPage page = new ResultPage(false,"更新失败，异常信息："+ e.getMessage(),code);
            return  page;
        }
    }




}
