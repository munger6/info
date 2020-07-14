package com.stock.info.dao;

import com.stock.info.domain.entity.StkStockAll;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface StkStockAllMapper {

    int insert(StkStockAll record);

    List<StkStockAll> selectAll();

    void deleteAll(StkStockAll stkStockAll);
}