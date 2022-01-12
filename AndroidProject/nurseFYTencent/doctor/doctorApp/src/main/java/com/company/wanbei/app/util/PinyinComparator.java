package com.company.wanbei.app.util;

import com.company.wanbei.app.bean.DoctorBean;

import java.util.Comparator;


public class PinyinComparator implements Comparator<DoctorBean>{
	public int compare(DoctorBean o1, DoctorBean o2){
		if (o1.getItem_en().equals("@")
				|| o2.getItem_en().equals("#")){
			return -1;
		} else if (o1.getItem_en().equals("#")
				|| o2.getItem_en().equals("@")) {
			return 1;
		} else {
			return o1.getItem_en().compareTo(o2.getItem_en());
		}
	}

}
