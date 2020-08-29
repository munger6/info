package com.stock.info.domain.queryObject;

import java.util.Date;
import java.util.List;

/**
 * 证券查询对象
 */
public class StkStockAllQueryObject {
    //ts股票
    private String tsCode;
    //ts股票
    private List<String> tsCodeList;
    //股票代码
    private String symbol;
    //股票名称
    private String name;
    //所在地域
    private String area;
    //所属行业
    private String industry;

    private String fullname;

    private String enname;
    //市场类型：  主板/中小板/创业板/科创板
    private String market;
    //交易市场
    private String exchange;

    private String currType;

    private String listStatus;

    private String listDate;

    private String delistDate;

    private String isHs;

    private Date createDate;


    //get and set
    public String getTsCode() {
        return tsCode;
    }

    public void setTsCode(String tsCode) {
        this.tsCode = tsCode;
    }

    public List<String> getTsCodeList() {
        return tsCodeList;
    }

    public void setTsCodeList(List<String> tsCodeList) {
        this.tsCodeList = tsCodeList;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getEnname() {
        return enname;
    }

    public void setEnname(String enname) {
        this.enname = enname;
    }

    public String getMarket() {
        return market;
    }

    public void setMarket(String market) {
        this.market = market;
    }

    public String getExchange() {
        return exchange;
    }

    public void setExchange(String exchange) {
        this.exchange = exchange;
    }

    public String getCurrType() {
        return currType;
    }

    public void setCurrType(String currType) {
        this.currType = currType;
    }

    public String getListStatus() {
        return listStatus;
    }

    public void setListStatus(String listStatus) {
        this.listStatus = listStatus;
    }

    public String getListDate() {
        return listDate;
    }

    public void setListDate(String listDate) {
        this.listDate = listDate;
    }

    public String getDelistDate() {
        return delistDate;
    }

    public void setDelistDate(String delistDate) {
        this.delistDate = delistDate;
    }

    public String getIsHs() {
        return isHs;
    }

    public void setIsHs(String isHs) {
        this.isHs = isHs;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}
