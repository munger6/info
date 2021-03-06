package com.stock.info.dao;

import com.stock.info.domain.entity.StkFinanceIncome;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;


@Mapper
@Repository
public interface StkFinanceIncomeMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table stk_finance_income
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(String tsCode);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table stk_finance_income
     *
     * @mbggenerated
     */
    int insert(StkFinanceIncome record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table stk_finance_income
     *
     * @mbggenerated
     */
    StkFinanceIncome selectByPrimaryKey(String tsCode);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table stk_finance_income
     *
     * @mbggenerated
     */
    List<StkFinanceIncome> selectByContion(Map<String,Object> param);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table stk_finance_income
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(StkFinanceIncome record);
}