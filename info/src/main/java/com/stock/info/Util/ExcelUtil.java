package com.stock.info.Util;

import org.apache.commons.lang3.math.NumberUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.util.CellRangeAddress;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

/***
 *  excel工具类：提供excel写入的基础方法
 *      创建excel、sheet、行、列、图标等等....
 *      支持为excel2007 xmlx格式文件
 */
public class ExcelUtil {
    private static final int DEFAULT_WIDTH = 12;

    //不允许new这个工具类
    private ExcelUtil() {
    }

    /***
     * 初始化一个HSSFWorkbook
     * @return
     */
    public static HSSFWorkbook getWorkbook() {
        return new HSSFWorkbook();
    }


    /**
     * 创建一个sheet页
     *
     * @param book excel文件
     * @param sheetName sheet页的名称
     * @return 返回sheet对象
     */
    public static HSSFSheet createSheetWithName(HSSFWorkbook book, String sheetName) {
        return book.createSheet(sheetName);
    }

    /**
     * 设置每一列的宽度
     *
     * @param columnIndex 列标
     * @param byteSize    可以显示的字节数
     */
    public static void setColumnWidth(HSSFSheet sheet, int columnIndex, int byteSize) {
        sheet.setColumnWidth(columnIndex, byteSize * 256);
    }


    /**
     * 创建标题行
     *
     * @param book excel文件
     * @param sheet sheet
     * @param headerArray 标题列名组成的数组
     */
    public static HSSFRow createHeaderRow(HSSFWorkbook book,HSSFSheet sheet, String[] headerArray) {
        sheet.setDefaultColumnWidth((short) DEFAULT_WIDTH);// 设置表格默认列宽度为10个字节
        HSSFRow row = sheet.createRow(0);//第一行
        row.setHeight((short) (30 * 15.625));
        for (int i = 0; i < headerArray.length; i++) {
            setSizeIfNecessary(sheet, i, headerArray[i]);
            HSSFCell cell = row.createCell(i);//新建单元格
            HSSFRichTextString text = new HSSFRichTextString(headerArray[i]);
            cell.setCellValue(text);//设置文本
            cell.setCellStyle(getDefaultHeaderCellStyle(book));//设置样式
        }
        return row;
    }

    /**
     * 创建具体的一行
     *
     * @param book     excel文件
     * @param sheet     sheet
     * @param rowNum     行号
     * @param columnData 每一列的数据
     * @return 返回行对象，row可用于获取特定单元格row.getCell()，再进行特定的设置（不想使用这个默认设置的时候）
     */
    public static HSSFRow createRow(HSSFWorkbook book, HSSFSheet sheet, int rowNum, List<String> columnData,String mode) {
        HSSFRow row = sheet.createRow(rowNum);
        row.setHeight((short) (21 * 15.625));
        for (int i = 0; i < columnData.size(); i++) {
            HSSFCell cell = row.createCell(i);//新建单元格
            setSizeIfNecessary(sheet, i, columnData.get(i));
            if(NumberUtils.isDigits(columnData.get(i))){
                cell.setCellValue(new BigDecimal(columnData.get(i)).doubleValue());//设置文本
            }else{
                HSSFRichTextString text = new HSSFRichTextString(columnData.get(i));
                if(MODE_FORMULA.equals(mode) && i > 0){
                    cell.setCellFormula(columnData.get(i));
                }else{
                    cell.setCellValue(text);//设置文本
                }
            }
            cell.setCellStyle(getDefaultCellStyle(book));//设置样式
        }
        return row;
    }

    //单元格数据表达式：  方式
    private final static String MODE_FORMULA = "experisson";



    /**
     * 创建特殊的一行
     * @param book     excel文件
     * @param sheet    sheet
     * @param rowNum   行号
     * @param columnData   列数据
     * @return
     */
    public static HSSFRow createSpecailRow(HSSFWorkbook book,HSSFSheet sheet, int rowNum, String[] columnData) {
        HSSFRow row = sheet.createRow(rowNum);
        for (int i = 0; i < columnData.length; i++) {
            HSSFCell cell = row.createCell(i);//新建单元格
            HSSFRichTextString text = new HSSFRichTextString(columnData[i]);
            cell.setCellValue(text);//设置文本
            cell.setCellStyle(getDefaultCellStyle(book));//设置样式
        }
        return row;
    }

    /**
     * 超过我们设置的默认列宽度的时候，重新设置列宽
     *
     * @param sheet sheet
     * @param columnIndex 列标
     * @param cellData    单元格的数据
     */
    private static void setSizeIfNecessary(HSSFSheet sheet, int columnIndex, String cellData) {
        if (null == cellData || "".equals(cellData)){
            return;
        }
        int size = cellData.getBytes().length;
        if (size > DEFAULT_WIDTH) {
            setColumnWidth(sheet, columnIndex, size);
        }
    }

    /**
     * 设置跨行跨列
     *
     * @param sheet       sheet对象
     * @param startRow    起始行
     * @param endRow      结束行
     * @param startColumn 起始列
     * @param endColumn   结束列
     */
    public static void crossingRowsAndColumns(HSSFSheet sheet, int startRow, int endRow, int startColumn, int endColumn) {
        sheet.addMergedRegion(new CellRangeAddress(startRow, endRow, startColumn, endColumn));
    }


