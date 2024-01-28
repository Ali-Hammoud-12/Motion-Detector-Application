package com.example.project;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import androidx.annotation.NonNull;

import android.Manifest;
import android.content.pm.PackageManager;

import org.opencv.android.CameraActivity;
import org.opencv.android.CameraBridgeViewBase;
import org.opencv.android.OpenCVLoader;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class track extends CameraActivity {

    CameraBridgeViewBase cameraBridgeViewBase;
    Mat curr_grey,prev_grey,rgb, diff;
    boolean is_init;
    List<MatOfPoint> cnts;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track);

        getpermission();

        is_init = false;
        cameraBridgeViewBase = findViewById(R.id.camera);
        cameraBridgeViewBase.setCvCameraViewListener(new CameraBridgeViewBase.CvCameraViewListener2() {
            @Override
            public void onCameraViewStarted(int width, int height) {

                curr_grey = new Mat();
                prev_grey = new Mat();
                rgb = new Mat();
                diff = new Mat();
                cnts = new ArrayList<>();

            }

            @Override
            public void onCameraViewStopped() {

            }

            @Override
            public Mat onCameraFrame(CameraBridgeViewBase.CvCameraViewFrame inputFrame) {

                if(!is_init){
                    prev_grey = inputFrame.gray();
                    is_init = true;
                    return prev_grey;
                }
                rgb = inputFrame.rgba();
                curr_grey = inputFrame.gray();

                //todo: detect noises

                Core.absdiff(curr_grey,prev_grey,diff);
                Imgproc.threshold(diff,diff,40,255, Imgproc.THRESH_BINARY);
                Imgproc.findContours(diff,cnts,new Mat(),Imgproc.RETR_TREE,Imgproc.CHAIN_APPROX_SIMPLE);

                Imgproc.drawContours(rgb,cnts,-1,new Scalar(255,0,0),4);

                for(MatOfPoint m:cnts){

                    Rect r = Imgproc.boundingRect(m);
                    Imgproc.rectangle(rgb,r,new Scalar(0,0,255), 4);
                }

                cnts.clear();
                /////////
                prev_grey = curr_grey.clone();
                return rgb;
            }
        });

        if(OpenCVLoader.initDebug()){
            cameraBridgeViewBase.enableView();
        }

    }

    @Override
    protected List<? extends CameraBridgeViewBase> getCameraViewList() {
        return Collections.singletonList(cameraBridgeViewBase);
    }

    public void getpermission(){
        if(checkSelfPermission(Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED){

            requestPermissions(new String[]{Manifest.permission.CAMERA},100);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(grantResults[0]!=PackageManager.PERMISSION_GRANTED  && grantResults.length>0){
            getpermission();

        }
    }
}