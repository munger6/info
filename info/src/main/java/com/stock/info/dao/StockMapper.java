package com.stock.info.dao;


import com.stock.info.domain.StkStockDO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;


@Mapper
@Repository
public interface StockMapper {

    StkStockDO query();
}
