package com.prachatech.appointment.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.prachatech.appointment.R;
import com.prachatech.appointment.model.RegResult;
import com.prachatech.appointment.utils.MessageToast;
import com.prachatech.appointment.utils.NetworkConnection;
import com.prachatech.appointment.utils.SessionManager;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener{
    public static final int MULTIPLE_PERMISSIONS = 10;
    static final Integer GPS_SETTINGS = 0x7;
    static int SELECT_FILE = 1;
    static int REQUEST_CAMERA = 2;
      @BindView(R.id.maps)
    ImageView maps;
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
    @BindView(R.id.op_app_card)
    CardView open_op_appointment;
    @BindView(R.id.find_doc_card)
    CardView find_doc;
    @BindView(R.id.pharamcy_card)
    CardView pharmacy;
    @BindView(R.id.lab_app_card)
    CardView lab_card;
    @BindView(R.id.title_home)
            TextView title_home;
    SessionManager manager;
    NetworkConnection connection;
    String[] permissions = new String[]{
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA};//,
//            Manifest.permission.ACCESS_COARSE_LOCATION,
//            Manifest.permission.ACCESS_FINE_LOCATION
//            };
    GoogleApiClient client;
    LocationRequest mLocationRequest;
    PendingResult<LocationSettingsResult> result;

    @SuppressLint("NewApi")
    public static String getFilePath(Context context, Uri uri) throws URISyntaxException {
        String selection = null;
        String[] selectionArgs = null;
        // Uri is different in versions after KITKAT (Android 4.4), we need to
        if (Build.VERSION.SDK_INT >= 19 && DocumentsContract.isDocumentUri(context.getApplicationContext(), uri)) {
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                return Environment.getExternalStorageDirectory() + "/" + split[1];
            } else if (isDownloadsDocument(uri)) {
                final String id = DocumentsContract.getDocumentId(uri);
                uri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));
            } else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];
                if ("image".equals(type)) {
                    uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }
                selection = "_id=?";
                selectionArgs = new String[]{
                        split[1]
                };
            }
        }
        if ("content".equalsIgnoreCase(uri.getScheme())) {
            String[] projection = {
                    MediaStore.Images.Media.DATA
            };
            Cursor cursor = null;
            try {
                cursor = context.getContentResolver()
                        .query(uri, projection, selection, selectionArgs, null);
                int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                if (cursor.moveToFirst()) {
                    return cursor.getString(column_index);
                }
            } catch (Exception e) {
            }
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }
        return null;
    }

    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
            ButterKnife.bind(this);

        client = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .build();

        checkPermissions();
           manager=new SessionManager(this);
           connection=new NetworkConnection(this);


         hamberger=findViewById(R.id.hamberger);
         drawer=findViewById(R.id.drawer_layout);
         notifications=findViewById(R.id.notification_lay);
         messages=findViewById(R.id.message_lay);
         profile_settings_lay=findViewById(R.id.profile_lay);
         camera_open=findViewById(R.id.camera_icon);;


        navigationView=findViewById(R.id.nav_view);
        View header=navigationView.getHeaderView(0);
        profileSettings=header.findViewById(R.id.profile_settings);
        camera_open=header.findViewById(R.id.camera_icon);
        user_name=header.findViewById(R.id.user_name);
        profile_pic=header.findViewById(R.id.profile_pic);
        ///setting profile details
             updateProfile();
        hamberger.setOnClickListener(this);
        notifications.setOnClickListener(this);
        messages.setOnClickListener(this);
        profile_settings_lay.setOnClickListener(this);
        profileSettings.setOnClickListener(this);
        open_op_appointment.setOnClickListener(this);
        lab_card.setOnClickListener(this);
        find_doc.setOnClickListener(this);
        pharmacy.setOnClickListener(this);
        camera_open.setOnClickListener(this);
        profile_pic.setOnClickListener(this);
        maps.setOnClickListener(this);
        navigationView.setNavigationItemSelectedListener(this);

       String profile=manager.getSingleField(SessionManager.PROFILE_IMG_URL)+manager.getSingleField(SessionManager.PROFILE_IMG_PATH);
        Glide.with(this)
                .load(profile)
                .error(R.drawable.dummy_user)
                .into(profile_pic);

    }

    @Override
    public void onStart() {
        super.onStart();
        client.connect();
    }

    @Override
    public void onStop() {
        super.onStop();
        client.disconnect();
    }

    private boolean checkPermissions() {
        int result;
        List<String> listPermissionsNeeded = new ArrayList<>();
        for (String p : permissions) {
            result = ContextCompat.checkSelfPermission(HomeActivity.this, p);
            if (result != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(p);
            }
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), MULTIPLE_PERMISSIONS);
            return false;
        }
        return true;
    }

    private void askForGPS() {
        mLocationRequest = LocationRequest.create();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(30 * 1000);
        mLocationRequest.setFastestInterval(5 * 1000);
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder().addLocationRequest(mLocationRequest);
        builder.setAlwaysShow(true);
        result = LocationServices.SettingsApi.checkLocationSettings(client, builder.build());
        result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
            @Override
            public void onResult(LocationSettingsResult result) {
                final Status status = result.getStatus();
                switch (status.getStatusCode()) {
                    case LocationSettingsStatusCodes.SUCCESS:
                        Log.e("onSuccess","on success");
                        break;
                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                        try {
                            status.startResolutionForResult(HomeActivity.this, GPS_SETTINGS);
                        } catch (IntentSender.SendIntentException e) {

                        }
                        break;
                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                        break;
                }
            }
        });
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
            startActivity(new Intent(HomeActivity.this, OpRegistrationActivity.class));

        } else if (id == R.id.lab_app) {
            startActivity(new Intent(HomeActivity.this, ComingSoon.class));
        } else if (id == R.id.pharmacy) {
            startActivity(new Intent(HomeActivity.this, ComingSoon.class));
        }
        else if (id == R.id.status) {
            Intent intent=new Intent(HomeActivity.this,StatusActivity.class);
            startActivity(intent);
        }
        else if (id == R.id.history) {
            Intent intent=new Intent(HomeActivity.this,StatusActivity.class);
            intent.putExtra("title","History");
           startActivity(intent);
        }
         else if (id == R.id.signout) {
            manager.logoutUser();
            Intent intent=new Intent(HomeActivity.this,SignInActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
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
                startActivity(new Intent(HomeActivity.this, OpRegistrationActivity.class));
                break;
            case R.id.find_doc_card:
            case R.id.pharamcy_card:
            case R.id.lab_app_card:
            case  R.id.maps:
                startActivity(new Intent(HomeActivity.this, ComingSoon.class));
                break;
            case  R.id.notification_lay:
                startActivity(new Intent(HomeActivity.this, StatusActivity.class));
                break;
            case R.id.message_lay:
                Intent intent=new Intent(HomeActivity.this,StatusActivity.class);
                intent.putExtra("title","History");
                startActivity(intent);
                break;
            case R.id.profile_lay:
                startActivity(new Intent(HomeActivity.this, ProfileActivity.class));
                break;
            case R.id.profile_settings:
                openProfilesettingDialog(v);
                break;
            case R.id.profile_pic:
             selectImage();
                break;

        }

    }

    public void openProfilesettingDialog(View view){

        PopupMenu pm = new PopupMenu(HomeActivity.this, view);

        pm.getMenuInflater().inflate(R.menu.popup_menu, pm.getMenu());
        pm.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.edit_profile:
                      startActivityForResult(new Intent(HomeActivity.this, ProfileActivity.class),100);
                        return true;

                    case R.id.change_password:
                        startActivity(new Intent(HomeActivity.this, ChangePasswordActivity.class));
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

    private void updateProfile() {
        String username=manager.getSingleField(SessionManager.KEY_NAME);
        if(username!=null&&!username.isEmpty()){
            user_name.setText(username);
            title_home.setText("Hello "+username);

        }
    }

    private void selectImage() {
        final CharSequence[] items = {"take Photos", "Gallery", "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this);
        builder.setTitle("Add photos !");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                switch (item){
                    case 0:
                        cameraIntent();
                        break;
                    case 1:
                        galleryIntent();
                        break;
                    case 2:
                        dialog.dismiss();
                        break;

                }



            }
        });
        builder.show();
    }

    private void cameraIntent() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_CAMERA);
    }
