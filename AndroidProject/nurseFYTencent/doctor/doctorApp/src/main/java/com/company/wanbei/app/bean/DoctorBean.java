package com.company.wanbei.app.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.company.wanbei.app.util.CharacterParser;

import java.io.Serializable;
import java.util.ArrayList;

public class DoctorBean implements Serializable {

	public static final int TYPE_CHARACTER = 0;
	public static final int TYPE_DATA = 1;
	private int item_type;
	private String item_en;

//	id ：医生id
//	myname：医生姓名
//	mobile：手机号
//	departmentid：科室ID
//	departmentName：科室名称
//	academicTitleName:：职称
//	wyyID：网易云ID
//	headurl：头像

	private String dutyTime;

	private String id;
	private String myName;
	private String mobile;
	private String departmentId;
	private String departmentName;
	private String academicTitleName;
	private String wyyId;
	private String headUrl;
	private String personRemark;
	private String hospitalName;

	private String deptName;// ：科室名称
	private String docName;// ：医生名称
	private String sex;// ：性别
	private String docTitle;// ：职称


	private String doctorId;// ：医生姓名
	private String doctorName;// ：医生姓名
	private String doctorSex;// ：医生性别
	private String doctorMobile;// ：医生手机号
	private String patientCount;// ：接诊人数
	private ArrayList<PatientBean> patientArray;// -- 患者信息json数组

	private boolean isSelected;

	public DoctorBean(String myname, String id,  String mobile, String departmentid, String departmentName, String academicTitleName, String wyyID, String headurl,int type) {
		CharacterParser parser = CharacterParser.getInstance();
		this.item_type = type;
		this.id = id;
		this.myName = myname;
		this.mobile = mobile;
		this.departmentId = departmentid;
		this.departmentName = departmentName;
		this.academicTitleName = academicTitleName;
		this.wyyId = wyyID;
		this.headUrl = headurl;

		this.item_en = parser.getSelling(myname).toUpperCase().trim();
		if(!item_en.matches("[A-Z]+")){
			item_en = "#"+item_en;
		}
	}


	public DoctorBean() {

	}

	protected DoctorBean(Parcel in) {
		item_type = in.readInt();
		item_en = in.readString();
		id = in.readString();
		myName = in.readString();
		mobile = in.readString();
		departmentId = in.readString();
		departmentName = in.readString();
		academicTitleName = in.readString();
		wyyId = in.readString();
		headUrl = in.readString();
		personRemark = in.readString();
		hospitalName = in.readString();
		deptName = in.readString();
		docName = in.readString();
		sex = in.readString();
		docTitle = in.readString();
		doctorId = in.readString();
		doctorName = in.readString();
		doctorSex = in.readString();
		doctorMobile = in.readString();
		patientCount = in.readString();
		isSelected = in.readByte() != 0;
	}


	public int getItem_type(){
		return item_type;
	}
	public void setItem_type(int item_type){
		this.item_type = item_type;
	}
	public String getItem_en(){
		return item_en;
	}
	public void setItem_en(String item_en){
		this.item_en = item_en;
	}


	public String getDutyTime() {
		return dutyTime;
	}

	public void setDutyTime(String dutyTime) {
		this.dutyTime = dutyTime;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMyname() {
		return myName;
	}

	public void setMyname(String myname) {
		this.myName = myname;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getDepartmentid() {
		return departmentId;
	}

	public void setDepartmentid(String departmentid) {
		this.departmentId = departmentid;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public String getAcademicTitleName() {
		return academicTitleName;
	}

	public void setAcademicTitleName(String academicTitleName) {
		this.academicTitleName = academicTitleName;
	}

	public String getWyyID() {
		return wyyId;
	}

	public void setWyyID(String wyyID) {
		this.wyyId = wyyID;
	}

	public String getHeadurl() {
		return headUrl;
	}

	public void setHeadurl(String headurl) {
		this.headUrl = headurl;
	}

	public boolean isSelected() {
		return isSelected;
	}

	public void setSelected(boolean selected) {
		isSelected = selected;
	}

	public String getPersonRemark() {
		return personRemark;
	}

	public void setPersonRemark(String personRemark) {
		this.personRemark = personRemark;
	}

	public String getHospitalName() {
		return hospitalName;
	}

	public void setHospitalName(String hospitalName) {
		this.hospitalName = hospitalName;
	}


	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getDocName() {
		return docName;
	}

	public void setDocName(String docName) {
		this.docName = docName;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getDocTitle() {
		return docTitle;
	}

	public void setDocTitle(String docTitle) {
		this.docTitle = docTitle;
	}

	public String getDoctorId() {
		return doctorId;
	}

	public void setDoctorId(String doctorId) {
		this.doctorId = doctorId;
	}

	public String getDoctorName() {
		return doctorName;
	}

	public void setDoctorName(String doctorName) {
		this.doctorName = doctorName;
	}

	public String getDoctorSex() {
		return doctorSex;
	}

	public void setDoctorSex(String doctorSex) {
		this.doctorSex = doctorSex;
	}

	public String getDoctorMobile() {
		return doctorMobile;
	}

	public void setDoctorMobile(String doctorMobile) {
		this.doctorMobile = doctorMobile;
	}

	public String getPatientCount() {
		return patientCount;
	}

	public void setPatientCount(String patientCount) {
		this.patientCount = patientCount;
	}

	public ArrayList<PatientBean> getPatientArray() {
		return patientArray;
	}

	public void setPatientArray(ArrayList<PatientBean> patientArray) {
		this.patientArray = patientArray;
	}

}
