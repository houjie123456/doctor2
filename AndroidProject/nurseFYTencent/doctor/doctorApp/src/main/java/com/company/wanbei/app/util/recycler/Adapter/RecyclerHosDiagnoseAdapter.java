package com.company.wanbei.app.util.recycler.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.company.wanbei.app.R;
import com.company.wanbei.app.bean.HosDiagnosisBean;
import com.company.wanbei.app.bean.ReportBean2;

import java.util.ArrayList;

public class RecyclerHosDiagnoseAdapter extends SecondaryHosDiagnoseListAdapter<RecyclerHosDiagnoseAdapter.GroupItemViewHolder, RecyclerHosDiagnoseAdapter.SubItemViewHolder> {


    private Context context;

    private ArrayList<HosDiagnosisBean> dts = new ArrayList<>();

    public RecyclerHosDiagnoseAdapter(Context context) {
        this.context = context;
    }

    public void setData(ArrayList<HosDiagnosisBean> datas,String type) {
        dts = datas;
        notifyNewData(dts,type);
    }

    @Override
    public RecyclerView.ViewHolder groupItemViewHolder(ViewGroup parent) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_hos_diagnose_parent, parent, false);

        return new GroupItemViewHolder(v);
    }

    @Override
    public RecyclerView.ViewHolder subItemViewHolder(ViewGroup parent) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_hos_diagnose_child, parent, false);

        return new SubItemViewHolder(v);
    }

    @Override
    public void onGroupItemBindViewHolder(RecyclerView.ViewHolder holder, int groupItemIndex) {
        ((GroupItemViewHolder) holder).tvGroup.setText(dts.get(groupItemIndex).getVisitdate());
        if(dts.get(groupItemIndex).isSelected()){
            ((GroupItemViewHolder) holder).constraintLayout.setBackgroundResource(R.color.background_gray1);
            ((GroupItemViewHolder) holder).imageView.setImageDrawable(context.getResources().getDrawable(R.drawable.icon_selected));
        }else {
            ((GroupItemViewHolder) holder).constraintLayout.setBackgroundResource(R.color.white);
            ((GroupItemViewHolder) holder).imageView.setImageDrawable(context.getResources().getDrawable(R.drawable.icon_default));
        }
    }

    @Override
    public void onSubItemBindViewHolder(RecyclerView.ViewHolder holder, int groupItemIndex, int subItemIndex) {

        ((SubItemViewHolder) holder).diagnoseNumTV.setText(dts.get(groupItemIndex).getAdmid());
        ((SubItemViewHolder) holder).diagnoseDeptTV.setText(dts.get(groupItemIndex).getDepartName());
        String diagnoseList = "";
        for (int i=0;i<dts.get(groupItemIndex).getDiseaseNameList().size();i++){
            diagnoseList += i+1+"、"+dts.get(groupItemIndex).getDiseaseNameList().get(i);
        }
        ((SubItemViewHolder) holder).diagnoseListTV.setText(diagnoseList);

    }

    @Override
    public void onGroupItemClick(Boolean isExpand, GroupItemViewHolder holder, int groupItemIndex) {

        for(int i=0;i<dts.size();i++){
            if(i == groupItemIndex){
                dts.get(i).setSelected(true);
                if(isExpand){
                    dts.get(i).setSelected(false);
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

            tvGroup = (TextView) itemView.findViewById(R.id.visit_time);
            imageView = itemView.findViewById(R.id.image);
            constraintLayout = itemView.findViewById(R.id.layout_head);

        }
    }

    public static class SubItemViewHolder extends RecyclerView.ViewHolder {

        TextView diagnoseNumTV,diagnoseDeptTV,diagnoseListTV;

        public SubItemViewHolder(View itemView) {
            super(itemView);

            diagnoseNumTV = (TextView) itemView.findViewById(R.id.diagnose_num);
            diagnoseDeptTV = (TextView) itemView.findViewById(R.id.diagnose_dept);
            diagnoseListTV = (TextView) itemView.findViewById(R.id.diagnose_list);
        }
    }


}
