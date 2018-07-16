package com.prachatech.medspace.adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.prachatech.medspace.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WeightListAdapter extends RecyclerView.Adapter<WeightListAdapter.ViewHolder>{
    Context context;
ArrayList<Integer> integers;
  int selected=-1;

    public WeightListAdapter(Context context, ArrayList<Integer> integers) {
        this.context = context;
        this.integers = integers;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.weight_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.size.setText(""+integers.get(position));
        if(selected==position) {
            holder.itemView.setBackgroundColor(ContextCompat.getColor(context,R.color.colorPrimary));
            holder.size.setTextColor(Color.WHITE);
            holder.measurement.setVisibility(View.VISIBLE);
        }else {
            holder.itemView.setBackgroundColor(Color.WHITE);
            holder.size.setTextColor(Color.BLACK);
            holder.measurement.setVisibility(View.INVISIBLE);
        }

    }



    @Override
    public int getItemCount() {
        return integers.size();
    }
    public void setIntegers(ArrayList<Integer> integers) {
        this.integers = integers;
    }
    public void setSelected(int selected){
        this.selected=selected;
        notifyDataSetChanged();

    }

    class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.size)
        TextView size;
        @BindView(R.id.measurement)
      TextView measurement;
      View itemView;
        public ViewHolder(View itemView) {
            super(itemView);
            this.itemView=itemView;
            ButterKnife.bind(this,itemView);

        }
    }
}
