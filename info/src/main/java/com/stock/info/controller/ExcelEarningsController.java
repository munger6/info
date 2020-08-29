package com.stock.info.controller;


import com.stock.info.Util.PublicUtil;
import com.stock.info.constant.enums.EarningModeTypeEnum;
import com.stock.info.domain.ResultPage;
import com.stock.info.service.StkStockExcelService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * 财报生成excel报表
 */
@RestController
@RequestMapping(value = "excel")
public class ExcelEarningsController {

    private Logger logger = LoggerFactory.getLogger("WARN_FILE");

    @Autowired
    StkStockExcelService stkStockExcelService;


    /**
     *  创建财务报表（资产负债表/利润表/现金流量表/
     *              分项财务构成信息： 资产负债表/利润表-毛利/三费/    资产周转率/ 营运周期/运营周期    ）
     *          杜邦分析：
     * @param code   证券代码
     * @param type   创建类型（year - 纯年报/ all - 年报+ 季报）
     * @param timeLong  时间长度（不传入参数则为7年起步）  报表展示时间长度
     * @return
     */
    @RequestMapping(value = "createEarningsExcel")
    public ResultPage createEarningsExcel(String code, String type, String timeLong){
        try {
            stkStockExcelService.createEarningsExcel(code, type, timeLong);
            return PublicUtil.initResult(true,"创建证券代码"+code+"财务报表信息成功",null);
        } catch (Exception e) {
            return PublicUtil.initResult(false,"创建证券代码"+code+"财务报表信息失败,失败原因：" + e.getMessage(),null);
        }
    }


    /**
     * 财务报表建模
     * @param code   证券代码，不传则按照条件查询所有符合条件证券代码进行计算
     * @param type   模型-taotao  王重涛建模     模型-2 杨林建模
     * @param timeLong   时长信息
     * @return
     */
    @RequestMapping(value = "createmModeExcel")
    public ResultPage createmModeExcel(String code, String type, String timeLong){
        try {
            if(StringUtils.isEmpty(timeLong)){
                timeLong = "10";
            }
            if(EarningModeTypeEnum.getEnumByCode(type) == null){
                return PublicUtil.initResult(false,"请传入有效的模型类型",null);
            }
            boolean isSuccess = stkStockExcelService.createEarningsModeExcel(code, type, timeLong);
            if(isSuccess){
                return PublicUtil.initResult(isSuccess,"创建证券代码"+code+"财务报表信息成功",isSuccess);
            }
            return PublicUtil.initResult(isSuccess,"创建证券代码"+code+"财务报表信息失败",isSuccess);
        } catch (Exception e) {
            logger.error("创建证券代码"+code+"财务报表信息失败；",e);
            return PublicUtil.initResult(false,"创建证券代码"+code+"财务报表信息失败,失败原因：",null);
        }
    }

    /**
     * todo  自主建模；根据建模方式生成建模财务报表
     *        1，获取证券代码，
     *        2，过滤条件（利用表达式进行条件判断）
     *        3，根据建模类型，生成建模数据；
     *        4，写入指定模型的excel报表路径地址 模型type/年份/报表
     */


}
