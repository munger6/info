package com.stock.info.service.mode.strategy.title;

import com.stock.info.Util.DateOperation;
import com.stock.info.Util.ExcelUtil;
import com.stock.info.constant.PublicConstant;
import com.stock.info.domain.entity.StkStockAll;
import com.stock.info.service.mode.TitleHandler;
import com.stock.info.service.mode.context.EarningsContext;
import com.stock.info.service.mode.context.ModeExcelCreateRule;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 涛涛模型处理器
 */
@Service
@Repository
public class TaoTaoTitleHandler  implements TitleHandler {
    @Override
    public HSSFWorkbook createTitle(StkStockAll stk, HSSFWorkbook book, HSSFSheet sheet,
                                    EarningsContext context, Date startDate, Date endDate) {

        ModeExcelCreateRule contextRule = context.getRule();

        //标签
        String[] title = "代码,简称,资本成本,贝塔,RM,RF,G(增速),单位".split(PublicConstant.COMMA);
        ExcelUtil.createHeaderRow(book,sheet,title);

        //基础信息栏
        List<String> param = new ArrayList<>();
        param.add(stk.getTsCode());
        param.add(stk.getName());
        param.add("0.09812");
        param.add("1.29");
        param.add("0.08");
        param.add("0.0175");
        param.add("0.01");
        param.add(contextRule.getUnit() == 1 ? "初始单位" :
                (contextRule.getUnit() == 1<<1 ? "万" :
                        (contextRule.getUnit() == 1<<2 ? "百万" :
                                (contextRule.getUnit() == 1<<3 ? "亿" :("未设置")))));
        ExcelUtil.createRow(book,sheet,1,param,"");

        //标题栏
        Integer forecastTimeLong = contextRule.getForecastTimeLong();
        param = new ArrayList<>();
        param.add("预测年份");
        Date indexDate = startDate;
        for (;endDate.after(indexDate);indexDate = DateOperation.addYears(indexDate,1)){
            param.add(DateOperation.formatDate(indexDate,DateOperation.YEAR));
        }
        if(forecastTimeLong  == null || forecastTimeLong < 0){
            forecastTimeLong = 0;
        }
        for (int i = 1; i <=  forecastTimeLong; i++) {
            param.add(DateOperation.formatDate(DateOperation.addYears(endDate,i),DateOperation.YEAR));
        }
        ExcelUtil.createRow(book,sheet,3,param,"");
        return book;
    }
}
