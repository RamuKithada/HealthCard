package com.prachatech.medspace.acitivities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.prachatech.medspace.HomeActivity;
import com.prachatech.medspace.R;
import com.prachatech.medspace.Utils.RecyclerTouchListener;
import com.prachatech.medspace.adapters.HeightListAdapter;
import com.prachatech.medspace.adapters.WeightListAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WeightActivity extends AppCompatActivity implements View.OnClickListener{

    @BindView(R.id.next)
    Button next;
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.skip)
    TextView skip;
    @BindView(R.id.weight_list)
    RecyclerView weightRecyclerView;
WeightListAdapter adapter;
ArrayList<Integer> integers=new ArrayList<Integer>();
int white,primary,black;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weight);
        ButterKnife.bind(this);

        next.setOnClickListener(this);
        skip.setOnClickListener(this);
        back.setOnClickListener(this);


        for(int i=40;i<200;i++){
            integers.add(new Integer(i));
        }
        adapter=new WeightListAdapter(this,integers);
        weightRecyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        weightRecyclerView.setAdapter(adapter);
        weightRecyclerView.addOnItemTouchListener(new RecyclerTouchListener(this, weightRecyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                adapter.setSelected(position);
            }

            @Override
            public void onLongClick(View view, int position) {
                adapter.setSelected(position);
            }
        }));

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.next:
                startActivity(new Intent(WeightActivity.this,HeightActivity.class));
                break;
            case R.id.skip:
                startActivity(new Intent(WeightActivity.this,HomeActivity.class));
                break;
            case R.id.back:
                finish();
                break;

        }
    }
}
