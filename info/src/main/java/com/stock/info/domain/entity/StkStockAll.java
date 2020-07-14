package com.stock.info.domain.entity;

import java.io.Serializable;
import java.util.Date;

public class StkStockAll implements Serializable {
    //ts股票
    private String tsCode;
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

    private static final long serialVersionUID = 1L;

    public String getTsCode() {
        return tsCode;
    }

    public void setTsCode(String tsCode) {
        this.tsCode = tsCode == null ? null : tsCode.trim();
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol == null ? null : symbol.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area == null ? null : area.trim();
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry == null ? null : industry.trim();
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname == null ? null : fullname.trim();
    }

    public String getEnname() {
        return enname;
    }

    public void setEnname(String enname) {
        this.enname = enname == null ? null : enname.trim();
    }

    public String getMarket() {
        return market;
    }

    public void setMarket(String market) {
        this.market = market == null ? null : market.trim();
    }

    public String getExchange() {
        return exchange;
    }

    public void setExchange(String exchange) {
        this.exchange = exchange == null ? null : exchange.trim();
    }

    public String getCurrType() {
        return currType;
    }

    public void setCurrType(String currType) {
        this.currType = currType == null ? null : currType.trim();
    }

    public String getListStatus() {
        return listStatus;
    }

    public void setListStatus(String listStatus) {
        this.listStatus = listStatus == null ? null : listStatus.trim();
    }

    public String getListDate() {
        return listDate;
    }

    public void setListDate(String listDate) {
        this.listDate = listDate == null ? null : listDate.trim();
    }

    public String getDelistDate() {
        return delistDate;
    }

    public void setDelistDate(String delistDate) {
        this.delistDate = delistDate == null ? null : delistDate.trim();
    }

    public String getIsHs() {
        return isHs;
    }

    public void setIsHs(String isHs) {
        this.isHs = isHs == null ? null : isHs.trim();
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", tsCode=").append(tsCode);
        sb.append(", symbol=").append(symbol);
        sb.append(", name=").append(name);
        sb.append(", area=").append(area);
        sb.append(", industry=").append(industry);
        sb.append(", fullname=").append(fullname);
        sb.append(", enname=").append(enname);
        sb.append(", market=").append(market);
        sb.append(", exchange=").append(exchange);
        sb.append(", currType=").append(currType);
        sb.append(", listStatus=").append(listStatus);
        sb.append(", listDate=").append(listDate);
        sb.append(", delistDate=").append(delistDate);
        sb.append(", isHs=").append(isHs);
        sb.append(", createDate=").append(createDate);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}