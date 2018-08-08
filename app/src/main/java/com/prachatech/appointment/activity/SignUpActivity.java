package com.prachatech.appointment.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.prachatech.appointment.R;
import com.prachatech.appointment.apis.ApiUrl;
import com.prachatech.appointment.model.RegResult;
import com.prachatech.appointment.model.Registration;
import com.prachatech.appointment.utils.SessionManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpActivity extends BaseActivity implements View.OnClickListener{
    @BindView(R.id.user_name)
    EditText edt_user_name;

    @BindView(R.id.molibe_nmbr)
    EditText edt_mobile_num;

    @BindView(R.id.email)
    EditText edt_email;

    @BindView(R.id.password)
    EditText edt_password;

    @BindView(R.id.repassword)
    EditText edt_conform_password;

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
                signUpUser();
                break;
            case R.id.txt_sign_in:
                startActivity(new Intent(SignUpActivity.this, SignInActivity.class));
                break;
        }

    }

    private void signUpUser() {
        String user_name=edt_user_name.getText().toString();
        if(user_name==null||user_name.isEmpty()){
            edt_user_name.setError("Please Enter username");
            return;
        }
        String mobile_number=edt_mobile_num.getText().toString();
        if(mobile_number==null||mobile_number.isEmpty()){
            edt_mobile_num.setError("Please enter mobile number");
            return;
        }
        if(mobile_number.length()<10){
            edt_mobile_num.setError("number have 10 numbers");
            return;
        }

        String email=edt_email.getText().toString();
        if(email==null||email.isEmpty()){
            edt_email.setError("Plase enter email");
            return;
        }
        if(!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            edt_email.setError("enter a valid email address");
            return;
        }
        String password=edt_password.getText().toString();
        String conform_password=edt_conform_password.getText().toString();
        if(password==null||password.isEmpty()){
            edt_password.setError("enter password");
            return;
        }
        if(conform_password==null||conform_password.isEmpty()){
            edt_conform_password.setError("re-enter password");
            return;
        }
        if(password.equals(conform_password)){
        if(password.length()<8){
            edt_password.setError("password has mininum 8 charecters");
           return;
        }

        }else {
           showToast("password and  conform password are not same ");
            return;
        }


        final Registration registration=new Registration();
        registration.setName(user_name);
        registration.setMobile(mobile_number);
        registration.setEmail(email);
        registration.setPassword(password);
        registration.setConfirmpassword(conform_password);
        String token=getToken();
        if(token==null)
            token=manager.getSingleField(SessionManager.Token);
        if(token==null)
            token="null";
        registration.setToken(token);
//        MyService service=MyService.Factory.create(getApplication());
        if(isConnected()) {
            Call<RegResult> call=service.registration(registration, ApiUrl.context_type);
            showDialog();
            call.enqueue(new Callback<RegResult>() {
                @Override
                public void onResponse(Call<RegResult> call, Response<RegResult> response) {
                 hideDialog();
                    RegResult body = response.body();
                      if( body.getStatus()==1)
                        {
                            manager.createSignUpSession(registration,body);
                            startActivity(new Intent(SignUpActivity.this, HomeActivity.class));
                        }
                }

                @Override
                public void onFailure(Call<RegResult> call, Throwable t) {
                 hideDialog();
                }
            });
        }
        else
        {
          showToast("No Internet");
        }


    }
}
