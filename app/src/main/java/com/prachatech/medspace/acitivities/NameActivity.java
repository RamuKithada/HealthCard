package com.prachatech.medspace.acitivities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.prachatech.medspace.HomeActivity;
import com.prachatech.medspace.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NameActivity extends Activity implements View.OnClickListener{
    @BindView(R.id.next)
    Button next;
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.skip)
    TextView skip;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_name);
        ButterKnife.bind(this);
        next.setOnClickListener(this);
        skip.setOnClickListener(this);
        back.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.next:
                startActivity(new Intent(NameActivity.this,GenderActivity.class));
                break;
            case R.id.skip:
                startActivity(new Intent(NameActivity.this,HomeActivity.class));
                break;
            case R.id.back:
                finish();
                break;

        }
    }
}
