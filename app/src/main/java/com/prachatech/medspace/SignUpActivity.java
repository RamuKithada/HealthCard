package com.prachatech.medspace;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.prachatech.medspace.acitivities.NameActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SignUpActivity extends Activity implements View.OnClickListener{



    @BindView(R.id.user_name)
    EditText edt_user_name;

    @BindView(R.id.molibe_nmbr)
    EditText edt_mobile_num;

    @BindView(R.id.email)
    EditText edt_email;

    @BindView(R.id.password)
    EditText password;

    @BindView(R.id.repassword)
    EditText conform_password;

    @BindView(R.id.signup)
    Button signup;
    @BindView(R.id.txt_sign_in)
    TextView sign_in;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        ButterKnife.bind(this);
        signup.setOnClickListener(this);
        sign_in.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.signup:
                startActivity(new Intent(SignUpActivity.this, NameActivity.class));
                break;
            case R.id.txt_sign_in:
                startActivity(new Intent(SignUpActivity.this, SignInActivity.class));
                break;
        }

    }
}
