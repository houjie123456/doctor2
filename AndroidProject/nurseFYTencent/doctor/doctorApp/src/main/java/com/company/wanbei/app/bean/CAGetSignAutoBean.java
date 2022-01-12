package com.company.wanbei.app.bean;

/**
 * Created by YC on 2018/8/4.
 */

public class CAGetSignAutoBean {

    private String status;
    private String message;
    private CADataBean data;

    private String openId; //用户唯一标识
    private String userName; //用户名
    private String startTime; //证书颁发时间
    private String endTime; //证书有效时间
    private String nowTime; //医网信服务器当前时间
    private String certUpdateTipDay; //证书更新提前提醒天数。假设为30天，即证书到期前30天可以进行证书更新操作
    private boolean existsStamp;//当前用户是否已经设置个人签章
    private boolean deviceFit;  //当前用户证书的设备和服务端记录的是否保持一致（仅当status 表示成功时有效）


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


    public CADataBean getData() {
        return data;
    }

    public void setData(CADataBean data) {
        this.data = data;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getNowTime() {
        return nowTime;
    }

    public void setNowTime(String nowTime) {
        this.nowTime = nowTime;
    }

    public String getCertUpdateTipDay() {
        return certUpdateTipDay;
    }

    public void setCertUpdateTipDay(String certUpdateTipDay) {
        this.certUpdateTipDay = certUpdateTipDay;
    }

    public boolean isExistsStamp() {
        return existsStamp;
    }

    public void setExistsStamp(boolean existsStamp) {
        this.existsStamp = existsStamp;
    }

    public boolean isDeviceFit() {
        return deviceFit;
    }

    public void setDeviceFit(boolean deviceFit) {
        this.deviceFit = deviceFit;
    }
}
