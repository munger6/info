package com.stock.info.test;

import com.stock.info.Util.ExcelUtil;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.util.ArrayList;
import java.util.List;

public class TestExcel {

    /**
     * 测试excel的基本功能
     * @param args
     */
    public static void main(String[] args) {
        HSSFWorkbook workbook = ExcelUtil.getWorkbook();
        HSSFSheet sheet = ExcelUtil.createSheetWithName(workbook,"测试");
        List<String> columnData = new ArrayList<>();
        columnData.add("市值");
        columnData.add("34");
        columnData.add("45");
        columnData.add("53");
        columnData.add("64");
        ExcelUtil.createRow(workbook,sheet,0,columnData,"");
        columnData = new ArrayList<>();
        columnData.add("nps");
        columnData.add("E1/3");
        columnData.add("B1/3");
        columnData.add("C1/3");
        columnData.add("D1/3");
        ExcelUtil.createRow(workbook,sheet,1,columnData,"experisson");

        ExcelUtil.saveExcelFile(workbook,"D://","test.xls");

    }

}
