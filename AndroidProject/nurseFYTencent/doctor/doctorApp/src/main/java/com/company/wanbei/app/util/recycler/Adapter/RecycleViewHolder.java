package com.company.wanbei.app.util.recycler.Adapter;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.tencent.qcloud.tuikit.tuichat.fromApp.bean.DiseaseBean;
import com.tencent.qcloud.tuikit.tuichat.fromApp.view.MyTextView;
import com.company.wanbei.app.R;

import java.util.ArrayList;

class RecycleViewHolder extends RecyclerView.ViewHolder {
    public RecyclerView titleRecycle;
    public RecordListAdapter personListAdapter;

    public ArrayList<DiseaseBean> arrayPerson;
    private SparseArray<View> mViews;

    RecycleViewHolder(View itemView) {
        super(itemView);
        this.mViews = new SparseArray<>();
    }

    /**
     * 获取需要的View，如果已经存在引用则直接获取，如果不存在则重新加载并保存
     *
     * @param viewId id
     * @return 需要的View
     */
    View getView(int viewId) {
        View view = mViews.get(viewId);
        if (view == null) {
            view = itemView.findViewById(viewId);
            mViews.put(viewId, view);
        }
        return view;
    }


    /**
     * recycler 适配器
     */
    public class RecordListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

        private Context mContext;
        private ArrayList<DiseaseBean> mList;
        public OnItemClick onItemClickListener;


        public RecordListAdapter(Context context,ArrayList<DiseaseBean> list ){
            this.mContext = context;
            this.mList = list;
        }


        public void setList(ArrayList<DiseaseBean> list){
            this.mList = list;
            notifyDataSetChanged();
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(mContext)
                    .inflate(R.layout.list_item_patient_group_child,parent,false);
            return new RecordViewHolder(view,onItemClickListener);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            if(holder instanceof RecordViewHolder){
                initView((RecordViewHolder) holder, mList.get(position));
            }
        }

        @Override
        public int getItemCount() {
            return mList.size();
        }

        public void setOnItemClickListener(OnItemClick listener){
            onItemClickListener = listener;
        }

        private void initView(RecordViewHolder handler, DiseaseBean bean){
            if (bean == null) return;


            handler.diseaseTV.setText(bean.getIcdname());
        }
    }

    /**
     * 静态类
     */
    private static class RecordViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public MyTextView diseaseTV;
        public OnItemClick onItemClick;


        public RecordViewHolder(View view, OnItemClick onItemClick){
            super(view);
            this.onItemClick = onItemClick;
            view.setOnClickListener(this);
            diseaseTV = (MyTextView) view.findViewById(R.id.disease_name);
        }

        @Override
        public void onClick(View view) {
            if(onItemClick != null){
                onItemClick.onItemClick(view,getLayoutPosition(),3);
            }
        }
    }

    public interface OnItemClick{
        public void onItemClick(View view, int position, int index);
    }
}