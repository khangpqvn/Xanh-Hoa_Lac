package com.example.deathdevilt_t.googlemaps_testv1.FragmentSetting;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.deathdevilt_t.googlemaps_testv1.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DeathDevil.T_T on 03-Aug-17.
 */

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.MyViewHolder> {

    private List<String> listData = new ArrayList<>();
    private List<String> listData2 = new ArrayList<>();
    private List<String> listData3 = new ArrayList<>();

    public HistoryAdapter(List<String> listData,List<String> listData2,List<String> listData3){
        this.listData = listData;
        this.listData2 = listData2;
        this.listData3 = listData3;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView txt_name;
        public TextView txt_action;
        public TextView txt_time;
        public MyViewHolder(View itemView) {
            super(itemView);
            txt_name = (TextView) itemView.findViewById(R.id.txt_name);
            txt_action = (TextView) itemView.findViewById(R.id.txt_action);
            txt_time = (TextView) itemView.findViewById(R.id.txt_time);
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemview = inflater.inflate(R.layout.history_recycle_view,parent,false);
        return new MyViewHolder(itemview);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.txt_name.setText(listData.get(position));
        holder.txt_action.setText(listData2.get(position));
        holder.txt_time.setText(listData3.get(position));
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

}
