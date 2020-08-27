package com.me.noban.DengueSolution.pulseRate;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.Camera;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.WindowManager;
import android.widget.TextView;

import com.gauravbhola.ripplepulsebackground.RipplePulseLayout;
import com.me.noban.DengueSolution.R;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicBoolean;

import pl.bclogic.pulsator4droid.library.PulsatorLayout;


public class pulsMeasure extends AppCompatActivity implements SurfaceHolder.Callback {

    private static final int averageArraySize = 4;
    private static final int[] averageArray = new int[averageArraySize];
    private static final AtomicBoolean processing = new AtomicBoolean(false);
    private static final int beatsArraySize = 3;
    private static final int[] beatsArray = new int[beatsArraySize];
    public static Context c;
    static TextView tv;
    private static int averageIndex = 0;
    private static double beats = 0;
    private static long startTime = 0;
    private static int beatsIndex = 0;
    private static TYPE currentType = TYPE.GREEN;
    public Camera mCamera;
    SurfaceHolder SurfaceHolder;
    SurfaceView SurfaceView;
    boolean mPreviewRunning;

    ;
    RipplePulseLayout mRipplePulseLayout;
    /**
     * FROM: https://github.com/phishman3579/android-heart-rate-monitor
     */
    private Camera.PreviewCallback previewCallback = new Camera.PreviewCallback() {


        @Override
        public void onPreviewFrame(byte[] data, Camera camera) {
            if (data == null) throw new NullPointerException();
            Camera.Size size = camera.getParameters().getPreviewSize();
            if (size == null) throw new NullPointerException();

            if (!processing.compareAndSet(false, true)) return;

            int width = size.width;
            int height = size.height;

            int imgAvg = ImageProcessing.decodeYUV420SPtoRedAvg(data.clone(), height, width);
            // Log.i(TAG, "imgAvg="+imgAvg);
            if (imgAvg == 0 || imgAvg == 255) {
                processing.set(false);
                return;
            }

            int averageArrayAvg = 0;
            int averageArrayCnt = 0;
            for (int i = 0; i < averageArray.length; i++) {
                if (averageArray[i] > 0) {
                    averageArrayAvg += averageArray[i];
                    averageArrayCnt++;
                }
            }

            int rollingAverage = (averageArrayCnt > 0) ? (averageArrayAvg / averageArrayCnt) : 0;

            TYPE newType = currentType;

            if (imgAvg < rollingAverage) {
                newType = TYPE.RED;
                if (newType != currentType) {
                    beats++;
                    // Log.d(TAG, "BEAT!! beats="+beats);
                }
            } else if (imgAvg > rollingAverage) {
                newType = TYPE.GREEN;
            }

            if (averageIndex == averageArraySize) averageIndex = 0;
            averageArray[averageIndex] = imgAvg;
            averageIndex++;

            // Transitioned from one state to another to the same
            /*if (newType != currentType) {
                currentType = newType;
            }*/

            long endTime = System.currentTimeMillis();
            double totalTimeInSecs = (endTime - startTime) / 1000d;
            if (totalTimeInSecs >= 10) {
                double bps = (beats / totalTimeInSecs);
                int dpm = (int) (bps * 60d);
                if (dpm < 50 || dpm > 180) {
                    startTime = System.currentTimeMillis();
                    beats = 0;
                    processing.set(false);
                    return;
                }

                // Log.d(TAG,
                // "totalTimeInSecs="+totalTimeInSecs+" beats="+beats);

                if (beatsIndex == beatsArraySize) beatsIndex = 0;
                beatsArray[beatsIndex] = dpm;
                beatsIndex++;

                int beatsArrayAvg = 0;
                int beatsArrayCnt = 0;
                for (int i = 0; i < beatsArray.length; i++) {
                    if (beatsArray[i] > 0) {
                        beatsArrayAvg += beatsArray[i];
                        beatsArrayCnt++;
                    }
                }
                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(c);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                int beatsAvg = (beatsArrayAvg / beatsArrayCnt);

                //Toast t = Toast.makeText(c, String.valueOf(beatsAvg), Toast.LENGTH_LONG);
                if (beatsAvg > 100 && beatsAvg < 120) {
                    //   tv.setText(String.valueOf(beatsAvg-22));
                    editor.putString("LAST_MEASURE", String.valueOf(beatsAvg - 22));
                    go();

                }
                if (beatsAvg > 60 && beatsAvg < 70) {
                    //   tv.setText(String.valueOf(beatsAvg+13));
                    editor.putString("LAST_MEASURE", String.valueOf(beatsAvg + 13));
                    go();

                }
                if (beatsAvg > 120 && beatsAvg < 180) {
                    if (beatsAvg > 100 && beatsAvg < 120) {
                        //    tv.setText(String.valueOf(beatsAvg-22));
                        editor.putString("LAST_MEASURE", String.valueOf(beatsAvg - 22));
                        go();

                    }
                    if (beatsAvg > 120 && beatsAvg < 140) {
                        //  tv.setText(String.valueOf(beatsAvg-42));
                        editor.putString("LAST_MEASURE", String.valueOf(beatsAvg - 42));
                        go();

                    }
                    if (beatsAvg > 140 && beatsAvg < 160) {
                        //   tv.setText(String.valueOf(beatsAvg-62));
                        editor.putString("LAST_MEASURE", String.valueOf(beatsAvg - 62));
                        go();

                    }
                    if (beatsAvg > 160 && beatsAvg < 180) {
                        // tv.setText(String.valueOf(beatsAvg-82));

                        editor.putString("LAST_MEASURE", String.valueOf(beatsAvg - 82));
                        go();

                    }

                }


                editor.commit();

                startTime = System.currentTimeMillis();
                beats = 0;
            }

            processing.set(false);
        }

    };

