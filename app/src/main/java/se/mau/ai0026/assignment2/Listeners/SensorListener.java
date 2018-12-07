package se.mau.ai0026.assignment2.Listeners;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;

public class SensorListener implements SensorEventListener {

    @Override
    public void onSensorChanged(SensorEvent event) {
        if(event.sensor.getType() == Sensor.TYPE_AMBIENT_TEMPERATURE){

        } else if(event.sensor.getType() == Sensor.TYPE_RELATIVE_HUMIDITY){

        } else if(event.sensor.getType() == )
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
