package com.company.wanbei.app.util.recycler.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public abstract class DiseaseRecyclerViewAdapter<T> extends RecyclerView.Adapter<RecycleViewHolder> implements View.OnClickListener {
    private Context mContext;
    private List<T> mData;
    private int mLayoutIds;

    private OnItemClickListener mListener;

    DiseaseRecyclerViewAdapter(Context context, List<T> data, int layoutIds) {
        this.mContext = context;
        this.mData = data;
        this.mLayoutIds = layoutIds;
    }
    public void setList(List<T> list){
        this.mData = list;
        notifyDataSetChanged();
    }
    @Override
    public RecycleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view ;
        view = LayoutInflater.from(mContext).inflate( mLayoutIds, parent, false );
        view.setOnClickListener(this);
        return new RecycleViewHolder(view);
    }
//    @Override
//    public int getItemViewType ( int position ) {
//        DiseaseBean common = (DiseaseBean) mData.get( position );
//        //通过字段status值，来使用不同的布局
//        if ( common.getType().equals("1")  ) {
//            return mLayoutIds[0];
//        }
//        return super.getItemViewType( position );
//    }


    @Override
    public void onBindViewHolder(RecycleViewHolder holder, int position) {
        holder.itemView.setTag(position);
        T bean = mData.get(position);
            onBindData(holder, bean, position);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    @Override
    public void onClick(View v) {
        if (mListener != null) {
            mListener.onItemClick(this, v, (Integer) v.getTag());
        }
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mListener = onItemClickListener;
    }

    /**
     * 数据绑定，由实现类实现
     *
     * @param holder   The reference of the all view within the item.
     * @param bean     The data bean related to the position.
     * @param position The position to bind data.
     */
    protected abstract void onBindData(RecycleViewHolder holder, T bean, int position);


    /**
     * item点击监听器
     */
    public interface OnItemClickListener {
        /**
         * item点击回调
         *
         * @param adapter  The Adapter where the click happened.
         * @param v        The view that was clicked.
         * @param position The position of the view in the adapter.
         */
        void onItemClick(RecyclerView.Adapter adapter, View v, int position);
    }
}