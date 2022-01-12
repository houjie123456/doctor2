package com.company.wanbei.app.util.recycler.Adapter;

import android.content.Context;
import android.view.View;

import com.company.wanbei.app.bean.PatientGroupDiseaseBean;
import com.tencent.qcloud.tuikit.tuichat.fromApp.view.MyTextView;
import com.company.wanbei.app.R;

import java.util.List;

public class DiseaseGroupAdapter extends BaseRecyclerViewAdapter<PatientGroupDiseaseBean> {

    private OnDeleteClickLister mDeleteClickListener;
    private OnUpdateClickLister mUpdateClickListener;
    private OnAddClickLister mAddClickListener;
    private OnItemClick onItemClickListener;
    private Context mContext;
    private List<PatientGroupDiseaseBean> mData;
    private final int GROUP=1,CHILD=2;
    private static final int[] mLayoutIds={R.layout.list_item_patient_group,R.layout.list_item_patient_group_child,R.layout.list_item_patient_group_child_add};

    public DiseaseGroupAdapter(Context context, List<PatientGroupDiseaseBean> data) {

        super(context, data, mLayoutIds);
        this.mContext=context;
        this.mData=data;
    }

    @Override
    public void setList(List<PatientGroupDiseaseBean> list) {
        super.setList(list);
    }



    @Override
    protected void onBindData(RecycleViewHolder holder, PatientGroupDiseaseBean bean, int position) {
        View updateV = holder.getView(R.id.update);
        updateV.setTag(position);
        if (!updateV.hasOnClickListeners()) {
            updateV.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mUpdateClickListener != null) {
                        mUpdateClickListener.onUpdateClick(v, (Integer) v.getTag());
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
        ((MyTextView) holder.getView(R.id.disease_group)).setText(bean.getGroupname());

    }

    @Override
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        super.setOnItemClickListener(onItemClickListener);
    }

    @Override
    protected void onBindDataChild(RecycleViewHolder holder, PatientGroupDiseaseBean bean, int position) {
        View updateV = holder.getView(R.id.update);
        updateV.setTag(position);
        if (!updateV.hasOnClickListeners()) {
            updateV.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mUpdateClickListener != null) {
                        mUpdateClickListener.onUpdateClick(v, (Integer) v.getTag());
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
        ((MyTextView) holder.getView(R.id.disease_name)).setText(bean.getIcdname());
    }

    @Override
    protected void onBindDataChildAdd(RecycleViewHolder holder, PatientGroupDiseaseBean bean, int position) {

    }

    public void setOnDeleteClickListener(OnDeleteClickLister listener) {
        this.mDeleteClickListener = listener;
    }

    public interface OnDeleteClickLister {
        void onDeleteClick(View view, int position);
    }
    public void setOnUpdateClickListener(OnUpdateClickLister listener) {
        this.mUpdateClickListener = listener;
    }

    public interface OnUpdateClickLister {
        void onUpdateClick(View view, int position);
    }

    public void setOnAddClickListener(OnAddClickLister listener) {
        this.mAddClickListener = listener;
    }

    public interface OnAddClickLister {
        void onAddClick(View view, int position);
    }

    public interface OnItemClick{
        public void onItemClick(View view, int position);
    }

    private void setOnItemClickListener(OnItemClick listener){
        onItemClickListener = listener;
    }
}
