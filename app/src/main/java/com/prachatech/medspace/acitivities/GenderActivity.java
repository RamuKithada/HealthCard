package com.prachatech.medspace.acitivities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.prachatech.medspace.HomeActivity;
import com.prachatech.medspace.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GenderActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener,View.OnClickListener{

    @BindView(R.id.next)
    Button next;
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.skip)
    TextView skip;
    @BindView(R.id.gender_radio)
    RadioGroup radioGroup;
    @BindView(R.id.male_radio)
    RadioButton male;
    @BindView(R.id.female_radio)
    RadioButton female;
    @BindView(R.id.others_radio)
    RadioButton otheres;
    int white,primary,black;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gender);
        ButterKnife.bind(this);
        white= ContextCompat.getColor(this, R.color.colorWhite);
        primary=ContextCompat.getColor(this,R.color.colorPrimary);
        black=ContextCompat.getColor(this,R.color.colorBlack);
        radioGroup.setOnCheckedChangeListener(this);
        next.setOnClickListener(this);
        skip.setOnClickListener(this);
        back.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.next:
                startActivity(new Intent(GenderActivity.this,WeightActivity.class));
                break;
            case R.id.skip:
                startActivity(new Intent(GenderActivity.this,HomeActivity.class));
                break;
            case R.id.back:
                finish();
                break;

        }
    }

    private void setDefaultColors(RadioButton radioButton) {
        radioButton.setTextColor(black);
        radioButton.setBackgroundColor(white);
    }
    private void setCheckedColors(RadioButton radioButton) {
        radioButton.setTextColor(white);
        radioButton.setBackgroundColor(primary);
    }

        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            setDefaultColors(male);
            setDefaultColors(female);
            setDefaultColors(otheres);
            switch (checkedId){
                case R.id.male_radio:
                    setCheckedColors(male);
                    break;
                case R.id.female_radio:
                    setCheckedColors(female);
                    break;
                case R.id.others_radio:
                    setCheckedColors(otheres);
                    break;
                default:
                    setCheckedColors(male);
                    break;
            }
    }
}
