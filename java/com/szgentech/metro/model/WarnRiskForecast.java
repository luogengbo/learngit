package com.szgentech.metro.model;

/**
 * Created by dell on 2017/11/28.
 */

public class WarnRiskForecast {

    private String lineIntervalName; //线路区间名称
    private String msg;     //风险预报信息
    private int level;      //风险预报级别   1.红色标记为当前风险源 ，2.黄色标记为临近风险源 ， 3.黑色标记为离开风险源


    public String getLineIntervalName() {
        return lineIntervalName;
    }

    public void setLineIntervalName(String lineIntervalName) {
        this.lineIntervalName = lineIntervalName;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    @Override
    public String toString() {
        return "WarnRiskForecast{" +
                "lineIntervalName='" + lineIntervalName + '\'' +
                ", msg='" + msg + '\'' +
                ", level=" + level +
                '}';
    }
}
