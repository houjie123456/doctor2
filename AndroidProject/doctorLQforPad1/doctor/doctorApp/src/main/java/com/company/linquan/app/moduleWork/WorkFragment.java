package com.company.linquan.app.moduleWork;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.company.linquan.app.R;
import com.company.linquan.app.base.BaseFragment;
import com.company.linquan.app.bean.UserInfoBean;
import com.company.linquan.app.config.C;
import com.company.linquan.app.moduleWeb.WebActivity;
import com.company.linquan.app.moduleWork.ui.CreateTeamActivity;
import com.company.linquan.app.moduleWork.ui.PrivateDoctorActivity;
import com.company.linquan.app.moduleWork.ui.TeamDetailActivity;
import com.company.linquan.app.moduleWork.ui.WorkChangeActivity;
import com.company.linquan.app.moduleWork.ui.WorkFaceActivity;
import com.company.linquan.app.moduleWork.ui.WorkFreeActivity;
import com.company.linquan.app.moduleWork.ui.WorkNurseActivity;
import com.company.linquan.app.moduleWork.ui.WorkPatientActivity;
import com.company.linquan.app.moduleWork.ui.WorkPictureActivity;
import com.company.linquan.app.moduleWork.ui.WorkRecipeActivity;
import com.company.linquan.app.util.MyDialog;
import com.company.linquan.app.util.MyToast;
import com.company.linquan.app.util.ToolSharePerference;
import com.company.linquan.app.view.MyTextView;

import static com.company.linquan.app.config.C.Url_IP;

/**
 * Created by YC on 2018/6/7.
 */

public class WorkFragment extends BaseFragment implements WorkInterface.ViewInterface{

    private Dialog myDialog;

    private RecyclerView recyclerView;
    private SwipeRefreshLayout refreshLayout;
    private SparseArray<SparseArray<String>> array;
    private MyListAdapter adapter;

    private WorkPresenter presenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_work, null);

        //fragment的布局文件
//        View view = inflater.inflate(R.layout.fragment_home_work, container, false);
//        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        view.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        initView(view);
        if(UserInfoBean.getInstance().getIdType().equals("1")){
            setListener2();
        }else {
            setListener();
        }
        return view;
    }


    @Override
    protected void lazyLoad() {
        if(getActivity() == null){
            return;
        }
        presenter.getGridData();
    }


    private void initView(View view){
        presenter = new WorkPresenter(this);
        refreshLayout = (SwipeRefreshLayout)view.findViewById(R.id.work_refresh);
        refreshLayout.setEnabled(false);
        refreshLayout.setColorSchemeColors(ContextCompat.getColor(getActivity(),R.color.base_red_color));
        recyclerView = (RecyclerView)view.findViewById(R.id.work_recycler);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),3,GridLayoutManager.VERTICAL, false));
        array = new SparseArray<>();
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
                        startActivity(new Intent(getActivity(), WorkFaceActivity.class));
                        break;
                    case 1:
                        startActivity(new Intent(getActivity(), WorkPictureActivity.class));
                        break;
                    case 2:
                        showToast("功能建设中");
//                        startActivity(new Intent(getActivity(), WorkVoiceActivity.class));
                        break;
                    case 3:
                        showToast("功能建设中");
//                        startActivity(new Intent(getActivity(), WorkVideoActivity.class));
                        break;
                    case 4:
                        if (!"1".equals(ToolSharePerference.getStringData(getActivity(), C.fileconfig,C.AUTH))){
                            showToast("请先进行实名认证");
                        }else {
                            startActivity(new Intent(getActivity(), WorkRecipeActivity.class));
                        }
                        break;
                    case 5:
                        startActivity(new Intent(getActivity(), WorkChangeActivity.class));
                        break;
                    case 6:
                        startActivity(new Intent(getActivity(), PrivateDoctorActivity.class));
                        break;
                    case 7:
//                        presenter.getTeamStatus();
                        if (!"1".equals(ToolSharePerference.getStringData(getActivity(), C.fileconfig,C.AUTH))){
                            showToast("请先进行实名认证");
                        }else {
                            startActivity(new Intent(getActivity(), WorkPatientActivity.class));
                        }
                        break;
                    case 8:
                        Intent intent = new Intent(getActivity(),WebActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("title","任务中心");
                        bundle.putString("url",Url_IP + "doctorProject/pages/task.html");
                        intent.putExtras(bundle);
                        startActivity(intent);
//                        startActivity(new Intent(getActivity(), WorkFreeActivity.class));
                        break;
                }
            }
        });
    }
    private void setListener2() {

        adapter.setOnItemClickListener(new OnItemClick() {
            @Override
            public void onItemClick(View view, int position) {
                switch (position){
                    case 0:
//                        presenter.getTeamStatus();WorkNurseActivity
                        startActivity(new Intent(getActivity(), WorkNurseActivity.class));
                        break;
                    case 1:
                        startActivity(new Intent(getActivity(), WorkPatientActivity.class));
                        break;
                }
            }
        });
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
                    .inflate(R.layout.grid_item_work,parent,false);
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
    public void onResume() {
        if(UserInfoBean.getInstance().getIdType().equals("1")){
            setListener2();
        }else {
            setListener();
        }
        lazyLoad();
        super.onResume();
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
    public void reloadGrid(SparseArray<SparseArray<String>> array) {
        this.array = array;
        adapter.setList(array);
    }

    @Override
    public void enterDetailTeam() {
       Intent teamIntent = new Intent();
       teamIntent.setClass(getActivity(), TeamDetailActivity.class);
       startActivity(teamIntent);
    }

    @Override
    public void enterCreateTeam() {
        Intent teamIntent = new Intent();
        teamIntent.setClass(getActivity(), CreateTeamActivity.class);
        startActivity(teamIntent);
    }
}
