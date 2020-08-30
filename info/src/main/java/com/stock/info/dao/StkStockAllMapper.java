package com.stock.info.dao;

import com.stock.info.domain.entity.StkStockAll;
import com.stock.info.domain.queryObject.StkStockAllQueryObject;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Mapper
@Repository
public interface StkStockAllMapper {

    int insert(StkStockAll record);

    List<StkStockAll> selectAll();

    void deleteAll(StkStockAll stkStockAll);

    /**
     * 查询指标信息
     * @param  param{
     *    indexColumnName  指标列明
     *    indexTable     指标表名
     *    dateList       日期集合
     *    contion        查询条件
     * }
     * @return
     */
    List<Map<String,Object>> selectIndexMessage(Map<String, Object> param);


    /**
     * 根据条件查询证券信息
     * @param queryObject
     * @return
     */
    List<StkStockAll> selectAllByContion(StkStockAllQueryObject queryObject);
}