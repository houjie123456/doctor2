package com.company.linquan.app.moduleDoc;

import com.company.linquan.app.base.BaseViewInterface;
import com.company.linquan.app.bean.DocBean;

import java.util.ArrayList;

/**
 * Created by YC on 2018/8/1.
 */

public class DocInterface {

    public interface DocInterFace extends BaseViewInterface{
        void reloadList(ArrayList<DocBean> list);
    }

    public interface DocDetailInterFace extends BaseViewInterface{
        void reloadView(DocBean list);
    }

    public interface DocPresenterInterFace{
        void getListData(int page);
    }

    public interface DocDetailPresenterInterFace{
        void getData(String id);
    }
}
