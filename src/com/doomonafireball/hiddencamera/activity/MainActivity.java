package com.doomonafireball.hiddencamera.activity;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;
import com.doomonafireball.hiddencamera.R;
import com.doomonafireball.hiddencamera.util.Constants;
import com.doomonafireball.hiddencamera.util.PhotoHandler;
import com.doomonafireball.hiddencamera.views.CameraSurfaceView;

public class MainActivity extends Activity {
    private Camera mCamera;
    private CameraSurfaceView mCameraSurfaceView;
    private Context mContext;
    private int mCameraId = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        mContext = this;

        // Check to see if we have a Camera
        if (!getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
            Toast.makeText(mContext, "No camera on this device.", Toast.LENGTH_SHORT).show();
        } else {
            mCameraId = findBackFacingCamera();
            mCamera = Camera.open(mCameraId);
            if (mCameraId < 0) {
                Toast.makeText(mContext, "No back-facing camera found.", Toast.LENGTH_SHORT).show();
            } else {
                mCameraSurfaceView = new CameraSurfaceView(this);
                mCamera = mCameraSurfaceView.getCamera();
                FrameLayout preview = (FrameLayout) findViewById(R.id.FL_preview);
                preview.addView(mCameraSurfaceView);

                Button takePicBTN = (Button) findViewById(R.id.BTN_take_picture);
                takePicBTN.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mCamera.takePicture(null, null, new PhotoHandler(mContext));
                    }
                });
            }
        }
    }

    private int findBackFacingCamera() {
        int cameraId = -1;
        int numberOfCameras = Camera.getNumberOfCameras();
        for (int i = 0; i < numberOfCameras; i++) {
            Camera.CameraInfo info = new Camera.CameraInfo();
            Camera.getCameraInfo(i, info);
            if (info.facing == Camera.CameraInfo.CAMERA_FACING_BACK) {
                Log.d(Constants.DEBUG_TAG, "Back-facing Camera found.");
                mCameraId = i;
                break;
            }
        }
        return cameraId;
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mCamera != null) {
            mCamera.release();
            mCamera = null;
        }
    }
}
