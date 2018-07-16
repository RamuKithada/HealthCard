package com.prachatech.medspace;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.prachatech.medspace.acitivities.SelectLanguageActivity;
import de.hdodenhof.circleimageview.CircleImageView;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,View.OnClickListener {

   
  //  @BindView(R.id.hamberger)
    ImageView hamberger;

    //@BindView(R.id.drawer_layout)
    DrawerLayout drawer;

//    @BindView(R.id.notification_lay)
    RelativeLayout notifications;

//    @BindView(R.id.message_lay)
    RelativeLayout messages;

//    @BindView(R.id.profile_lay)
    RelativeLayout profile_settings_lay;

//    @BindView(R.id.profile_settings)
    ImageView profileSettings;

//    @BindView(R.id.camera_icon)
    ImageView camera_open;

//    @BindView(R.id.nav_view)
    NavigationView navigationView;

//    @BindView(R.id.profile_pic)
    CircleImageView profile_pic;

//    @BindView(R.id.user_name)
    TextView user_name;

//    @BindView(R.id.op_app_card)
    CardView open_op_appointment;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

         hamberger=findViewById(R.id.hamberger);
         drawer=findViewById(R.id.drawer_layout);
         notifications=findViewById(R.id.notification_lay);
         messages=findViewById(R.id.message_lay);
         profile_settings_lay=findViewById(R.id.profile_lay);
         camera_open=findViewById(R.id.camera_icon);;
         open_op_appointment=findViewById(R.id.op_app_card);


        navigationView=findViewById(R.id.nav_view);
        View header=navigationView.getHeaderView(0);
        profileSettings=header.findViewById(R.id.profile_settings);
        camera_open=header.findViewById(R.id.camera_icon);
        user_name=header.findViewById(R.id.user_name);
        profile_pic=header.findViewById(R.id.profile_pic);


        hamberger.setOnClickListener(this);
        notifications.setOnClickListener(this);
        messages.setOnClickListener(this);
        profile_settings_lay.setOnClickListener(this);
        profileSettings.setOnClickListener(this);
        camera_open.setOnClickListener(this);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            // Handle the camera action
        } else if (id == R.id.op_app) {
            startActivity(new Intent(HomeActivity.this, SelectLanguageActivity.class));

        } else if (id == R.id.lab_app) {

        } else if (id == R.id.pharmacy) {

        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.hamberger:
                hambergerAction();
                break;
            case R.id.op_app_card:
                startActivity(new Intent(HomeActivity.this, SelectLanguageActivity.class));
                break;
            case  R.id.notification_lay:

                break;
            case R.id.message_lay:

                break;
            case R.id.profile_lay:
            case R.id.profile_settings:
                openProfilesettingDialog();
                break;
            case R.id.camera_icon:
                getProfilePicture();
                break;

        }

    }

    private void getProfilePicture() {
    }

    private void openProfilesettingDialog() {
openPopupMenu();
    }
    public void openPopupMenu(){

        PopupMenu pm = new PopupMenu(HomeActivity.this, profile_settings_lay);

        pm.getMenuInflater().inflate(R.menu.popup_menu, pm.getMenu());
        pm.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.first:
                        Toast.makeText(HomeActivity.this, "Clicked First Menu Item", Toast.LENGTH_SHORT).show();
                        return true;

                    case R.id.second:
                        Toast.makeText(HomeActivity.this, "Clicked Second Menu Item", Toast.LENGTH_SHORT).show();
                        return true;
                }

                return true;
            }
        });
        pm.show();

    }


    private void hambergerAction() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }else {
            drawer.openDrawer(GravityCompat.START);
        }
    }
}
