package com.company.linquan.app.wxapi;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;


import com.company.linquan.app.bean.BusWXBean;
import com.company.linquan.app.config.C;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.ShowMessageFromWX;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by YC on 2017/9/13.
 */

public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {
    private IWXAPI api;
    private Dialog myDialog;
    private String orderId = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 通过WXAPIFactory工厂，获取IWXAPI的实例
        api = WXAPIFactory.createWXAPI(this, C.W_APPID);
//        api.registerApp(C.W_APPID);
        //注意：
        //第三方开发者如果使用透明界面来实现WXEntryActivity，需要判断handleIntent的返回值，如果返回值为false，则说明入参不合法未被SDK处理，应finish当前透明界面，避免外部通过传递非法参数的Intent导致停留在透明界面，引起用户的疑惑
        try {
            api.handleIntent(getIntent(), this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq req) {
//        switch (req.getType()) {
//            case ConstantsAPI.COMMAND_GETMESSAGE_FROM_WX:
//                goToGetMsg();
//                break;
//            case ConstantsAPI.COMMAND_SHOWMESSAGE_FROM_WX:
//                goToShowMsg((ShowMessageFromWX.Req) req);
//                break;
//            default:
//                break;
//        }
    }

    @Override
    public void onResp(BaseResp resp) {
        if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
            EventBus.getDefault().post(new BusWXBean(resp.errCode));
            finish();
        }
//        switch (resp.errCode) {
//            case BaseResp.ErrCode.ERR_OK:
//                result = "支付成功";
//                checkOrder();
//                break;
//            case BaseResp.ErrCode.ERR_USER_CANCEL:
//                result = "取消支付";
//                Toast.makeText(this, result, Toast.LENGTH_SHORT).show();
//                finish();
//                break;
//            case BaseResp.ErrCode.ERR_AUTH_DENIED:
//                result = "权限不足";
//                break;
//            case BaseResp.ErrCode.ERR_UNSUPPORT:
//                result = "不支持";
//                break;
//            default:
//                result = "未知错误";
//                break;
//        }
    }

    private void goToGetMsg() {
    }

    private void goToShowMsg(ShowMessageFromWX.Req showReq) {
    }

}
