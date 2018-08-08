package com.prachatech.appointment.activity;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.prachatech.appointment.R;
import com.prachatech.appointment.apis.ApiUrl;
import com.prachatech.appointment.apis.MyService;
import com.prachatech.appointment.model.Details;
import com.prachatech.appointment.model.Login;
import com.prachatech.appointment.model.LoginResult;
import com.prachatech.appointment.utils.MessageToast;
import com.prachatech.appointment.utils.SessionManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignInActivity extends BaseActivity implements View.OnClickListener{

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
        if(manager.isRemembered())
        {
            edt_email.setText(manager.getSingleField(SessionManager.KEY_EMAIL));
            edt_password.setText(manager.getSingleField(SessionManager.KEY_PASSWORD));
            check_rememberme.setChecked(true);

        }



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
        {
            Login login=new Login();
            login.setEmail(userName);
            login.setPassword(passworg);
            if(isConnected())
            {
                  ApiCall(login);
            }
            else
               showToast("No Internet");

        }


    }

    private boolean validate(String userName, String passworg) {
        if(!android.util.Patterns.EMAIL_ADDRESS.matcher(userName).matches()){
            edt_email.setError("enter a valid email address");
            return false;
        }

        if(passworg.length()<8)
       {
           edt_password.setError("please check password");
           return false;
       }



      return true;
    }

    private void ApiCall(final Login login) {

        String token=getToken();
        if(token==null)
            token=manager.getSingleField(SessionManager.Token);
        if(token==null)
            token="null";
        login.setToken(token);
        MyService service=MyService.Factory.create(getApplicationContext());
        Call<LoginResult> call=service.login(login, ApiUrl.context_type);
         showDialog();
        call.enqueue(new Callback<LoginResult>() {
            @Override
            public void onResponse(Call<LoginResult> call, Response<LoginResult> response) {
           hideDialog();
                LoginResult result=response.body();
                MessageToast.showToastMethod(SignInActivity.this,result.getMessage());
                if(result.getStatus()==1)
                {
                    Details details=result.getDetails();

                    manager.createSigninSession(login,result,check_rememberme.isChecked());
                    startActivity(new Intent(SignInActivity.this, HomeActivity.class));
                    finish();
                }


            }

            @Override
            public void onFailure(Call<LoginResult> call, Throwable t) {
            hideDialog();
            }
        });



    }
}

