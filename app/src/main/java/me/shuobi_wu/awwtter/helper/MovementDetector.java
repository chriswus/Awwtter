package me.shuobi_wu.awwtter.helper;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;

import java.util.HashSet;

import me.shuobi_wu.awwtter.model.GameModel;

/**
 * A class that detect movement and make changes
 */

public class MovementDetector extends Activity implements SensorEventListener {

    protected final String TAG = getClass().getSimpleName();

    private SensorManager sensorMan;
    private Sensor accelerometer;

    private float[] mGravity;
    private float mAccel;
    private float mAccelCurrent;
    private float mAccelLast;
    private GameModel mGameModel;

    private MovementDetector(GameModel model) {
        this.mGameModel = model;
    }

    private static MovementDetector mInstance;

    public static MovementDetector getInstance(GameModel model) {
        if (mInstance == null) {
            mInstance = new MovementDetector(model);
            mInstance.init();
        }
        return mInstance;
    }

    private HashSet<Listener> mListeners = new HashSet<MovementDetector.Listener>();

    private void init() {
        sensorMan = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        accelerometer = sensorMan.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);
    }

    public void start() {
        sensorMan.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }

    public void stop() {
        sensorMan.unregisterListener(this);
    }

    public void addListener(Listener listener) {
        mListeners.add(listener);
    }

    /* (non-Javadoc)
     * @see android.hardware.SensorEventListener#onSensorChanged(android.hardware.SensorEvent)
     */
    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER){
            mGravity = event.values.clone();
            // Shake detection
            float x = mGravity[0];
            float y = mGravity[1];
            float z = mGravity[2];
            mAccelLast = mAccelCurrent;
            mAccelCurrent = (float) Math.sqrt(x*x + y*y + z*z);
            float delta = mAccelCurrent - mAccelLast;
            mAccel = mAccel * 0.9f + delta;
            // Make this higher or lower according to how much
            // motion you want to detect
            if(mAccel > 3){
                Log.e("Movement", "movement detected");
                mGameModel.addEnergy(5);
            }
        }

    }

    /* (non-Javadoc)
     * @see android.hardware.SensorEventListener#onAccuracyChanged(android.hardware.Sensor, int)
     */
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // TODO Auto-generated method stub

    }

    public interface Listener {
        void onMotionDetected(SensorEvent event, float acceleration);
    }
}