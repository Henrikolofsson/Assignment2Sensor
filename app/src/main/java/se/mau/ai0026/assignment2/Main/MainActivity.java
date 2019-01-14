package se.mau.ai0026.assignment2.Main;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;


import se.mau.ai0026.assignment2.Listeners.SensorListener;
import se.mau.ai0026.assignment2.Main.Controller;
import se.mau.ai0026.assignment2.R;

public class MainActivity extends AppCompatActivity {
    private FragmentManager fm;
    private Controller controller;
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;

    private SensorManager mSensorManager;
    private Sensor mTemperatureSensor;
    private Sensor mLocalPressureSensor;
    private Sensor mHumiditySensor;
    private Sensor mProximitySensor;
    private Sensor mLightSensor;
    private boolean isTemperatureSensorPresent;
    private boolean isLocalPressureSensorPresent;
    private boolean isHumiditySensorPresent;
    private boolean isProximitySensorPresent;
    private boolean isLightSensorPresent;
    private SensorListener mSensorListener;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initComponents();
        //checkPermission();
        initSensors();
    }


    private void initComponents(){
        fm = getSupportFragmentManager();
        controller = new Controller(this);

        toolbar = (Toolbar) findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);

        final ActionBar actionBar = getSupportActionBar();
        getSupportActionBar().setTitle("Sensors");
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.menu_navigation);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.navigation_drawer);
        setNavigationViewListener();

        mSensorListener = new SensorListener(controller);
    }

    private void initSensors(){
        initializeTemperatureSensor();
        initializeHumiditySensor();
        initializeLocalPressureSensor();
        initializeProximitySensor();
        initializeLightSensor();
    }

    private void initializeTemperatureSensor(){
        mSensorManager = (SensorManager) this.getSystemService(Context.SENSOR_SERVICE);
        if(mSensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE) != null){
            mTemperatureSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);
            isTemperatureSensorPresent = true;
        } else {
         //   mTempValue.setText("Ambient temperature not available!");
            isTemperatureSensorPresent = false;
        }
    }

    private void initializeHumiditySensor(){
        mSensorManager = (SensorManager) this.getSystemService(Context.SENSOR_SERVICE);
        if(mSensorManager.getDefaultSensor(Sensor.TYPE_RELATIVE_HUMIDITY) != null){
            mHumiditySensor = mSensorManager.getDefaultSensor(Sensor.TYPE_RELATIVE_HUMIDITY);
            isHumiditySensorPresent = true;
        } else {
          //  mHumidityValue.setText("Relative humidity is not available");
            isHumiditySensorPresent = false;
        }
    }

    private void initializeLocalPressureSensor(){
        mSensorManager = (SensorManager) this.getSystemService(Context.SENSOR_SERVICE);
        if(mSensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE) != null){
            mLocalPressureSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE);
            isLocalPressureSensorPresent = true;
        } else {
         //  mLocalPressure.setText("Pressure is not available!");
            isLocalPressureSensorPresent = false;
        }
    }

    private void initializeProximitySensor(){
        mSensorManager = (SensorManager) this.getSystemService(Context.SENSOR_SERVICE);
        if(mSensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY) != null){
            mProximitySensor = mSensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
            isProximitySensorPresent = true;
        } else {
            isProximitySensorPresent = false;
        }
    }

    private void initializeLightSensor(){
        mSensorManager = (SensorManager) this.getSystemService(Context.SENSOR_SERVICE);
        if(mSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT) != null){
            mLightSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
            isLightSensorPresent = true;
        } else {
            isLightSensorPresent = false;
        }
    }

    public void checkSensors(){     //Fault in implementation, unregistering all sensors.
        if(isTemperatureSensorPresent){
            mSensorManager.unregisterListener(mSensorListener);
            Toast.makeText(this, "UNREGISTER TEMPERATURE LISTENER", Toast.LENGTH_SHORT);
            isTemperatureSensorPresent = false;
        }
        if(isHumiditySensorPresent){
            mSensorManager.unregisterListener(mSensorListener);
            Toast.makeText(this, "UNREGISTER HUMIDITY LISTENER", Toast.LENGTH_SHORT);
            isHumiditySensorPresent = false;
        }
        if(isLocalPressureSensorPresent){
            mSensorManager.unregisterListener(mSensorListener);
            Toast.makeText(this, "UNREGISTER PRESSURE LISTENER", Toast.LENGTH_SHORT);
            isLocalPressureSensorPresent = false;
        }
        if(isProximitySensorPresent){
            mSensorManager.unregisterListener(mSensorListener);
            Toast.makeText(this, "UNREGISTER PROXIMITY LISTENER", Toast.LENGTH_SHORT);
            isProximitySensorPresent = false;
        }
        if(isLightSensorPresent){
            mSensorManager.unregisterListener(mSensorListener);
            Toast.makeText(this, "UNREGISTER LIGHT LISTENER", Toast.LENGTH_SHORT);
            isLightSensorPresent = false;
        }
    }

    public void onBackPressed(){
        if(controller.onBackPressed()){
            super.onBackPressed();
        }
    }

    public void setFragment(Fragment fragment, String tag){
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.main_container, fragment, tag);
        fragmentTransaction.commit();
    }

    public void addFragment(Fragment fragment, String tag){
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.add(fragment, tag);
        fragmentTransaction.commit();
    }

    public Fragment getFragment(String tag){
        return fm.findFragmentByTag(tag);
    }

    private void setNavigationViewListener(){
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch(menuItem.getItemId()){
                    case R.id.assignment2_item:
                        menuItem.setChecked(true);
                        drawerLayout.closeDrawers();
                        break;

                    case R.id.assignment3_item:
                        menuItem.setChecked(true);
                        drawerLayout.closeDrawers();
                        break;
                }
                return false;
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(isTemperatureSensorPresent){
            mSensorManager.registerListener(mSensorListener, mTemperatureSensor, SensorManager.SENSOR_DELAY_NORMAL);
            Toast.makeText(this, "REGISTER TEMPERATURE LISTENER", Toast.LENGTH_SHORT);
        }
        if(isHumiditySensorPresent){
            mSensorManager.registerListener(mSensorListener, mHumiditySensor, SensorManager.SENSOR_DELAY_NORMAL);
            Toast.makeText(this, "REGISTER HUMIDITY LISTENER", Toast.LENGTH_SHORT);
        }
        if(isLocalPressureSensorPresent){
            mSensorManager.registerListener(mSensorListener, mLocalPressureSensor, SensorManager.SENSOR_DELAY_NORMAL);
            Toast.makeText(this, "REGISTER PRESSURE LISTENER", Toast.LENGTH_SHORT);
        }
        if(isProximitySensorPresent){
            mSensorManager.registerListener(mSensorListener, mProximitySensor, SensorManager.SENSOR_DELAY_NORMAL);
            Toast.makeText(this, "REGISTER PROXIMITY LISTENER", Toast.LENGTH_SHORT);
        }
        if(isLightSensorPresent){
            mSensorManager.registerListener(mSensorListener, mLightSensor, SensorManager.SENSOR_DELAY_NORMAL);
            Toast.makeText(this, "REGISTER LIGHT LISTENER", Toast.LENGTH_SHORT);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        checkSensors();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        checkSensors();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void checkPermission(float value){


    }


}
