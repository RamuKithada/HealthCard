package com.prachatech.appointment.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.prachatech.appointment.R;
import com.prachatech.appointment.apis.ApiUrl;
import com.prachatech.appointment.apis.MyService;
import com.prachatech.appointment.model.PswChange;
import com.prachatech.appointment.model.RegResult;
import com.prachatech.appointment.utils.DialogsUtils;
import com.prachatech.appointment.utils.MessageToast;
import com.prachatech.appointment.utils.SessionManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangePasswordActivity extends BaseActivity {

    @BindView(R.id.old_psw)
    EditText edt_old_psw;

    @BindView(R.id.psw)
    EditText edt_psw;

    @BindView(R.id.conform_psw)
    EditText edt_conform_psw;

    @BindView(R.id.update_psw)
    Button btn_update;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        ButterKnife.bind(this);
        btn_update.setOnClickListener(this);
    }

    private void ApiCall(final PswChange pswChange) {
        MyService service=MyService.Factory.create(getApplicationContext());
        Call<RegResult> call=service.pswChange(pswChange, ApiUrl.context_type);

        final ProgressDialog progressDialog= DialogsUtils.showProgressDialog(ChangePasswordActivity.this,"Checking...");
        call.enqueue(new Callback<RegResult>() {
            @Override
            public void onResponse(Call<RegResult> call, Response<RegResult> response) {
                if (progressDialog.isShowing())
                    progressDialog.dismiss();
                RegResult result=response.body();
                MessageToast.showToastMethod(ChangePasswordActivity.this,result.getMessage());
                if(result.getStatus()==1)
                {
                    if (manager.isRemembered())
                        manager.setSingleField(SessionManager.KEY_PASSWORD,pswChange.getConfirmpassword());

                  finish();
                }


            }

            @Override
            public void onFailure(Call<RegResult> call, Throwable t) {
                if (progressDialog.isShowing())
                    progressDialog.dismiss();
            }
        });



    }

    @Override
    public void onClick(View v) {

        String oldpsw,psw,conformpsw;

        oldpsw=edt_old_psw.getText().toString();
        if(oldpsw==null&&oldpsw.isEmpty())
        {
            edt_old_psw.setError("fill this field");
            return;
        }
        psw=edt_psw.getText().toString();
        if(psw==null&&psw.isEmpty())
        {
            edt_psw.setError("fill this field");
            return;
        }
        conformpsw=edt_conform_psw.getText().toString();
        if(conformpsw==null&&conformpsw.isEmpty())
        {
            edt_conform_psw.setError("fill this field");
            return;
        }
        PswChange pswChange=new PswChange();
        pswChange.setAUId(manager.getSingleField(SessionManager.KEY_ID));
        pswChange.setOldpassword(edt_old_psw.getText().toString());
        pswChange.setPassword(edt_psw.getText().toString());
        pswChange.setConfirmpassword(edt_conform_psw.getText().toString());
        if(isConnected())
        {
            ApiCall(pswChange);
        }
        else
            MessageToast.showToastMethod(ChangePasswordActivity.this,"No Internet");

    }
}
