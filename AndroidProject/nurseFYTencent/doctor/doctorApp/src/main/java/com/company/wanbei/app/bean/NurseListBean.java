package com.company.wanbei.app.bean;

/**
 * Created by YC on 2018/6/29.
 */
/*
返回参数：
code-1.成功 0.失败
msgbox-信息说明
id-护理服务ID
myName-护士姓名
typeName-护理类型
serviceName-护理服务名称
sortLevel-优先级
createTime-添加时间
appointStartTime-预约开始时间
appointEndTime-预约结束时间
appointState-预约状态   （ 0.未预约 1.预约中）
totalNumber-总预约人数
useNumber-已预约人数
appointAmount-预约金额
 */
public class NurseListBean {
    private String id;//-护理服务ID
    private String myName;//-护士姓名
    private String typeName;//-护理类型
    private String serviceName;//-护理服务名称
    private String sortLevel;//-优先级
    private String createTime;//-添加时间
    private String appointStartTime;//-预约开始时间
    private String appointEndTime;//-预约结束时间
    private String appointState;//-预约状态   （ 0.未预约 1.预约中）
    private String totalNumber;//-总预约人数
    private String useNumber;//-已预约人数
    private String appointAmount;//-预约金额

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMyName() {
        return myName;
    }

    public void setMyName(String myName) {
        this.myName = myName;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getSortLevel() {
        return sortLevel;
    }

    public void setSortLevel(String sortLevel) {
        this.sortLevel = sortLevel;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getAppointStartTime() {
        return appointStartTime;
    }

    public void setAppointStartTime(String appointStartTime) {
        this.appointStartTime = appointStartTime;
    }

    public String getAppointEndTime() {
        return appointEndTime;
    }

    public void setAppointEndTime(String appointEndTime) {
        this.appointEndTime = appointEndTime;
    }

    public String getAppointState() {
        return appointState;
    }

    public void setAppointState(String appointState) {
        this.appointState = appointState;
    }

    public String getTotalNumber() {
        return totalNumber;
    }

    public void setTotalNumber(String totalNumber) {
        this.totalNumber = totalNumber;
    }

    public String getUseNumber() {
        return useNumber;
    }

    public void setUseNumber(String useNumber) {
        this.useNumber = useNumber;
    }

    public String getAppointAmount() {
        return appointAmount;
    }

    public void setAppointAmount(String appointAmount) {
        this.appointAmount = appointAmount;
    }
}
