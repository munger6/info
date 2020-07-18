package com.stock.info.service.mode.context;

public class EarningsContext {

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
    public static EarningsContext getContext(String type, String timeLong) {
        EarningsContext context = new EarningsContext();
        context.setTimeLong(timeLong);
        context.setType(type);
        context.setRule(EarningsConfig.getRuleByType(type));
        return context;
    }

    public ModeExcelCreateRule getRule() {
        return rule;
    }

    public void setRule(ModeExcelCreateRule rule) {
        this.rule = rule;
    }

    public boolean isCanCreate() {
        return true;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTimeLong() {
        return timeLong;
    }

    public void setTimeLong(String timeLong) {
        this.timeLong = timeLong;
    }
}
