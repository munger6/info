package com.stock.info.dao;

import com.stock.info.domain.entity.StkStockAll;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Mapper
@Repository
public interface StkStockAllMapper {

    int insert(StkStockAll record);

    List<StkStockAll> selectAll();

    void deleteAll(StkStockAll stkStockAll);

    /**
     * 查询指标信息
     * @param indexColumnName  指标列明
     * @param indexTable     指标表名
     * @param dateList       日期集合
     * @param contion        查询条件
     * @return
     */
    List<BigDecimal> selectIndexMessage(String indexColumnName, String indexTable, List<String> dateList, String contion);
}