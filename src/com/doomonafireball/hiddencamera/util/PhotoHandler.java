package com.doomonafireball.hiddencamera.util;

import android.content.Context;
import android.hardware.Camera;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.SimpleTimeZone;

public class PhotoHandler implements Camera.PictureCallback {

    private final Context mContext;

    public PhotoHandler(Context context) {
        this.mContext = context;
    }

    @Override
    public void onPictureTaken(byte[] data, Camera camera) {
        File pictureFileDir = getDir();

        if (!pictureFileDir.exists() && !pictureFileDir.mkdirs()) {
            Log.d(Constants.DEBUG_TAG, "Can't create directory to save image.");
            Toast.makeText(mContext, "Can't create directory to save image.", Toast.LENGTH_SHORT).show();
            return;
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyymmddhhmmss");
        String date = dateFormat.format(new Date());
        String photoFile = "Picture_" + date + ".png";

        String filename = pictureFileDir.getPath() + File.separator + photoFile;

        File pictureFile = new File(filename);

        try {
            FileOutputStream fos = new FileOutputStream(pictureFile);
            fos.write(data);
            fos.close();
            Toast.makeText(mContext, "New image saved: " + photoFile, Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Log.d(Constants.DEBUG_TAG, "File " + filename + " not saved: " + e.getMessage());
            Toast.makeText(mContext, "Image could not be saved.", Toast.LENGTH_SHORT).show();
        }
    }

    private File getDir() {
        File sdDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        return new File(sdDir, "HiddenCamera");
    }
}
