package org.tensorflow.lite.examples.detection;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;

import androidx.appcompat.app.AppCompatActivity;

import android.widget.TextView;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


import android.os.Bundle;

public class Sensors extends AppCompatActivity implements SensorEventListener {


    private static final int N_SAMPLES = 200;
    private static List<Float> x;
    private static List<Float> y;
    private static List<Float> z;

    private TextView smoothTextView;
    private TextView potholeTextView;
    private float[] results;
    private TensorFlowClassifier classifier;

    private String[] labels = {"", "", "", "smooth", "pothole", ""};

    private LocationDelegate locationDelegate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor);
        x = new ArrayList<>();
        y = new ArrayList<>();
        z = new ArrayList<>();

        locationDelegate = new LocationDelegate(this);

        smoothTextView = (TextView) findViewById(R.id.smooth_prob);
        potholeTextView = (TextView) findViewById(R.id.pothole_prob);

        classifier = new TensorFlowClassifier(getApplicationContext());
    }


    protected void onPause() {
        getSensorManager().unregisterListener(this);
        super.onPause();
    }

    protected void onResume() {
        super.onResume();
        getSensorManager().registerListener(this, getSensorManager().getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_GAME);
    }

    @Override
    public synchronized void onDestroy() {
        super.onDestroy();
        locationDelegate.destroy();
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        activityPrediction();
        x.add(event.values[0]);
        y.add(event.values[1]);
        z.add(event.values[2]);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    private void activityPrediction() {
        if (x.size() == N_SAMPLES && y.size() == N_SAMPLES && z.size() == N_SAMPLES) {
            List<Float> data = new ArrayList<>();
            data.addAll(x);
            data.addAll(y);
            data.addAll(z);

            results = classifier.predictProbabilities(toFloatArray(data));
            smoothTextView.setText(Float.toString(round(results[3], 2)));
            potholeTextView.setText(Float.toString(round(results[4], 2)));

            if (results[4] > 0.20f) {
                locationDelegate.sendLocation();
            }

            x.clear();
            y.clear();
            z.clear();
        }
    }

    private float[] toFloatArray(List<Float> list) {
        int i = 0;
        float[] array = new float[list.size()];

        for (Float f : list) {
            array[i++] = (f != null ? f : Float.NaN);
        }
        return array;
    }

    private static float round(float d, int decimalPlace) {
        BigDecimal bd = new BigDecimal(Float.toString(d));
        bd = bd.setScale(decimalPlace, BigDecimal.ROUND_HALF_UP);
        return bd.floatValue();
    }

    private SensorManager getSensorManager() {
        return (SensorManager) getSystemService(SENSOR_SERVICE);
    }
}
