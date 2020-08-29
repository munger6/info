package com.stock.info.service.mode.context;

import org.springframework.util.Assert;

public class EarningsContext {

    //证券代码
    private String tsCode;
    //财报类型
    private String type;
    //时长
    private String timeLong;
    //时长
    private ModeExcelCreateRule rule;

    /**
     * 查询获取上下文信息
     * @param type
     * @param timeLong
     * @return
     */
    public static EarningsContext getContext(String type, String timeLong,String tsCode) {
        ModeExcelCreateRule ruleByType = EarningsConfig.getRuleByType(type);
        Assert.isTrue(ruleByType != null,String.format("模型【%s】未支持",type));
        EarningsContext context = new EarningsContext();
        context.setTimeLong(timeLong);
        context.setType(type);
        context.setRule(ruleByType);
        context.setTsCode(tsCode);
        return context;
    }

    public String getTsCode() {
        return tsCode;
    }

    private void setTsCode(String tsCode) {
        this.tsCode = tsCode;
    }

    public ModeExcelCreateRule getRule() {
        return rule;
    }

    private void setRule(ModeExcelCreateRule rule) {
        this.rule = rule;
    }

    public boolean isCanCreate() {
        return true;
    }

    public String getType() {
        return type;
    }

    private void setType(String type) {
        this.type = type;
    }

    public String getTimeLong() {
        return timeLong;
    }

    private void setTimeLong(String timeLong) {
        this.timeLong = timeLong;
    }

    @Override
    public String toString() {
        return "EarningsContext{" +
                "type='" + type + '\'' +
                ", timeLong='" + timeLong + '\'' +
                ", rule=" + rule +
                '}';
    }
}
