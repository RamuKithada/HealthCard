package com.prachatech.appointment.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.prachatech.appointment.R;
import com.prachatech.appointment.apis.MyService;
import com.prachatech.appointment.utils.DialogsUtils;
import com.prachatech.appointment.utils.NetworkConnection;
import com.prachatech.appointment.utils.SessionManager;

public abstract class BaseActivity extends AppCompatActivity implements View.OnClickListener{
    private static final String TAG ="FireBase" ;
    SessionManager manager;
NetworkConnection connection;
MyService service;
ProgressDialog dialog;
    String token=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        manager=new SessionManager(this);
        connection=new NetworkConnection(this);
        service=MyService.Factory.create(getApplicationContext());
    }

    public void showDialog(){
        if(dialog==null){
            dialog=DialogsUtils.showProgressDialog(this,"Loading data..");
            return;
        }
        if(dialog.isShowing())
            return;
        dialog.show();

    }

    public void hideDialog(){
        if(dialog!=null&&dialog.isShowing())
        {
            dialog.dismiss();
            dialog=null;
        }
    }

public void back(View view){
        finish();
}

    public boolean isConnected(){
        return connection.isConnectingToInternet();
    }

    public SessionManager getManager(){
        return manager;
    }

    public void showToast(String message){
        Toast.makeText(this,message,Toast.LENGTH_LONG).show();
    }

    public String getToken() {
        // Get token
        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "getInstanceId failed", task.getException());
                            return;
                        }

                        // Get new Instance ID token
                         token = task.getResult().getToken();

                        // Log and toast
                        String msg = getString(R.string.msg_token_fmt, token);
                        Log.d(TAG, msg);
                        Toast.makeText(BaseActivity.this, msg, Toast.LENGTH_SHORT).show();
                    }
                });

        return token;
    }
}