    /**
     * 获取默认的标题列的单元格样式(背景色，填充色，字体样式)
     *    https://blog.csdn.net/lsh15846393847/article/details/93199909
     * @return 样式对象
     */
    private static CellStyle getDefaultHeaderCellStyle(HSSFWorkbook book) {
        if (null != myHeaderCellStyle){
            return myHeaderCellStyle;
        }
        CellStyle cellStyle = getDefaultCellStyle(book);
        cellStyle.setFillForegroundColor(IndexedColors.BRIGHT_GREEN.getIndex());//背景色
        cellStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);//填充背景色
        HSSFFont font = getDefaultFont(book);
        font.setFontName(FontNameEnum.SIM_HEI.getFormalName());
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);//粗体显示
        cellStyle.setFont(font);//选择需要用到的字体格式
        return cellStyle;
    }

    /**
     * 获取默认的单元格样式（水平居中/垂直居中/边框颜色/自动换行/）
     *
     * @param book excel文件对象
     * @return 样式对象
     */
    public static CellStyle getDefaultCellStyle(HSSFWorkbook book) {
        if (null != myCellStyle){
            return myCellStyle;
        }
        CellStyle cellStyle = book.createCellStyle();
        cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 居中
        cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 上下居中
        cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框
        cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN); //下边框
        cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框
        cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框
        cellStyle.setWrapText(true);//设置自动换行
        cellStyle.setFont(getDefaultFont(book));//选择需要用到的字体格式
        return cellStyle;
    }

    /**
     * 获取默认的字体（字体大小，字体-微软雅黑）
     *
     * @param book  excel文件
     * @return 字体对象，如果外部需要获取字体，可将此方法设置为public
     */
    private static HSSFFont getDefaultFont(HSSFWorkbook book) {
        if (null != myFont){
            return myFont;
        }
        HSSFFont font = book.createFont();
        font.setFontName(FontNameEnum.MICROSOFT_YA_HEI.getFormalName());
        font.setFontHeightInPoints((short) 10);//设置字体大小
        return font;
    }

    /**
     * 保存为Excel文件
     *
     * @param book  excel文件
     * @param filePath 文件全路径
     * @throws IOException 文件操作异常
     */
    public static void saveExcelFile(HSSFWorkbook book,String filePath) {
        FileOutputStream fileOutputStream = null;
        try {
//            filePath   获取当前文档的命名信息
            fileOutputStream = new FileOutputStream(filePath);//指定路径与名字和格式
            book.write(fileOutputStream);//将数据写出去
            fileOutputStream.close();//关闭输出流
        } catch (IOException e) {
            if(fileOutputStream == null){
                try {
                    fileOutputStream.close();//关闭输出流
                } catch (IOException e1) {

                }
            }
        }
    }

    /**
     * 下载文件到客户端
     *
     * @param file     文件对象
     * @param request  servlet请求对象
     * @param response servlet响应对象
     */
    public static void downloadExcelFile(File file, HttpServletRequest request, HttpServletResponse response) {
        String fileName = file.getName();
        InputStream fin = null;
        ServletOutputStream out = null;
        try {
            fin = new FileInputStream(file);
            out = response.getOutputStream();
            response.setCharacterEncoding("utf-8");
            response.setContentType("application/octet-stream");
            boolean isMSIE = isMSBrowser(request);
            if (isMSIE) {
                //IE浏览器的乱码问题解决
                fileName = URLEncoder.encode(fileName, "UTF-8");
            } else {
                //万能乱码问题解决
                fileName = new String(fileName.getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1);
            }
            response.setHeader("Content-disposition", "attachment;filename=\"" + fileName + "\"");

            byte[] buffer = new byte[1024];
            int bytesToRead = -1;
            // 通过循环将读入的Word文件的内容输出到浏览器中
            while ((bytesToRead = fin.read(buffer)) != -1) {
                out.write(buffer, 0, bytesToRead);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fin != null) {
                try {
                    fin.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
    }

    private static String[] IEBrowserSignals = {"MSIE", "Trident", "Edge"};

    /**
     * 判断是否是IE浏览器
     */
    private static boolean isMSBrowser(HttpServletRequest request) {
        String userAgent = request.getHeader("User-Agent");
        for (String signal : IEBrowserSignals) {
            if (userAgent.contains(signal)){
                return true;
            }
        }
        return false;
    }


    private enum FontNameEnum {
        SIM_SUM("宋体", "SimSum"),
        SIM_HEI("黑体", "SimHei"),
        MICROSOFT_YA_HEI("微软雅黑", "Microsoft YaHei"),
        N_SIM_SUM("新宋体", "NSimSum"),
        FANG_SONG("仿宋", "FangSong"),
        KAI_TI("楷体", "KaiTi");

        private String chineseName;
        private String formalName;

        FontNameEnum(String chineseName, String formalName) {
            this.chineseName = chineseName;
            this.formalName = formalName;
        }

        public String getFormalName() {
            return this.formalName;
        }

        public String getChineseName() {
            return this.chineseName;
        }
    }

    private static CellStyle myHeaderCellStyle = null;
    private static CellStyle myCellStyle = null;
    private static HSSFFont myFont = null;

    public static void setMyHeaderCellStyle(CellStyle cellStyle) {
        myHeaderCellStyle = cellStyle;
    }

    public static void setMyCellStyle(CellStyle cellStyle) {
        myCellStyle = cellStyle;
    }

    public static void setMyFont(HSSFFont font) {
        myFont = font;
    }
}
