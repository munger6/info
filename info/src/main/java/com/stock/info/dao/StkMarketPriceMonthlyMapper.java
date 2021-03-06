package com.stock.info.dao;

import com.stock.info.domain.entity.StkMarketPriceMonthly;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;


@Mapper
@Repository
public interface StkMarketPriceMonthlyMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table stk_market_price_monthly
     *
     * @mbggenerated
     */
    int insert(StkMarketPriceMonthly record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table stk_market_price_monthly
     *
     * @mbggenerated
     */
    List<StkMarketPriceMonthly> selectAll();


    /**
     * 删除根据code
     * @param code
     */
    void deleteByCode(String code);
}