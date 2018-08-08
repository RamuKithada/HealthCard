package com.prachatech.appointment.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.JsonObject;
import com.prachatech.appointment.R;
import com.prachatech.appointment.apis.ApiUrl;
import com.prachatech.appointment.apis.MyService;
import com.prachatech.appointment.model.RegResult;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgotPasswordActivity extends BaseActivity implements View.OnClickListener{


    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.submit)
    Button submit;
    @BindView(R.id.txt_sign_in)
    TextView signIn;

    @BindView(R.id.email)
    EditText edt_mail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        ButterKnife.bind(this);
        submit.setOnClickListener(this);
        signIn.setOnClickListener(this);
        back.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back:
                finish();
                break;
            case R.id.submit:
                submit();

                break;
            case R.id.txt_sign_in:
                Intent intent=new Intent(ForgotPasswordActivity.this, SignInActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(new Intent());
                break;



        }
    }

    private void submit() {
        String email=edt_mail.getText().toString();
        if(email==null||email.isEmpty()){
           edt_mail.setError("please provide email");

            return;
        }
        if(isConnected())
        {
            ApiCall(email);
        }
        else
       showToast("No Internet");
    }

    private void ApiCall(String email) {
        JsonObject object=new JsonObject();
        object.addProperty("email",email);
        MyService service=MyService.Factory.create(getApplicationContext());
        Call<RegResult> call=service.forgetPsw(object, ApiUrl.context_type);
        showDialog();
        call.enqueue(new Callback<RegResult>() {
            @Override
            public void onResponse(Call<RegResult> call, Response<RegResult> response) {
              hideDialog();
                RegResult result=response.body();
                showToast(result.getMessage());

            }

            @Override
            public void onFailure(Call<RegResult> call, Throwable t) {
            hideDialog();
        }});



    }
}
