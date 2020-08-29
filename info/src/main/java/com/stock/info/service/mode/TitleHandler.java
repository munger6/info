package com.stock.info.service.mode;

import com.stock.info.domain.entity.StkStockAll;
import com.stock.info.service.mode.context.EarningsContext;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.util.Date;

/**
 * 标题处理器
 */
public interface TitleHandler {

    /**
     * 创建标题
     * @param stk       证券信息
     * @param book       excel
     * @param context    内容
     * @param startDate  开始时间
     * @param endDate    结束时间
     * @return
     */
    HSSFWorkbook createTitle(StkStockAll stk,HSSFWorkbook book , HSSFSheet sheet, EarningsContext context, Date startDate, Date endDate);

}
