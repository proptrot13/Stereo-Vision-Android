package com.example.lawrence.focusrangefinder.FocusTestActivity;

/**
 * Created by Lawrence on 11/26/2018.
 */


import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.lawrence.focusrangefinder.CameraPreview;
import com.example.lawrence.focusrangefinder.R;

import java.util.concurrent.TimeUnit;

public class FocusTestActivity extends AppCompatActivity {

    private Camera camera1;
    private Camera camera2;
    private CameraPreview mPreview;
    private TextView distance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.focus_main);

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED) {

            // Permission is not granted
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.READ_CONTACTS)) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
            } else {
                // No explanation needed; request the permission
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_CONTACTS},
                        1);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        } else {
            // Permission has already been granted
        }

        camera1 = getCameraInstance(-1);


        mPreview = new CameraPreview(this, camera1);
        FrameLayout preview = (FrameLayout) findViewById(R.id.camera_previews);
        preview.addView(mPreview);
        distance = (TextView) findViewById(R.id.distances);


            float[] distanceEstimation = new float[3];
            camera1.getParameters().getFocusDistances(distanceEstimation);

            if (distanceEstimation[1] != Float.POSITIVE_INFINITY && distanceEstimation[1] != Float.NEGATIVE_INFINITY) {
                distance.setText((int) (distanceEstimation[1]));
            } else {
                distance.setText("Error");
            }
            try {
                TimeUnit.SECONDS.sleep(1);
            }
            catch (Exception e) {

            }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    public static Camera getCameraInstance (int num) {
        Camera c = null;
        try {
            if(num == -1) {
                c = Camera.open();
            }
            else {
                c = Camera.open(num);
            }
        }
        catch (Exception e) {
            System.exit(0);
        }
        return c;
    }

    private boolean checkCameraHardware(Context context) {
        if (context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)){
            // this device has a camera
            return true;
        } else {
            // no camera on this device
            return false;
        }
    }

}
