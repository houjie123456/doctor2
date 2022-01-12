package com.company.wanbei.app.moduleCenter;

import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.company.wanbei.app.BuildConfig;
import com.company.wanbei.app.R;
import com.company.wanbei.app.base.BaseFragment;
import com.company.wanbei.app.bean.UserInfoBean;
import com.tencent.qcloud.tuikit.tuichat.fromApp.config.C;
import com.company.wanbei.app.http.JSONBean;
import com.company.wanbei.app.moduleAuth.AuthActivity;
import com.company.wanbei.app.moduleCenter.ui.AccountActivity;
import com.company.wanbei.app.moduleCenter.ui.MeActivity;
import com.company.wanbei.app.moduleCenter.ui.MyMoneyActivity;
import com.company.wanbei.app.moduleLogin.LoginActivity;
import com.tencent.qcloud.tuikit.tuichat.fromApp.util.MyDialog;
import com.tencent.qcloud.tuikit.tuichat.fromApp.util.MyToast;
import com.tencent.qcloud.tuikit.tuichat.fromApp.util.ToolSharePerference;
import com.tencent.qcloud.tuikit.tuichat.fromApp.view.MyTextView;
import com.tencent.qcloud.tuikit.tuichat.fromApp.view.RoundImageView;

//import androidx.recyclerview.widget.DefaultItemAnimator;
//import androidx.recyclerview.widget.GridLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by YC on 2018/6/18.
 */

public class UserCenterNurseFragment extends BaseFragment implements UserCenterInterface.ViewInterface,View.OnClickListener{
    private Dialog myDialog;

    private RecyclerView recyclerView;
    private SparseArray<SparseArray<String>> array;
    private MyListAdapter adapter;

    private MyTextView authTV;
    private final static int AUTH = 1,AUTH_LOGIN = 2;

    private UserCenterPresenter presenter;
    private RoundImageView photoIV;
    private MyTextView nameTV,profitTV,settledTV,balanceTV;
    private LinearLayout profitLL, settledLL,balanceLL;
    private String isAuth="",checkState = "";
    int sw;

//    profit：总收益
//    settled：已结算
//    balance：账户余额

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_user_center_nurse, null);
        initGridData(0);
        initView(view);
        setListener();
        return view;
    }

    @Override
    public void sureBtnClick() {
        startActivity(new Intent(getActivity(), LoginActivity.class));
        super.sureBtnClick();
    }

    @Override
    public void cancelBtnClick() {
        super.cancelBtnClick();
    }

    @Override
    protected void lazyLoad() {
        if(getActivity() == null){
            return;
        }
        presenter.getPersonInfo();
        presenter.getMyMoney();
    }

    @Override
    public void onResume() {
        lazyLoad();
        super.onResume();
    }

    private void initGridData(int index){
        array = new SparseArray<>();
        SparseArray<String> s1 = new SparseArray<>();
        s1.put(0,"我的名片");
        s1.put(1,R.drawable.img_user_center_me_nurse+"");
        array.put(0,s1);

        SparseArray<String> s2 = new SparseArray<>();
        s2.put(0,"去好评");
        s2.put(1,R.drawable.img_user_center_store_nurse+"");
        array.put(1,s2);

        SparseArray<String> s3 = new SparseArray<>();
        s3.put(0,"用户设置");
        s3.put(1,R.drawable.img_user_center_account_nurse+"");
        array.put(2,s3);

    }

    private void initView(View view){
        presenter = new UserCenterPresenter(this);

        sw = getContext().getResources().getDisplayMetrics().widthPixels;

        authTV = (MyTextView)view.findViewById(R.id.user_center_auth) ;
        authTV.setOnClickListener(this);

        nameTV = (MyTextView)view.findViewById(R.id.user_center_name) ;

        profitLL = (LinearLayout)view.findViewById(R.id.user_center_profit) ;
        profitLL.setOnClickListener(this);
        profitTV=view.findViewById(R.id.user_center_profit_txt);

        settledLL = (LinearLayout)view.findViewById(R.id.user_center_settled) ;
        settledLL.setOnClickListener(this);
        settledTV=view.findViewById(R.id.user_center_settled_txt);

        balanceLL = (LinearLayout)view.findViewById(R.id.user_center_balance) ;
        balanceLL.setOnClickListener(this);
        balanceTV=view.findViewById(R.id.user_center_balance_txt);

        photoIV =  (RoundImageView) view.findViewById(R.id.user_center_image) ;
        photoIV.setDrawCircle();
        photoIV.setOnClickListener(this);

        recyclerView = (RecyclerView)view.findViewById(R.id.user_center_recycler);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),3,RecyclerView.VERTICAL, false));
        //添加Android自带的分割线
