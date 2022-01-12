package com.company.linquan.app.moduleWork.imp;

import com.company.linquan.app.bean.MemberBean;
import com.company.linquan.app.moduleWork.WorkInterface;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Created by YC on 2018/7/15.
 */

public class MemberListPresenterImp implements WorkInterface.MemberListPresenterInterface {

    private WorkInterface.MemberListInterface view;

    public MemberListPresenterImp(WorkInterface.MemberListInterface view) {
        this.view = view;
    }

    @Override
    public void getMember(String json) {
        ArrayList<MemberBean> temp = new ArrayList<>();
        Gson gson = new Gson();
        Type listType = new TypeToken<ArrayList<MemberBean>>(){}.getType();
        temp = gson.fromJson(json, listType);
        view.reloadList(temp);
    }
}

