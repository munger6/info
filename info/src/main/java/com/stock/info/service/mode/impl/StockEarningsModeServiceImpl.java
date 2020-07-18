package com.stock.info.service.mode.impl;


import com.stock.info.service.mode.StockEarningsModeService;
import com.stock.info.service.mode.context.EarningsContext;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Service
@Repository
public class StockEarningsModeServiceImpl implements StockEarningsModeService {


    @Override
    public boolean createModeExcel(EarningsContext context) {
        //第一步：执行筛选个股信息流程，
        //      如果个股信息不存在则跳过执行，
        //      如果筛选信息不存在则执行全部个股

        //第二步：循环个股信息，查询符合时间期限的个股数据


        //第三步：数据初始化，查询指定指标数据（eps，bps）

        //第四步： 初始化excel，创建标题信息

        //第五步：按照行写入数信息；

        //第六步：导出excel


        return false;
    }
}
