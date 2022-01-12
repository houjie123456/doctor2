package com.company.wanbei.app.moduleWork;


import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.company.wanbei.app.R;
import com.company.wanbei.app.base.BaseFragment;
import com.tencent.qcloud.tuikit.tuichat.fromApp.config.C;
import com.company.wanbei.app.moduleWeb.WebActivity;
import com.company.wanbei.app.moduleWork.ui.WorkNurseActivity;
import com.company.wanbei.app.moduleWork.ui.WorkNurseAskActivity;
import com.company.wanbei.app.moduleWork.ui.moduleNurseGuide.NurseUserGuideActivity;
import com.company.wanbei.app.tim.VisitInfomation;
import com.tencent.qcloud.tuikit.tuichat.fromApp.util.MyDialog;
import com.tencent.qcloud.tuikit.tuichat.fromApp.util.MyToast;
import com.tencent.qcloud.tuikit.tuichat.fromApp.util.ToolSharePerference;
import com.company.wanbei.app.util.view.NewRoundImageView;
import com.tencent.qcloud.tuikit.tuichat.fromApp.view.MyTextView;

//import com.company.wanbei.app.moduleWork.ui.WorkVideoActivity;
//import com.netease.nimlib.sdk.avchat.model.AVChatVideoCapturerFactory;

/**
 * Created by YC on 2018/6/7.
 */

public class WorkNurseFragment extends BaseFragment implements WorkInterface.ViewInterface{

    private Dialog myDialog;

    private RecyclerView recyclerView;
    private SwipeRefreshLayout refreshLayout;
    private SparseArray<SparseArray<String>> array;
    private MyListAdapter adapter;
    private TextView docNameTV,docTitleTV,deptHosTV,nurseNumTV,askNumTV;
    private NewRoundImageView docHeadIV;
    
    private WorkPresenter presenter;

    int sw;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_work_nurse, null);

        //fragment的布局文件
//        View view = inflater.inflate(R.layout.fragment_home_work_nurse, container, false);
//        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        view.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        initHead(view);
        initView(view);
        setListener2();
        return view;
    }

    @Override
    protected void lazyLoad() {
        if(getActivity() == null){
            return;
        }
        presenter.getGridData();
    }

    private void initHead(View view) {
        RelativeLayout head = (RelativeLayout)view.findViewById(R.id.layout_head);
        MyTextView title = (MyTextView)head.findViewById(R.id.head_top_title);
        title.setText("工作台");
        ImageView rightIV = (ImageView)head.findViewById(R.id.head_top_image);
        rightIV.setVisibility(View.GONE);
    }
    private void initView(View view){
        sw = getResources().getDisplayMetrics().widthPixels;
        presenter = new WorkPresenter(this);

        docNameTV = view.findViewById(R.id.doc_name);
        docTitleTV = view.findViewById(R.id.doc_title);
        deptHosTV = view.findViewById(R.id.dept_hos);
        nurseNumTV = view.findViewById(R.id.nurse_num);
        askNumTV = view.findViewById(R.id.ask_num);
        docHeadIV = view.findViewById(R.id.doc_head);

        int w2 = (90*sw)/720;
        Glide.with(getContext()).load(VisitInfomation.getInstance().getDocHead()).apply(new RequestOptions().override(w2,w2).error(getResources().getDrawable(R.drawable.img_me_photo)).centerCrop()).into(docHeadIV);
        docNameTV.setText(VisitInfomation.getInstance().getDocName());
        docTitleTV.setText(VisitInfomation.getInstance().getDocTitle());
        deptHosTV.setText(VisitInfomation.getInstance().getDocDept()+" "+VisitInfomation.getInstance().getDocHos());
        nurseNumTV.setText(VisitInfomation.getInstance().getServiceCount());
        askNumTV.setText(VisitInfomation.getInstance().getConsultCount());



        refreshLayout = (SwipeRefreshLayout)view.findViewById(R.id.work_refresh);
        refreshLayout.setEnabled(false);
        refreshLayout.setColorSchemeColors(ContextCompat.getColor(getActivity(),R.color.base_red_color));
        recyclerView = (RecyclerView)view.findViewById(R.id.work_recycler);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),3,RecyclerView.VERTICAL, false));
        array = new SparseArray<>();
        adapter = new MyListAdapter(getContext(),array);
        recyclerView.setAdapter(adapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    //护理
    private void setListener2() {
        adapter.setOnItemClickListener(new OnItemClick() {
            @Override
            public void onItemClick(View view, int position) {
                switch (position){
                    case 0:
                        startActivity(new Intent(getActivity(), NurseUserGuideActivity.class));
                        break;
                    case 1:
                        startActivity(new Intent(getActivity(), WorkNurseActivity.class));
                        break;
                    case 2:
                        startActivity(new Intent(getActivity(), WorkNurseAskActivity.class));
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
                    .inflate(R.layout.grid_item_work_nurse,parent,false);
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
        if(ToolSharePerference.getStringData(getContext(), C.fileconfig, C.IDTYPE).equals("1")){//护理
            setListener2();
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
    public void enterOnlineStudy(String sign) {
        Intent intent = new Intent(getActivity(), WebActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("title","在线教育");
        bundle.putString("url",sign);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    public void enterDetailTeam() {
//       Intent teamIntent = new Intent();
//       teamIntent.setClass(getActivity(), TeamDetailActivity.class);
//       startActivity(teamIntent);
    }

    @Override
    public void enterCreateTeam() {
    }
}