    public static TYPE getCurrent() {
        return currentType;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_puls_measure);

        tv = findViewById(R.id.puls_cal);

        PulsatorLayout pulsator = (PulsatorLayout) findViewById(R.id.pulsator);
        pulsator.start();
        SurfaceView = (SurfaceView) findViewById(R.id.preview);
        SurfaceHolder = SurfaceView.getHolder();
        SurfaceHolder.addCallback(this);
        SurfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

        c = getApplicationContext();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SurfaceView.getHolder().removeCallback(this);
    }

    public void onPause() {
        super.onPause();
        if (mCamera != null) {
            mCamera.setPreviewCallback(null);
            SurfaceView.getHolder().removeCallback(this);
            mCamera.release();
        }
    }

    /*private static Camera.Size getSmallestPreviewSize(int width, int height, Camera.Parameters parameters) {
        Camera.Size result = null;

        for (Camera.Size size : parameters.getSupportedPreviewSizes()) {
            if (size.width <= width && size.height <= height) {
                if (result == null) {
                    result = size;
                } else {
                    int resultArea = result.width * result.height;
                    int newArea = size.width * size.height;
                    if (newArea < resultArea) result = size;
                }
            }
        }

        return result;
    }*/

    @Override
    public void surfaceCreated(android.view.SurfaceHolder holder) {
        mCamera = Camera.open();
        Camera.Parameters parameters = mCamera.getParameters();
        if (parameters.getMaxExposureCompensation() != parameters.getMinExposureCompensation()) {
            parameters.setExposureCompensation(0);
        }
        /*if(parameters.isAutoExposureLockSupported()){
            parameters.setAutoExposureLock(true);
         }*/
        /*if(parameters.isAutoWhiteBalanceLockSupported()){
            parameters.setAutoWhiteBalanceLock(true);
        }*/
        mCamera.setParameters(parameters);
        mCamera.setPreviewCallback(previewCallback);
        startTime = System.currentTimeMillis();
    }

    @Override
    public void surfaceChanged(android.view.SurfaceHolder arg0, int arg1, int arg2, int arg3) {
        if (mPreviewRunning) {
            mCamera.stopPreview();
        }
        Camera.Parameters p = mCamera.getParameters();
        p.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
        mCamera.setParameters(p);
        try {
            mCamera.setPreviewDisplay(arg0);
        } catch (IOException e) {
            e.printStackTrace();
        }
        setCameraDisplayOrientation(mCamera);

        mCamera.startPreview();
        mPreviewRunning = true;
    }

    @Override
    public void surfaceDestroyed(android.view.SurfaceHolder holder) {
        mCamera.stopPreview();
        mPreviewRunning = false;
        mCamera.release();
    }

    /**
     * FROM: http://stackoverflow.com/questions/4645960/how-to-set-android-camera-orientation-properly
     *
     * @param camera
     */
    public void setCameraDisplayOrientation(android.hardware.Camera camera) {
        Camera.Parameters parameters = camera.getParameters();
        android.hardware.Camera.CameraInfo camInfo = new android.hardware.Camera.CameraInfo();
        android.hardware.Camera.getCameraInfo(0, camInfo);

        Display display = ((WindowManager) this.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        int rotation = display.getRotation();
        int degrees = 0;
        switch (rotation) {
            case Surface.ROTATION_0:
                degrees = 0;
                break;
            case Surface.ROTATION_90:
                degrees = 90;
                break;
            case Surface.ROTATION_180:
                degrees = 180;
                break;
            case Surface.ROTATION_270:
                degrees = 270;
                break;
        }

        int result;
        if (camInfo.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
            result = (camInfo.orientation + degrees) % 360;
            result = (360 - result) % 360;  // compensate the mirror
        } else {  // back-facing
            result = (camInfo.orientation - degrees + 360) % 360;
        }
        camera.setDisplayOrientation(result);
    }

    private void go() {
        Intent intent = new Intent(this, pulsrateMain.class);
        startActivity(intent);
    }

    public enum TYPE {
        GREEN, RED
    }
}