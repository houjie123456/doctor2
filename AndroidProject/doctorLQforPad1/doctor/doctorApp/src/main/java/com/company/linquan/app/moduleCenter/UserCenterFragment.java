package com.company.linquan.app.moduleCenter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.company.linquan.app.R;
import com.company.linquan.app.base.BaseFragment;
import com.company.linquan.app.bean.AuthStatusBean;
import com.company.linquan.app.config.C;
import com.company.linquan.app.moduleAuth.AuthActivity;
import com.company.linquan.app.moduleCenter.ui.AccountActivity;
import com.company.linquan.app.moduleCenter.ui.FeedbackActivity;
import com.company.linquan.app.moduleCenter.ui.MeActivity;
import com.company.linquan.app.moduleCenter.ui.MyMeetingActivity;
import com.company.linquan.app.moduleHome.HomeActivity;
import com.company.linquan.app.moduleLogin.LoginActivity;
import com.company.linquan.app.moduleWeb.WebActivity;
import com.company.linquan.app.moduleWeb.WebActivityHome;
import com.company.linquan.app.util.MyDialog;
import com.company.linquan.app.util.MyToast;
import com.company.linquan.app.util.ToolSharePerference;
import com.company.linquan.app.view.DividerItemDecoration;
import com.company.linquan.app.view.MyTextView;
import com.company.linquan.app.view.RoundImageView;

import static com.company.linquan.app.config.C.Url_IP;

/**
 * Created by YC on 2018/6/18.
 */

public class UserCenterFragment extends BaseFragment implements UserCenterInterface.ViewInterface,View.OnClickListener{
    private Dialog myDialog;

    private RecyclerView recyclerView;
    private SparseArray<SparseArray<String>> array;
    private MyListAdapter adapter;

    private MyTextView authTV;
    private final static int AUTH = 1,AUTH_LOGIN = 2;

    private UserCenterPresenter presenter;
    private RoundImageView photoIV;
    private MyTextView nameTV,moneyTV,scoreTV;
    private LinearLayout moneyLL, scoreLL;
    private String isAuth="",checkState = "";
    int sw;

    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_user_center, null);
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
        s1.put(1,R.drawable.img_user_center_me+"");
        array.put(0,s1);

        SparseArray<String> s2 = new SparseArray<>();
        s2.put(0,"直播会议");
        s2.put(1,R.drawable.img_user_center_meeting+"");
        array.put(1,s2);

        SparseArray<String> s3 = new SparseArray<>();
        s3.put(0,"用户反馈");
        s3.put(1,R.drawable.img_user_center_feekback+"");
        array.put(2,s3);

        SparseArray<String> s4 = new SparseArray<>();
        s4.put(0,"去好评");
        s4.put(1,R.drawable.img_user_center_agree+"");
        array.put(3,s4);


        SparseArray<String> s5 = new SparseArray<>();
        s5.put(0,"用户设置");
        s5.put(1,R.drawable.img_user_center_setting+"");
        array.put(4,s5);

        SparseArray<String> s6 = new SparseArray<>();
        s6.put(0,"积分商城");
        s6.put(1,R.drawable.img_user_center_order+"");
        array.put(5,s6);

    }

    private void initView(View view){
        presenter = new UserCenterPresenter(this);

        sw = getContext().getResources().getDisplayMetrics().widthPixels;

        authTV = (MyTextView)view.findViewById(R.id.user_center_auth) ;
        authTV.setOnClickListener(this);

        nameTV = (MyTextView)view.findViewById(R.id.user_center_name) ;
        moneyLL = (LinearLayout)view.findViewById(R.id.user_center_money) ;
        moneyLL.setOnClickListener(this);
        moneyTV=view.findViewById(R.id.user_center_money_txt);
        scoreLL = (LinearLayout)view.findViewById(R.id.user_center_score) ;
        scoreLL.setOnClickListener(this);
        scoreTV=view.findViewById(R.id.user_center_score_txt);
        photoIV =  (RoundImageView) view.findViewById(R.id.user_center_image) ;
        photoIV.setDrawCircle();
        photoIV.setOnClickListener(this);

        recyclerView = (RecyclerView)view.findViewById(R.id.user_center_recycler);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),3,GridLayoutManager.VERTICAL, false));
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
                        startActivity(new Intent(getActivity(), MyMeetingActivity.class));
                        break;
                    case 2:
                        startActivity(new Intent(getActivity(), FeedbackActivity.class));
                        break;
                    case 3:
                        break;
                    case 4:
                        startActivity(new Intent(getActivity(), AccountActivity.class));
                        break;
                    case 5:
                        showToast("功能建设中");
                        break;
                }
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.user_center_auth:

                if ("".equals(ToolSharePerference.getStringData(getContext(), C.fileconfig,C.UserID))){
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
                startActivity(new Intent(getActivity(),AccountActivity.class));
                break;

            case R.id.user_center_money:
                Intent intent = new Intent(getActivity(),WebActivityHome.class);
                Bundle bundle = new Bundle();
                bundle.putString("title","我的钱包");
                bundle.putString("url",Url_IP + "doctorProject/pages/doctor_wallet.html?personID=" + ToolSharePerference.getStringData(view.getContext(), C.fileconfig,C.UserID));
                intent.putExtras(bundle);
                startActivity(intent);
                break;

            case R.id.user_center_score:
                Intent intent2 = new Intent(getActivity(),WebActivity.class);
                Bundle bundle2 = new Bundle();
                bundle2.putString("title","我的积分");
                bundle2.putString("url",Url_IP + "doctorProject/pages/integral.html?personID=" + ToolSharePerference.getStringData(view.getContext(), C.fileconfig,C.UserID));
                intent2.putExtras(bundle2);
                startActivity(intent2);
                break;

//            case R.id.user_center_score:
//                Intent intent = new Intent(getActivity(),WebActivity.class);
//                Bundle bundle = new Bundle();
//                bundle.putString("title","我的积分");
//                bundle.putString("url"," https://test.zhenyuetech.com/doctorProject/pages/integral.html");
//                intent.putExtras(bundle);
//                startActivity(intent);
//                break;


        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == AUTH_LOGIN && resultCode == getActivity().RESULT_OK){
            if (!"1".equals(ToolSharePerference.getStringData(getContext(), C.fileconfig,C.AUTH))
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
    public void reloadView(AuthStatusBean bean) {
//        isAuth = bean.getCheckState();
        checkState = bean.getCheckState();
        ToolSharePerference.putStringData(getActivity(), C.fileconfig,C.AUTH,checkState);
        if ("0".equals(checkState)){
//            if ("3".equals(checkState)){
                authTV.setText("实名认证审核中");

//            }else{
//                authTV.setText("暂未实名认证，去认证>");
//            }
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
        if(bean.getMyBalance()!=null||bean.getMyBalance()!=""||bean.getMyBalance()!="0"){
            moneyTV.setText(bean.getMyBalance());
        }else {
            moneyTV.setText("0");
        }
        if(bean.getMyScore()!=null||bean.getMyScore()!=""||bean.getMyScore()!="0"){
            scoreTV.setText(bean.getMyScore());
        }else {
            scoreTV.setText("0");
        }


        ToolSharePerference.putStringData(getContext(),C.fileconfig,C.UserName,bean.getMyName());
        ToolSharePerference.putStringData(getContext(),C.fileconfig,C.Photo,bean.getHeadUrl());
    }
}
