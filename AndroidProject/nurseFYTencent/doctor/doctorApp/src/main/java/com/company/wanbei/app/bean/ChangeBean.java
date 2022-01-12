package com.company.wanbei.app.bean;

/**
 * Created by YC on 2018/7/15.
 */

public class ChangeBean {
    private String startTime;// 报道日期
    private String deptName;// 科室名称
    private String visitSexName;// 就诊人性别
    private String cyzd;// 出院诊断
    private String leaveId;// 出院小结ID（用于查询出院小结详情）


    private String startDate;//: 预约挂号日期
    private String departmentid;//: 科室ID
    private String visitIdCardNo;//: 就诊人身份证号
    private String seqCode;//: 号源
    private String admitAddress;//: 就诊地址
    private String visitAge;//: 就诊人年龄
    private String seqStateStr;//: 挂号状态
    private String orderId;//: 订单编号
    private String detName;//: 科室名称
    private String visitMobile;//: 就诊人手机号


    private String id;// : 转诊ID
    private String doctorid;// : 发送医生ID
    private String doctorAcademicTitle;// : 发送医生职称代码
    private String doctorName;// : 发送医生姓名
    private String doctorAcademicTitleName;// : 发送医生职称名称
    private String doctorHeadUrl;// : 发送医生头像
    private String todoctorid;// : 接收医生ID
    private String toDoctorAcademicTitle;// :接收医生职称代码
    private String toDoctorHeadUrl;// : 接收医生头像
    private String toDoctorName;// : 接收医生名称
    private String toDoctorMobile;// : 接收医生手机号
    private String toDoctorAcademicTitleName;// : 接收医生职称名称
    private String toTimeStr;// : 转诊时间 （yyyy-MM-dd HH:mm）
    private String gotolevel;// : 转诊转向级别 1.同级别转 2.向上级转 3.向下级转
    private String patientName;// : 患者名称
    private String patientid;// : 患者ID
    private String hospitalName;// : 对方医院名称
    private String inAndOut;// : 转入转出的标识：1、转入;2、转出;为空时查所有
    private String visitName;// : 就诊人姓名
    private String visitid;// : 就诊人ID
    private String visitSex;// :就诊人性别 1男2女
    private String isAcceptName;// ：接收状态
    private String isAccept ;// : 是否接受 0.未接受 1.已接受 2.已过期 3.已拒绝 4.已完成 14.已接受或已完成

    private String otherParam1;//拒绝说明
    private String otherParam;//转诊说明


    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getDepartmentid() {
        return departmentid;
    }

    public void setDepartmentid(String departmentid) {
        this.departmentid = departmentid;
    }

    public String getVisitIdCardNo() {
        return visitIdCardNo;
    }

    public void setVisitIdCardNo(String visitIdCardNo) {
        this.visitIdCardNo = visitIdCardNo;
    }

    public String getSeqcode() {
        return seqCode;
    }

    public void setSeqcode(String seqcode) {
        this.seqCode = seqcode;
    }

    public String getAdmitaddress() {
        return admitAddress;
    }

    public void setAdmitaddress(String admitaddress) {
        this.admitAddress = admitaddress;
    }

    public String getVisitAge() {
        return visitAge;
    }

    public void setVisitAge(String visitAge) {
        this.visitAge = visitAge;
    }

    public String getSeqStateStr() {
        return seqStateStr;
    }

    public void setSeqStateStr(String seqStateStr) {
        this.seqStateStr = seqStateStr;
    }

    public String getOrderid() {
        return orderId;
    }

    public void setOrderid(String orderid) {
        this.orderId = orderid;
    }

    public String getDetName() {
        return detName;
    }

    public void setDetName(String detName) {
        this.detName = detName;
    }

    public String getVisitMobile() {
        return visitMobile;
    }

