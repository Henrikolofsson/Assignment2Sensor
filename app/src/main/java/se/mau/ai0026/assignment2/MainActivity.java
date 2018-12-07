package se.mau.ai0026.assignment2;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import se.mau.ai0026.assignment2.Listeners.SensorListener;

public class MainActivity extends AppCompatActivity {
    private SensorManager mSensorManager;

    private Sensor mTemperatureSensor;
    private Sensor mLocalPressureSensor;
    private Sensor mHumiditySensor;

    private boolean isTemperatureSensorPresent;
    private boolean isLocalPressureSensorPresent;
    private boolean isHumiditySensorPresent;
    private SensorListener mSensorListener;

    private TextView mTempValue;
    private TextView mLocalPressure;
    private TextView mHumidityValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initComponents();
        initializeTemperatureSensor();
        initializeHumiditySensor();
        initializeLocalPressureSensor();

    }

    private void initComponents(){
        mTempValue = (TextView) findViewById(R.id.tempText);
        mLocalPressure = (TextView) findViewById(R.id.localPressure);
        mHumidityValue = (TextView) findViewById(R.id.humidityValue);
        mSensorListener = new SensorListener();
    }

    private void initializeTemperatureSensor(){
        mSensorManager = (SensorManager) this.getSystemService(Context.SENSOR_SERVICE);
        if(mSensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE) != null){
            mTemperatureSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);
            isTemperatureSensorPresent = true;
        } else {
            mTempValue.setText("Ambient temperature not available!");
            isTemperatureSensorPresent = false;
        }
    }

    private void initializeHumiditySensor(){
        mSensorManager = (SensorManager) this.getSystemService(Context.SENSOR_SERVICE);
        if(mSensorManager.getDefaultSensor(Sensor.TYPE_RELATIVE_HUMIDITY) != null){
            mHumiditySensor = mSensorManager.getDefaultSensor(Sensor.TYPE_RELATIVE_HUMIDITY);
            isHumiditySensorPresent = true;
        } else {
            mHumidityValue.setText("Relative humidity is not available");
            isHumiditySensorPresent = false;
        }
    }

    private void initializeLocalPressureSensor(){
        mSensorManager = (SensorManager) this.getSystemService(Context.SENSOR_SERVICE);
        if(mSensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE) != null){
            mLocalPressureSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE);
            isLocalPressureSensorPresent = true;
        } else {
            mLocalPressure.setText("Pressure is not available!");
            isLocalPressureSensorPresent = false;
        }
    }



    @Override
    protected void onResume() {
        super.onResume();
        if(isSensorPresent){
            mSensorManager.registerListener(mSensorListener, mSensor, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(isSensorPresent){
            mSensorManager.unregisterListener(mSensorListener);
        }
    }
}
