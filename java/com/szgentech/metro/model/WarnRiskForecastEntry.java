package com.szgentech.metro.model;


import java.util.List;

import com.szgentech.metro.base.page.Page;

/**
 * 风险预报实体类
 *
 * @Author: xuxiaoming
 * @date: 2017/11/6
 */

public class WarnRiskForecastEntry {

    private int code;  //状态码  200:正常返回
    private List<WarnRiskForecast> result;  //风险预报信息集合
    private Page page;


    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<WarnRiskForecast> getResult() {
        return result;
    }

    public void setResult(List<WarnRiskForecast> result) {
        this.result = result;
    }

    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }

    @Override
    public String toString() {
        return "WarnRiskForecastEntry{" +
                "code=" + code +
                ", result=" + result +
                ", page=" + page +
                '}';
    }
}