    public void setVisitMobile(String visitMobile) {
        this.visitMobile = visitMobile;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDoctorid() {
        return doctorid;
    }

    public void setDoctorid(String doctorid) {
        this.doctorid = doctorid;
    }

    public String getDoctorAcademicTitle() {
        return doctorAcademicTitle;
    }

    public void setDoctorAcademicTitle(String doctorAcademicTitle) {
        this.doctorAcademicTitle = doctorAcademicTitle;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getToDoctorMobile() {
        return toDoctorMobile;
    }

    public void setToDoctorMobile(String toDoctorMobile) {
        this.toDoctorMobile = toDoctorMobile;
    }

    public String getDoctorAcademicTitleName() {
        return doctorAcademicTitleName;
    }

    public void setDoctorAcademicTitleName(String doctorAcademicTitleName) {
        this.doctorAcademicTitleName = doctorAcademicTitleName;
    }

    public String getDoctorHeadUrl() {
        return doctorHeadUrl;
    }

    public void setDoctorHeadUrl(String doctorHeadUrl) {
        this.doctorHeadUrl = doctorHeadUrl;
    }

    public String getTodoctorid() {
        return todoctorid;
    }

    public void setTodoctorid(String todoctorid) {
        this.todoctorid = todoctorid;
    }

    public String getToDoctorAcademicTitle() {
        return toDoctorAcademicTitle;
    }

    public void setToDoctorAcademicTitle(String toDoctorAcademicTitle) {
        this.toDoctorAcademicTitle = toDoctorAcademicTitle;
    }

    public String getToDoctorHeadUrl() {
        return toDoctorHeadUrl;
    }

    public void setToDoctorHeadUrl(String toDoctorHeadUrl) {
        this.toDoctorHeadUrl = toDoctorHeadUrl;
    }

    public String getToDoctorName() {
        return toDoctorName;
    }

    public void setToDoctorName(String toDoctorName) {
        this.toDoctorName = toDoctorName;
    }

    public String getToDoctorAcademicTitleName() {
        return toDoctorAcademicTitleName;
    }

    public void setToDoctorAcademicTitleName(String toDoctorAcademicTitleName) {
        this.toDoctorAcademicTitleName = toDoctorAcademicTitleName;
    }

    public String getToTime() {
        return toTimeStr;
    }

    public void setToTime(String toTime) {
        this.toTimeStr = toTime;
    }

    public String getGotolevel() {
        return gotolevel;
    }

    public void setGotolevel(String gotolevel) {
        this.gotolevel = gotolevel;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getPatientid() {
        return patientid;
    }

    public void setPatientid(String patientid) {
        this.patientid = patientid;
    }

    public String getHospitalName() {
        return hospitalName;
    }

    public void setHospitalName(String hospitalName) {
        this.hospitalName = hospitalName;
    }

    public String getInAndOut() {
        return inAndOut;
    }

    public void setInAndOut(String inAndOut) {
        this.inAndOut = inAndOut;
    }

    public String getVisitName() {
        return visitName;
    }

    public void setVisitName(String visitName) {
        this.visitName = visitName;
    }

    public String getVisitid() {
        return visitid;
    }

    public void setVisitid(String visitid) {
        this.visitid = visitid;
    }

    public String getVisitSex() {
        return visitSex;
    }

    public void setVisitSex(String visitSex) {
        this.visitSex = visitSex;
    }

    public String getIsacceptName() {
        return isAcceptName;
    }

    public void setIsacceptName(String isacceptName) {
        this.isAcceptName = isacceptName;
    }

    public String getIsaccept() {
        return isAccept;
    }

    public void setIsaccept(String isaccept) {
        this.isAccept = isaccept;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getVisitSexName() {
        return visitSexName;
    }

    public void setVisitSexName(String visitSexName) {
        this.visitSexName = visitSexName;
    }

    public String getCyzd() {
        return cyzd;
    }

    public void setCyzd(String cyzd) {
        this.cyzd = cyzd;
    }

    public String getLeaveID() {
        return leaveId;
    }

    public void setLeaveID(String leaveID) {
        this.leaveId = leaveID;
    }

    public String getOtherparam1() {
        return otherParam1;
    }

    public void setOtherparam1(String otherparam1) {
        this.otherParam1 = otherparam1;
    }

    public String getOtherParam() {
        return otherParam;
    }

    public void setOtherParam(String otherParam) {
        this.otherParam = otherParam;
    }
}
