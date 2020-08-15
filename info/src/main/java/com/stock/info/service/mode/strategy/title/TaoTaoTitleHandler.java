package com.stock.info.service.mode.strategy.title;

import com.stock.info.Util.DateOperation;
import com.stock.info.Util.ExcelUtil;
import com.stock.info.constant.PublicConstant;
import com.stock.info.domain.entity.StkStockAll;
import com.stock.info.service.mode.TitleHandler;
import com.stock.info.service.mode.context.EarningsContext;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 涛涛模型处理器
 */
public class TaoTaoTitleHandler  implements TitleHandler {
    @Override
    public XSSFWorkbook createTitle(StkStockAll stk, HSSFWorkbook book, HSSFSheet sheet,
                                    EarningsContext context, Date startDate, Date endDate) {

        //标签
        String[] title = "代码,简称,资本成本,贝塔,RM,RF,G(增速)".split(PublicConstant.COMMA);
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
        ExcelUtil.createRow(book,sheet,1,param,"");

        //标题栏
        param = new ArrayList<>();
        param.add("预测年份");
        param.add(stk.getName());
        for (startDate.before(endDate);startDate.after(endDate);){
            param.add(DateOperation.formatDate(startDate,DateOperation.YEAR));
            startDate = DateOperation.addYears(startDate,1);
        }
        ExcelUtil.createRow(book,sheet,3,param,"");
        return null;
    }
}
