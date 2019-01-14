package se.mau.ai0026.assignment2.Listeners;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.util.Log;

import se.mau.ai0026.assignment2.Main.Controller;

public class SensorListener implements SensorEventListener {
    private Controller controller;

    public SensorListener(Controller controller){
        this.controller = controller;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if(event.sensor.getType() == Sensor.TYPE_AMBIENT_TEMPERATURE){
            //A constant describing the temperature.
            //Public static final int TYPE_AMBIENT_TEMPERATURE
            //See SensorEvent.values for mor information
            //Constant value = 13
            //Values[0] = Ambient room temperature in celcius
            float temperature = event.values[0];
            controller.sensorInput("TYPE_AMBIENT_TEMPERATURE", temperature);
        }

        else if(event.sensor.getType() == Sensor.TYPE_RELATIVE_HUMIDITY){
            //A constant describing the humidity
            //Public static final int TYPE_RELATIVE_HUMIDITY
            //See SensorEvent.values for mor information
            //Constant value = 12
            //Values[0] = Relative ambient air humidity in percent
            float humidity = event.values[0];
            controller.sensorInput("TYPE_RELATIVE_HUMIDITY", humidity);
        }

        else if(event.sensor.getType() == Sensor.TYPE_PRESSURE){
            //A constant describing the pressure.
            //Public static final int TYPE_PRESSURE
            //See SensorEvent.values for mor information
            //Constant value = 6
            //Values[0] = Atmospheric pressure in hPa(hecto pascal, millibar)
            float pressure = event.values[0];
            controller.sensorInput("TYPE_PRESSURE", pressure);
        }

        else if(event.sensor.getType() == Sensor.TYPE_PROXIMITY){
            //??
            float proximity = event.values[0];
            Log.d("PROXIMITY", Float.toString(proximity));
            controller.sensorInput("TYPE_PROXIMITY", proximity);
        }

        else if(event.sensor.getType() == Sensor.TYPE_LIGHT){
            float light = event.values[0];
            Log.d("LIGHT", Float.toString(light));
            controller.sensorInput("TYPE_LIGHT", light);
           // if(light > 0 && light <= 100){
             //   controller.changeScreenBrightness(1/light);
           // }

            controller.setLightSensorValue(light);

        //    controller.setCurrentLightValue(light);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
