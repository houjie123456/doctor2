package com.company.linquan.app.http;

import com.company.linquan.app.bean.SelectDataBean;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class JSONSelectDoc {
        private String code;
        @SerializedName("msgbox")
        private String msgBox;
        private ArrayList<SelectDataBean> departmentList;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getMsgBox() {
            return msgBox;
        }

        public void setMsgBox(String msgBox) {
            this.msgBox = msgBox;
        }

        public ArrayList<SelectDataBean> getTable() {
            return departmentList;
        }

        public void setTable(ArrayList<SelectDataBean> departmentList) {
            this.departmentList = departmentList;
        }


}
