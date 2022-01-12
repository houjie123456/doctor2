package com.company.wanbei.app.bean;

/**
 * Created by YC on 2018/8/4.
 */

public class ReportBean2 {

//    testCode ：化验项目编码
//    testName ：化验项目名称
//    testSync ：化验项目的缩写
//    result ：化验结果
//    resFlag ：化验结果是否异常的提示：N代表正常，L代表结果低于参考值，H代表结果高于参考值
//    resFlagStr：化验结果是否异常的提示（用于显示）
//    unit ：单位
//    ranges ：参考范围

    private String testCode;
    private String testName;
    private String testSync;
    private String result;
    private String resFlag;
    private String resFlagStr;
    private String unit;
    private String ranges;

    private boolean isSelect;

    public String getTestCode() {
        return testCode;
    }

    public void setTestCode(String testCode) {
        this.testCode = testCode;
    }

    public String getTestName() {
        return testName;
    }

    public void setTestName(String testName) {
        this.testName = testName;
    }

    public String getTestSync() {
        return testSync;
    }

    public void setTestSync(String testSync) {
        this.testSync = testSync;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getResFlag() {
        return resFlag;
    }

    public void setResFlag(String resFlag) {
        this.resFlag = resFlag;
    }

    public String getResFlagStr() {
        return resFlagStr;
    }

    public void setResFlagStr(String resFlagStr) {
        this.resFlagStr = resFlagStr;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getRanges() {
        return ranges;
    }

    public void setRanges(String ranges) {
        this.ranges = ranges;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }
}
