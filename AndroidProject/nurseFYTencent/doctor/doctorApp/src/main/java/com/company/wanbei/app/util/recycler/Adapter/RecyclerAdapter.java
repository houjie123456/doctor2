package com.company.wanbei.app.util.recycler.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.company.wanbei.app.R;
import com.company.wanbei.app.bean.ReportBean2;

import java.util.ArrayList;

public class RecyclerAdapter extends SecondaryListAdapter<RecyclerAdapter.GroupItemViewHolder, RecyclerAdapter.SubItemViewHolder> {


    private Context context;

    private ArrayList<ReportBean2> dts = new ArrayList<>();

    public RecyclerAdapter(Context context) {
        this.context = context;
    }

    public void setData(ArrayList<ReportBean2> datas,String type) {
        dts = datas;
        notifyNewData(dts,type);
    }

    @Override
    public RecyclerView.ViewHolder groupItemViewHolder(ViewGroup parent) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_report2_parent, parent, false);

        return new GroupItemViewHolder(v);
    }

    @Override
    public RecyclerView.ViewHolder subItemViewHolder(ViewGroup parent) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_report2_child, parent, false);

        return new SubItemViewHolder(v);
    }

    @Override
    public void onGroupItemBindViewHolder(RecyclerView.ViewHolder holder, int groupItemIndex) {
        ((GroupItemViewHolder) holder).tvGroup.setText(dts.get(groupItemIndex).getTestName());
        if(dts.get(groupItemIndex).isSelect()){
            ((GroupItemViewHolder) holder).constraintLayout.setBackgroundResource(R.color.background_gray1);
            ((GroupItemViewHolder) holder).imageView.setImageDrawable(context.getResources().getDrawable(R.drawable.icon_selected));
        }else {
            ((GroupItemViewHolder) holder).constraintLayout.setBackgroundResource(R.color.white);
            ((GroupItemViewHolder) holder).imageView.setImageDrawable(context.getResources().getDrawable(R.drawable.icon_default));
        }
    }

    @Override
    public void onSubItemBindViewHolder(RecyclerView.ViewHolder holder, int groupItemIndex, int subItemIndex) {

        ((SubItemViewHolder) holder).resultTV.setText(dts.get(groupItemIndex).getResult());
        ((SubItemViewHolder) holder).unitTV.setText(dts.get(groupItemIndex).getUnit());
        ((SubItemViewHolder) holder).heightTV.setText(dts.get(groupItemIndex).getResFlagStr());
        ((SubItemViewHolder) holder).rangeTV.setText(dts.get(groupItemIndex).getRanges());

    }

    @Override
    public void onGroupItemClick(Boolean isExpand, GroupItemViewHolder holder, int groupItemIndex) {

        for(int i=0;i<dts.size();i++){
            if(i == groupItemIndex){
                dts.get(i).setSelect(true);
                if(isExpand){
                    dts.get(i).setSelect(false);
                }
            }
        }
//        for (ReportBean2 bean: dts) {
//            bean.setSelect(false);
//        }
//        dts.get(groupItemIndex).setSelect(true);

        setData(dts,"2");
//        Toast.makeText(context, "group item " + String.valueOf(groupItemIndex) + " is expand " +
//                String.valueOf(isExpand), Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onSubItemClick(SubItemViewHolder holder, int groupItemIndex, int subItemIndex) {

//        Toast.makeText(context, "sub item " + String.valueOf(subItemIndex) + " in group item " +
//                String.valueOf(groupItemIndex), Toast.LENGTH_SHORT).show();

    }

    public static class GroupItemViewHolder extends RecyclerView.ViewHolder {

        TextView tvGroup;
        ImageView imageView;
        ConstraintLayout constraintLayout;

        public GroupItemViewHolder(View itemView) {
            super(itemView);

            tvGroup = (TextView) itemView.findViewById(R.id.report_name);
            imageView = itemView.findViewById(R.id.image);
            constraintLayout = itemView.findViewById(R.id.layout_head);

        }
    }

    public static class SubItemViewHolder extends RecyclerView.ViewHolder {

        TextView resultTV,unitTV,heightTV,rangeTV;

        public SubItemViewHolder(View itemView) {
            super(itemView);

            resultTV = (TextView) itemView.findViewById(R.id.report_result);
            unitTV = (TextView) itemView.findViewById(R.id.report_result_unit);
            heightTV = (TextView) itemView.findViewById(R.id.report_height);
            rangeTV = (TextView) itemView.findViewById(R.id.reference_range);
        }
    }


}
