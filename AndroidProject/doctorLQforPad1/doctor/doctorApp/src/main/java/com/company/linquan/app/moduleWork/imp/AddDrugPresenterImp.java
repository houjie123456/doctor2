package com.company.linquan.app.moduleWork.imp;

import com.company.linquan.app.moduleWork.WorkInterface;

/**
 * Created by YC on 2018/7/1.
 */

public class AddDrugPresenterImp implements WorkInterface.AddDrugPresenterInterface {

    private WorkInterface.AddDrugsInterface view;

    public AddDrugPresenterImp(WorkInterface.AddDrugsInterface view) {
        this.view = view;
    }

    @Override
    public void addDrug(String patientID, String remark, String json) {

    }
}
