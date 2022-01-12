package com.company.wanbei.app.bean;

import java.util.ArrayList;

/**
 * Created by YC on 2018/8/1.
 */

public class DeptBean {
    private String deptName;// ：科室名称
    private String deptId;// ：科室id
    private String deptRow;// ：科室行数
    private ArrayList<DocBean> docArray;// ：医生数组

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getDeptId() {
        return deptId;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }

    public String getDeptRow() {
        return deptRow;
    }

    public void setDeptRow(String deptRow) {
        this.deptRow = deptRow;
    }

    public ArrayList<DocBean> getDocArray() {
        return docArray;
    }

    public void setDocArray(ArrayList<DocBean> docArray) {
        this.docArray = docArray;
    }
}
