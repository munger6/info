package com.stock.info.domain;

/**
 * 结果pageBean
 */
public class ResultPage {
    //执行是否成功
    private boolean isSuccess;
    //执行返回信息
    private String msg;
    //返回数据信息
    private Object date;

    public ResultPage(boolean isSuccess, String msg, Object date) {
        this.isSuccess = isSuccess;
        this.msg = msg;
        this.date = date;
    }

    //get and set
    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getDate() {
        return date;
    }

    public void setDate(Object date) {
        this.date = date;
    }
}
