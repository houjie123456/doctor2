
package com.company.wanbei.app.moduleWeb;

import com.tencent.qcloud.tuikit.tuichat.fromApp.base.BaseViewInterface;

/**
 * Created by YC on 2018/1/9.
 */

public class UploadInterface {
    public interface ViewInterface extends BaseViewInterface {
        void initPath(String path, String urlId);
    }
    public interface PresenterInterface{
        void upLoad(String personID, String path, int type);
    }

}
