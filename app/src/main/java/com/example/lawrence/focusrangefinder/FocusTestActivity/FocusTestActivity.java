package com.example.lawrence.focusrangefinder.FocusTestActivity;

/**
 * Created by Lawrence on 11/26/2018.
 */


import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.lawrence.focusrangefinder.CameraPreview;
import com.example.lawrence.focusrangefinder.R;

public class FocusTestActivity extends AppCompatActivity {

    private Camera camera1;
    private Camera camera2;
    private CameraPreview mPreview;
    private TextView distance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main);

        if(!checkCameraHardware(this)) {
            System.out.println("no camera");
        }

        camera1 = getCameraInstance(0);
        camera2 = getCameraInstance(1);


        mPreview = new CameraPreview(this, camera1);
        FrameLayout preview = (FrameLayout) findViewById(R.id.camera_preview);
        preview.addView(mPreview);
        distance = (TextView) findViewById(R.id.distance);
        float[] distanceEstimation = new float[3];
        camera1.getParameters().getFocusDistances(distanceEstimation);

        if(distanceEstimation[1] != Float.POSITIVE_INFINITY && distanceEstimation[1] != Float.NEGATIVE_INFINITY) {
            distance.setText((int) (distanceEstimation[1]));
        }
        else {
            distance.setText("Error");
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
            c = Camera.open(num);
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
