package com.example.project;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import org.opencv.android.OpenCVLoader;
import org.opencv.android.Utils;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

import java.io.IOException;

public class blackandwhite extends AppCompatActivity{

    Button select,camera;
    ImageView im;
    Bitmap bitmap;
    Mat mat;
    int call_granted=0;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blackandwhite);

        if(OpenCVLoader.initDebug()) Log.d("LOADED","success");
        else Log.d("LOADED","err");//if open cv library is loaded.

        camera = findViewById(R.id.c1);
        select = findViewById(R.id.select);
        im = findViewById(R.id.image);

        getPermission();

        select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_GET_CONTENT);// go to storage
                i.setType("image/*");//we need image only
                startActivityForResult(i,200);
            }
        });


        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(i,100);

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==200 && data!=null){
            try {
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(),data.getData()); //rectangle of pixels
                im.setImageBitmap(bitmap);
                mat = new Mat();
                Utils.bitmapToMat(bitmap,mat);

                Imgproc.cvtColor(mat,mat,Imgproc.COLOR_RGB2GRAY);
                Utils.matToBitmap(mat,bitmap);
                im.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }


        }

        if(requestCode==100 && data!=null){

            bitmap = (Bitmap) data.getExtras().get("data");
            im.setImageBitmap(bitmap);

            mat = new Mat();
            Utils.bitmapToMat(bitmap,mat);
            Imgproc.cvtColor(mat,mat,Imgproc.COLOR_RGB2GRAY);
            Utils.matToBitmap(mat,bitmap);
            im.setImageBitmap(bitmap);
        }

    }
    public void getPermission(){

        if(ContextCompat.checkSelfPermission(blackandwhite.this,Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED ){
            ActivityCompat.requestPermissions(blackandwhite.this,new String[]{Manifest.permission.CAMERA},300);
        }
        else{
            call_granted=1;
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(grantResults.length>0 && requestCode==300){

            if(grantResults[0] != PackageManager.PERMISSION_GRANTED){
                call_granted=1;
                getPermission();
            }else{
                call_granted=0;
            }


        }
    }
}