package com.company.linquan.app.base;

import android.app.Dialog;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;

import com.company.linquan.app.R;
import com.company.linquan.app.config.C;
import com.company.linquan.app.util.ToolSharePerference;
import com.company.linquan.app.view.MyTextView;

/**
 * Created by YC on 2016/10/12.
 */

public abstract class BaseFragment extends Fragment {

    /** Fragment当前状态是否可见 */
    protected boolean isVisible;
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if(getUserVisibleHint()) {
            isVisible = true;
            onVisible();
        } else {
            isVisible = false;
            onInvisible();
        }
    }
    /**
     * 可见
     */
    protected void onVisible() {
        lazyLoad();
    }
    /**
     * 不可见
     */
    protected void onInvisible() {}
    /**
     * 延迟加载
     * 子类必须重写此方法
     */
    protected abstract void lazyLoad();



    private void delayLogin() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                isLogin();
            }
        },2000);
    }

    public void isLogin(){
        if (!"1".equals(ToolSharePerference.getStringData(getActivity(), C.fileconfig,C.AUTH)))
        {
            showTwoBtnDialog("检测到您还没有实名认证，请实名认证！",false);
        }
    }

    /**
     * 提示登录
     */
    public void showTwoBtnDialog(String txt,boolean canCancel){
        final Dialog mDialog = new Dialog(getActivity(), R.style.custom_dialog);
        final View mDialogContentView= LayoutInflater.from(getActivity()).inflate(R.layout.dialog_item_error,null);

        MyTextView cameraTV = (MyTextView)mDialogContentView.findViewById(R.id.dialog_item_cancel);
        MyTextView albumTV = (MyTextView)mDialogContentView.findViewById(R.id.dialog_item_sure);

        MyTextView contentTV = (MyTextView)mDialogContentView.findViewById(R.id.dialog_item_txt);
        contentTV.setText(txt);

        cameraTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDialog.dismiss();
                cancelBtnClick();
            }
        });

        albumTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDialog.dismiss();
                sureBtnClick();
            }
        });

        mDialog.setContentView(mDialogContentView);
        if (canCancel){
            mDialog.setCanceledOnTouchOutside(true);
            mDialog.setCancelable(true);
        }else{
            mDialog.setCanceledOnTouchOutside(false);
            mDialog.setCancelable(false);
        }
        mDialog.getWindow().setGravity(Gravity.CENTER);
        WindowManager.LayoutParams p = mDialog.getWindow().getAttributes(); // 获取对话框当前的参数值
        p.width = (int) (getResources().getDisplayMetrics().widthPixels * 0.85); // 宽度设置为屏幕的0.85
        mDialog.getWindow().setAttributes(p);
        mDialog.show();
        mDialog.getWindow().setWindowAnimations(R.style.anim_dialog);
    }


    public void sureBtnClick(){

    }

    public void cancelBtnClick(){

    }


    /**
     * 提示登录
     */
    public void showOneBtnDialog(String txt,boolean canCancel){
        final Dialog mDialog = new Dialog(getActivity(), R.style.custom_dialog);
        final View mDialogContentView= LayoutInflater.from(getActivity()).inflate(R.layout.dialog_item_error,null);

        MyTextView cameraTV = (MyTextView)mDialogContentView.findViewById(R.id.dialog_item_cancel);
        MyTextView albumTV = (MyTextView)mDialogContentView.findViewById(R.id.dialog_item_sure);
        albumTV.setVisibility(View.GONE);
        MyTextView contentTV = (MyTextView)mDialogContentView.findViewById(R.id.dialog_item_txt);
        contentTV.setText(txt);

        cameraTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDialog.dismiss();
                cancelBtnClick();
            }
        });

        albumTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDialog.dismiss();
                sureBtnClick();
            }
        });

        mDialog.setContentView(mDialogContentView);
        if (canCancel){
            mDialog.setCanceledOnTouchOutside(true);
            mDialog.setCancelable(true);
        }else{
            mDialog.setCanceledOnTouchOutside(false);
            mDialog.setCancelable(false);
        }
        mDialog.getWindow().setGravity(Gravity.CENTER);
        WindowManager.LayoutParams p = mDialog.getWindow().getAttributes(); // 获取对话框当前的参数值
        p.width = (int) (getResources().getDisplayMetrics().widthPixels * 0.85); // 宽度设置为屏幕的0.85
        mDialog.getWindow().setAttributes(p);
        mDialog.show();
        mDialog.getWindow().setWindowAnimations(R.style.anim_dialog);
    }

}
