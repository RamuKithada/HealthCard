package com.prachatech.medspace;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.prachatech.medspace.Utils.MessageToast;
import com.prachatech.medspace.acitivities.ForgotPasswordActivity;
import com.prachatech.medspace.acitivities.NameActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SignInActivity extends AppCompatActivity implements View.OnClickListener{

    @BindView(R.id.edt_user_name)
    EditText edt_email;
    @BindView(R.id.edt_password)
    EditText edt_password;
    @BindView(R.id.check_remember_me)
    CheckBox check_rememberme;
    @BindView(R.id.img_forgot_password)
    ImageView forgot_password;
    @BindView(R.id.btn_sign_in)
    Button sign_in;
    @BindView(R.id.txt_sign_up)
    TextView sign_up;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        ButterKnife.bind(this);
        sign_in.setOnClickListener(this);
        sign_up.setOnClickListener(this);
        forgot_password.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_sign_in:
                SignIn();
                break;
            case R.id.txt_sign_up:
                startActivity(new Intent(SignInActivity.this,SignUpActivity.class));
                finish();
                break;
            case R.id.img_forgot_password:
                startActivity(new Intent(SignInActivity.this, ForgotPasswordActivity.class));
                break;



        }

    }

    private void SignIn() {
        String userName=edt_email.getText().toString();
        String passworg=edt_password.getText().toString();
        if(validate(userName,passworg))
        startActivity(new Intent(SignInActivity.this, NameActivity.class));
          else {
            MessageToast.showToastMethod(this,"Invalid Credentials");
        }

    }

    private boolean validate(String userName, String passworg) {
      return true;
    }
}

