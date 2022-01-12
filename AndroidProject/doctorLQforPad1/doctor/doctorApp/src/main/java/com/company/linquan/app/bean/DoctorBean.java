package com.company.linquan.app.bean;

import com.company.linquan.app.util.CharacterParser;

public class DoctorBean {

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

	private String id;
	private String myname;
	private String mobile;
	private String departmentid;
	private String departmentName;
	private String academicTitleName;
	private String wyyID;
	private String headurl;

	private boolean isSelected;

	public DoctorBean(String myname, String id, String mobile, String departmentid, String departmentName, String academicTitleName, String wyyID, String headurl, int type) {
		CharacterParser parser = CharacterParser.getInstance();
		this.item_type = type;
		this.id = id;
		this.myname = myname;
		this.mobile = mobile;
		this.departmentid = departmentid;
		this.departmentName = departmentName;
		this.academicTitleName = academicTitleName;
		this.wyyID = wyyID;
		this.headurl = headurl;

		this.item_en = parser.getSelling(myname).toUpperCase().trim();
		if(!item_en.matches("[A-Z]+")){
			item_en = "#"+item_en;
		}
	}


	public DoctorBean() {

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

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMyname() {
		return myname;
	}

	public void setMyname(String myname) {
		this.myname = myname;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getDepartmentid() {
		return departmentid;
	}

	public void setDepartmentid(String departmentid) {
		this.departmentid = departmentid;
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
		return wyyID;
	}

	public void setWyyID(String wyyID) {
		this.wyyID = wyyID;
	}

	public String getHeadurl() {
		return headurl;
	}

	public void setHeadurl(String headurl) {
		this.headurl = headurl;
	}

	public boolean isSelected() {
		return isSelected;
	}

	public void setSelected(boolean selected) {
		isSelected = selected;
	}
}
