package com.prachatech.medspace.acitivities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HeightActivity extends AppCompatActivity implements View.OnClickListener{

    @BindView(R.id.done)
    Button done;
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.skip)
    TextView skip;
    @BindView(R.id.height_list)
RecyclerView heightRecyclerView;
HeightListAdapter adapter;
ArrayList<Integer> integers=new ArrayList<Integer>();
int white,primary,black;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_height);


        ButterKnife.bind(this);


        done.setOnClickListener(this);
        skip.setOnClickListener(this);
        back.setOnClickListener(this);


        for(int i=100;i<200;i++){
            integers.add(new Integer(i));
        }
        adapter=new HeightListAdapter(this,integers);
        heightRecyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        heightRecyclerView.setAdapter(adapter);
        heightRecyclerView.addOnItemTouchListener(new RecyclerTouchListener(this, heightRecyclerView, new RecyclerTouchListener.ClickListener() {
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
            case R.id.done:
                startActivity(new Intent(HeightActivity.this,HomeActivity.class));
                break;
            case R.id.skip:
                startActivity(new Intent(HeightActivity.this,HomeActivity.class));
                break;
            case R.id.back:
                finish();
                break;

        }
    }

}
