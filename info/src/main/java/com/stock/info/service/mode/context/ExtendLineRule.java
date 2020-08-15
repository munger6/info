package com.stock.info.service.mode.context;

import java.util.Map;

public class ExtendLineRule {

    /**
     * 未来行长度
     */
    private int futureLineLength;

    /**
     * 未来行计算规则（指定增速版本/根据之前增速按照增速变化速率拟合增速）
     *     为简便： 首先完成简单的固定增速版本
     */
    private String futureCompuateType;

    /***
     * 未来行计算参数
     *      固定增速则为增速
     *      拟合增速则获取拟合指标
     */
    private Map<String,Object> futureCompuateMap;

    public ExtendLineRule(int futureLineLength, String futureCompuateType, Map<String, Object> futureCompuateMap) {
        this.futureLineLength = futureLineLength;
        this.futureCompuateType = futureCompuateType;
        this.futureCompuateMap = futureCompuateMap;
    }


    //get 方法
    public int getFutureLineLength() {
        return futureLineLength;
    }

    public String getFutureCompuateType() {
        return futureCompuateType;
    }

    public Map<String, Object> getFutureCompuateMap() {
        return futureCompuateMap;
    }
}