//

    private void galleryIntent() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select File"), SELECT_FILE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SELECT_FILE)
                uploadImage(onSelectFromGalleryResult(data));

            else if (requestCode == REQUEST_CAMERA)
                uploadImage(onCaptureImageResult(data));

            else if(requestCode==100)
                updateProfile();


        }
    }

    @SuppressWarnings("deprecation")
    private String onSelectFromGalleryResult(Intent data) {
        String picturePath=null;
        Bitmap bm = null;
        if (data != null) {
            try {
                bm = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), data.getData());
                Uri selectedImage = data.getData();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};
                Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                cursor.moveToFirst();
                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                try {
                    picturePath = getFilePath(HomeActivity.this, selectedImage);
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                }
//                Log.e("Gallery Path", picturePath);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
      return picturePath;
    }

    private String onCaptureImageResult(Intent data) {
        Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
        File destination = new File(Environment.getExternalStorageDirectory(), System.currentTimeMillis() + ".jpg");
        FileOutputStream fo;
        String picturePath=null;
        try {
            destination.createNewFile();
            fo = new FileOutputStream(destination);
            fo.write(bytes.toByteArray());
            fo.close();
            picturePath = destination.getAbsolutePath();
//            Log.e("Camera Path", destination.getAbsolutePath());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return picturePath;
    }

    private void uploadImage(final String filePath) {
        if(filePath!=null||!filePath.isEmpty()){
            final File file=new File(filePath);
            if(file.exists()){
                MessageToast.showToastMethod(this,file.getAbsolutePath());
                RequestBody mFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
//                RequestBody mFile = RequestBody.create(MediaType.parse("image/*"), file);
                MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("profile_pic", file.getName(), mFile);

                RequestBody filename = RequestBody.create(MediaType.parse("text/plain"),manager.getSingleField(SessionManager.KEY_ID));
                Call<RegResult> call = service.uploadFile(fileToUpload, filename);
                showDialog();
                call.enqueue(new Callback<RegResult>() {
                    @Override
                    public void onResponse(Call<RegResult> call, Response<RegResult> response) {
                        hideDialog();
                        RegResult result=response.body();
                        showToast(result.getMessage());
                        if(result.getStatus()==1){
                            manager.profileImageUrl("",filePath);
                            Glide.with(HomeActivity.this)
                                    .load(file)
                                    .error(R.drawable.dummy_user)
                                    .into(profile_pic);
                        }

                    }

                    @Override
                    public void onFailure(Call<RegResult> call, Throwable t) {
                       hideDialog();
                    }
                });

            }
        }
    }
    private void copyFile(File inputFile) {
        String outputPath=getFilesDir()+"/"+"profile";
        InputStream in = null;
        OutputStream out = null;
        try {

            //create output directory if it doesn't exist
            File dir = new File (outputPath);
            if (!dir.exists())
            {
                dir.mkdirs();
            }


            in = new FileInputStream(inputFile);
            out = new FileOutputStream(outputPath + "profile_pic"+getMimeType(inputFile));
            byte[] buffer = new byte[1024];
            int read;
            while ((read = in.read(buffer)) != -1) {
                out.write(buffer, 0, read);
            }
            in.close();
            in = null;

            // write the output file (You have now copied the file)
            out.flush();
            out.close();
            out = null;

        }  catch (FileNotFoundException fnfe1) {
            Log.e("tag", fnfe1.getMessage());
        }
        catch (Exception e) {
            Log.e("tag", e.getMessage());
        }

    }
    public  String getMimeType(File file)
    {
        Uri uri = Uri.fromFile(file);
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        String type = mime.getExtensionFromMimeType(cR.getType(uri));
        return type;
    }

}