//        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(),DividerItemDecoration.VERTICAL_LIST));
        adapter = new MyListAdapter(getContext(),array);
        recyclerView.setAdapter(adapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    private void setListener() {
        adapter.setOnItemClickListener(new OnItemClick() {
            @Override
            public void onItemClick(View view, int position) {
                    switch (position){
                        case 0:
                            startActivity(new Intent(getActivity(), MeActivity.class));
                            break;
                        case 1:
                            String market = "market://details?id=" + BuildConfig.APPLICATION_ID;
                            Uri uri = Uri.parse(market);
                            Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
                            goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                                    Intent.FLAG_ACTIVITY_NEW_DOCUMENT |
                                    Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                            try {
                                startActivity(goToMarket);
                            } catch (ActivityNotFoundException e) {
                                String url = "http://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID;
                                startActivity(new Intent(Intent.ACTION_VIEW,
                                        Uri.parse(url)));
                            }
                            break;
                        case 2:
                            startActivity(new Intent(getActivity(), AccountActivity.class));
                            break;

                    }

            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.user_center_auth:

                if ("".equals(ToolSharePerference.getStringData(getContext(), C.fileconfig, C.UserID))){
                    startActivityForResult(new Intent(getActivity(), LoginActivity.class),AUTH_LOGIN);
                   return;
                }

                if ("3".equals(checkState)){
                    showOneBtnDialog("您的认证已提交申请，请耐心等待审核，我们将在3个工作日内完成",true);
                    return;
                }
                startActivityForResult(new Intent(getActivity(), AuthActivity.class),AUTH);
                break;

            case R.id.user_center_image:
                startActivity(new Intent(getActivity(), AccountActivity.class));
                break;

            case R.id.user_center_profit:
                Intent intent = new Intent(getActivity(),MyMoneyActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("type","profit");
                intent.putExtras(bundle);
                startActivity(intent);
//                bundle.putString("url",Url_IP + "doctorProject/pages/doctor_wallet.html?personID=" + ToolSharePerference.getStringData(view.getContext(), C.fileconfig,C.UserID));
                break;

            case R.id.user_center_settled:
                Intent intent2 = new Intent(getActivity(),MyMoneyActivity.class);
                Bundle bundle2 = new Bundle();
                bundle2.putString("type","settled");
                intent2.putExtras(bundle2);
                startActivity(intent2);
//                bundle2.putString("url",Url_IP + "doctorProject/pages/integral.html?personID=" + ToolSharePerference.getStringData(view.getContext(), C.fileconfig,C.UserID));
                break;

            case R.id.user_center_balance:
                Intent intent3 = new Intent(getActivity(),MyMoneyActivity.class);
                Bundle bundle3 = new Bundle();
                bundle3.putString("type","balance");
                intent3.putExtras(bundle3);
                startActivity(intent3);
                break;


        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == AUTH_LOGIN && resultCode == getActivity().RESULT_OK){
            if (!"1".equals(ToolSharePerference.getStringData(getContext(), C.fileconfig, C.AUTH))
                    && !"3".equals(checkState)){
                startActivityForResult(new Intent(getActivity(), AuthActivity.class),AUTH);
                return;
            }
        }

        if(requestCode == AUTH && resultCode == getActivity().RESULT_OK ){

        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * recycler 适配器
     */
    private class MyListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

        private Context mContext;
        private SparseArray<SparseArray<String>> mList;
        private OnItemClick onItemClickListener;

        public MyListAdapter(Context context,SparseArray<SparseArray<String>> list ){
            this.mContext = context;
            this.mList = list;
        }


        public void setList(SparseArray<SparseArray<String>> list){
            this.mList = list;
            notifyDataSetChanged();
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(mContext)
                    .inflate(R.layout.grid_item_user_center,parent,false);
            return new MyViewHolder(view,onItemClickListener);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            if(holder instanceof MyViewHolder){
                initView((MyViewHolder) holder, mList.get(position));
            }
        }

        @Override
        public int getItemCount() {
            return mList.size();
        }

        private void setOnItemClickListener(OnItemClick listener){
            onItemClickListener = listener;
        }

        private void initView(MyViewHolder handler, SparseArray<String> bean){
            handler.imageView.setBackgroundResource(Integer.parseInt(bean.get(1)));
            handler.titleTV.setText(bean.get(0));
        }
    }

    public interface OnItemClick{
        public void onItemClick(View view, int position);
    }

    /**
     * 静态类
     */
    private static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public ImageView imageView;
        public MyTextView titleTV;
        public MyTextView contentTV;
        public MyTextView moneyTV;
        public MyTextView timeTV;
        private OnItemClick onItemClick;

        public MyViewHolder(View view, OnItemClick onItemClick){
            super(view);
            this.onItemClick = onItemClick;
            view.setOnClickListener(this);
            imageView = (ImageView)view.findViewById(R.id.grid_item_image);
            titleTV = (MyTextView) view.findViewById(R.id.grid_item_name);
        }

        @Override
        public void onClick(View view) {
            if(onItemClick != null){
                onItemClick.onItemClick(view,getLayoutPosition());
            }
        }
    }

    @Override
    public void finishActivity() {

    }

    @Override
    public Context getContext() {
        return getActivity();
    }

    @Override
    public void showDialog() {
        if (myDialog == null) myDialog = MyDialog.showLoadingDialog(getActivity());
    }
    @Override
    public void dismissDialog() {
        if (myDialog != null) myDialog.dismiss();
    }

    @Override
    public void showToast(String txt) {
        MyToast.showToast(getActivity(),txt, Toast.LENGTH_SHORT);
    }

    @Override
    public void reloadView(UserInfoBean bean) {
        isAuth = bean.getIsAuthenticat();
        checkState = bean.getCheckState();
        ToolSharePerference.putStringData(getActivity(), C.fileconfig, C.AUTH,checkState);
        if ("0".equals(checkState)){
            if ("0".equals(isAuth)){
                authTV.setText("暂未实名认证，去认证>");
            }else{
                authTV.setText("实名认证已提交");
            }
        }else if ("1".equals(checkState)){
            authTV.setText("已实名认证");
        }else if("3".equals(checkState)){
            authTV.setText("实名认证审核中");
        }else {
            authTV.setText("审核不通过："+bean.getCheckRemark());
        }

        int w2 = (134*sw)/720;
        int h2 = (134*sw)/720;
//        Glide.with(getContext()).load(bean.getHeadUrl()).override(w2,h2).centerCrop().into(photoIV);
        Glide.with(getContext()).load(bean.getHeadUrl()).apply(new RequestOptions().override(w2,h2).centerCrop()).into(photoIV);

        nameTV.setText(bean.getMyName());

        ToolSharePerference.putStringData(getContext(), C.fileconfig, C.UserName,bean.getMyName());
        ToolSharePerference.putStringData(getContext(), C.fileconfig, C.Photo,bean.getHeadUrl());
    }

    @Override
    public void reloadMyMoney(JSONBean bean) {
        if(bean.getProfit()!=null&&bean.getProfit()!=""&&bean.getProfit()!="0"){
            profitTV.setText(bean.getProfit());
        }else {
            profitTV.setText("0");
        }
        if(bean.getSettled()!=null&&bean.getSettled()!=""&&bean.getSettled()!="0"){
            settledTV.setText(bean.getSettled());
        }else {
            settledTV.setText("0");
        }
        if(bean.getBalance()!=null&&bean.getBalance()!=""&&bean.getBalance()!="0"){
            balanceTV.setText(bean.getBalance());
        }else {
            balanceTV.setText("0");
        }
    }
}
