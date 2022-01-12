package com.company.wanbei.app.util.recycler.Adapter;

import android.content.Context;
import android.view.View;

import com.company.wanbei.app.R;
import com.tencent.qcloud.tuikit.tuichat.fromApp.bean.DiseaseBean;
import com.tencent.qcloud.tuikit.tuichat.fromApp.view.MyTextView;

import java.util.List;

public class DiseaseListAdapter extends DiseaseRecyclerViewAdapter<DiseaseBean> {

    private OnDeleteClickLister mDeleteClickListener;
    private OnDiseaseClickLister mDiseaseClickListener;
    private OnGroupClickLister mGroupClickListener;
    private OnItemClick onItemClickListener;
    private Context mContext;
    private List<DiseaseBean> mData;
    private final int GROUP=1,CHILD=2;
    private static final int mLayoutIds=R.layout.list_item_disease;

    public DiseaseListAdapter(Context context, List<DiseaseBean> data) {

        super(context, data, mLayoutIds);
        this.mContext=context;
        this.mData=data;
    }

    @Override
    public void setList(List<DiseaseBean> list) {
        super.setList(list);
    }



    @Override
    protected void onBindData(RecycleViewHolder holder, DiseaseBean bean, int position) {
        View diseaseV = holder.getView(R.id.select_disease);
        diseaseV.setTag(position);
        if (!diseaseV.hasOnClickListeners()) {
            diseaseV.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mDiseaseClickListener != null) {
                        mDiseaseClickListener.onDiseaseClick(v, (Integer) v.getTag());
                    }
                }
            });
        }
        View groupV = holder.getView(R.id.select_disease_group);
        groupV.setTag(position);
        if (!groupV.hasOnClickListeners()) {
            groupV.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mGroupClickListener != null) {
                        mGroupClickListener.onGroupClick(v, (Integer) v.getTag());
                    }
                }
            });
        }
        View deleteV = holder.getView(R.id.delete);
        deleteV.setTag(position);
        if (!deleteV.hasOnClickListeners()) {
            deleteV.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mDeleteClickListener != null) {
                        mDeleteClickListener.onDeleteClick(v, (Integer) v.getTag());
                    }
                }
            });
        }
        ((MyTextView) holder.getView(R.id.select_disease_group)).setText(bean.getGroupname().equals("")?"请选择":bean.getGroupname());
        ((MyTextView) holder.getView(R.id.select_disease)).setText(bean.getIcdname().equals("")?"请选择":bean.getIcdname());

    }

    @Override
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        super.setOnItemClickListener(onItemClickListener);
    }
    public void setOnDiseaseClickListener(OnDiseaseClickLister listener) {
        this.mDiseaseClickListener = listener;
    }

    public interface OnDiseaseClickLister {
        void onDiseaseClick(View view, int position);
    }
    public void setOnGroupClickListener(OnGroupClickLister listener) {
        this.mGroupClickListener = listener;
    }

    public interface OnGroupClickLister {
        void onGroupClick(View view, int position);
    }




    public void setOnDeleteClickListener(OnDeleteClickLister listener) {
        this.mDeleteClickListener = listener;
    }

    public interface OnDeleteClickLister {
        void onDeleteClick(View view, int position);
    }


    public interface OnItemClick{
        public void onItemClick(View view, int position);
    }

    private void setOnItemClickListener(OnItemClick listener){
        onItemClickListener = listener;
    }
}
