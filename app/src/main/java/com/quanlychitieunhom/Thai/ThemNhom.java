package com.quanlychitieunhom.Thai;

import static android.content.ContentValues.TAG;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.quanlychitieunhom.R;

//import retrofit2.Call;
//import retrofit2.Callback;
//import retrofit2.Response;

public class ThemNhom extends AppCompatActivity {
    private static final int MY_REQUEST_CODE = 10;
    //   private Uri mUri;
    private ImageView imgAvatar;
    EditText edtTenNhom;
    Button btnTaiAnh, btnTaoNhom;
    private ActivityResultLauncher<Intent> mActivityLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult o) {
                    Log.e(TAG,"onActivityResult");
                    if (o.getResultCode() == Activity.RESULT_OK) {
                        Intent data = o.getData();
                        if(data == null){
                            return;
                        }
                        Uri uri = data.getData();
                        try {
                            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),uri);
                            imgAvatar.setImageBitmap(bitmap);
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }

                }
            }
    );
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_sheet_taonhom);
        btnTaiAnh = findViewById(R.id.btnChonHinhNen);
        imgAvatar = findViewById(R.id.imgAnhNhom);
        btnTaoNhom = findViewById(R.id.btnTaoNhom);
        edtTenNhom = findViewById(R.id.edtTenNhom);
        btnTaiAnh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickRequestPermission();
            }
        });
        btnTaoNhom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }
    private void onClickRequestPermission(){
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            openGallery();
            return;
        }
        if(checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
            openGallery();
        }else{
            String [] permission = {android.Manifest.permission.READ_EXTERNAL_STORAGE};
            requestPermissions(permission,MY_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode != MY_REQUEST_CODE){
            openGallery();
        }

    }

    private void openGallery(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        mActivityLauncher.launch(Intent.createChooser(intent,"Chọn ảnh"));

    }
}
