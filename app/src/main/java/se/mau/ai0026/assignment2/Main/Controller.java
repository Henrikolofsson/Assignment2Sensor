package se.mau.ai0026.assignment2.Main;

import android.content.ContentResolver;
import android.content.Intent;
import android.nfc.Tag;
import android.os.AsyncTask;
import android.provider.Settings;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import se.mau.ai0026.assignment2.Entities.Weather;
import se.mau.ai0026.assignment2.Fragment.APIFragment;
import se.mau.ai0026.assignment2.Fragment.BrightnessFragment;
import se.mau.ai0026.assignment2.Fragment.DataFragment;
import se.mau.ai0026.assignment2.Fragment.SensorFragment;
import se.mau.ai0026.assignment2.Fragment.StartFragment;
import se.mau.ai0026.assignment2.Fragment.WeatherFragment;

import static java.security.AccessController.getContext;

public class Controller {
    private MainActivity mainActivity;
    private List<Weather> sensorContent;
    private List<Weather> APIContent;
    private final static String API_KEY = "a268f0547f91575628dc6c1121b1b54e";
    private final static String malmoe_url =
            "http://api.openweathermap.org/data/2.5/weather?id=2692969&appid=a268f0547f91575628dc6c1121b1b54e";

    private DataFragment dataFragment;
    private StartFragment startFragment;
    private SensorFragment sensorFragment;
    private APIFragment apiFragment;
    private WeatherFragment weatherFragment;
    private BrightnessFragment brightnessFragment;

    private Window window;
    private ContentResolver contentResolver;
    private float currentLightValue;
    private float wantedSystemBrightness;

    public Controller(MainActivity mainActivity){
        this.mainActivity = mainActivity;
        initializeDataFragment();
        initializeAllFragments();
        initArrayLists();
        initScreenBrightness();

    }

    private void initializeDataFragment(){
        dataFragment = (DataFragment) mainActivity.getFragment("DataFragment");
        if(dataFragment == null){
            dataFragment = new DataFragment();
            mainActivity.addFragment(dataFragment, "DataFragment");
            dataFragment.setActiveFragment("WeatherFragment"); //Sätt till StartFragment efter test
        }
    }

    private void initializeAllFragments(){
        initializeStartFragment();
        initializeSensorFragment();
        initializeApiFragment();
        initializeWeatherFragment();
        initializeBrightnessFragment();
    }

    private void initializeStartFragment(){
        startFragment = (StartFragment) mainActivity.getFragment("StartFragment");
        if(startFragment == null){
            startFragment = new StartFragment();
        }
        startFragment.setController(this);
        //mainActivity.setFragment(startFragment, "StartFragment");
    }

    private void initializeSensorFragment(){
        sensorFragment = (SensorFragment) mainActivity.getFragment("SensorFragment");
        if(sensorFragment == null){
            sensorFragment = new SensorFragment();
        }
        sensorFragment.setController(this);
    }

    private void initializeApiFragment(){
        apiFragment = (APIFragment) mainActivity.getFragment("APIFragment");
        if(apiFragment == null){
            apiFragment = new APIFragment();
        }
        apiFragment.setController(this);
    }

    private void initializeWeatherFragment(){
        weatherFragment = (WeatherFragment) mainActivity.getFragment("WeatherFragment");
        if(weatherFragment == null){
            weatherFragment = new WeatherFragment();
        }
        weatherFragment.setController(this);
        weatherFragment.setSensorFragment(sensorFragment);
        weatherFragment.setApiFragment(apiFragment);
      //  mainActivity.setFragment(weatherFragment, "WeatherFragment");
        //sendContent();
    }

    private void initializeBrightnessFragment(){
        brightnessFragment = (BrightnessFragment) mainActivity.getFragment("BrightnessFragment");
        if(brightnessFragment == null){
            brightnessFragment = new BrightnessFragment();
        }
        brightnessFragment.setController(this);
        mainActivity.setFragment(brightnessFragment, "BrightnessFragment");
    }

    private void initArrayLists(){      //Fråga jose om bättre lösning
        sensorContent = new ArrayList<>();
        sensorContent.add(new Weather("nothing", 0));
        sensorContent.add(new Weather("nothing", 0));
        sensorContent.add(new Weather("nothing", 0));
        APIContent = new ArrayList<>();
        APIContent.add(new Weather("nothing", 0));
        APIContent.add(new Weather("nothing", 0));
        APIContent.add(new Weather("nothing", 0));
    }

    private void initScreenBrightness(){
        contentResolver = mainActivity.getContentResolver();
        window = mainActivity.getWindow();
    }

    public boolean onBackPressed(){
        String activeFragment = dataFragment.getActiveFragment();

        switch(activeFragment){
            case "WeatherFragment":
                setFragment("StartFragment");
                break;
        }
        return false;
    }

    public void setFragment(String tag){
        switch(tag){
            case "StartFragment":
                setFragment(startFragment, tag);
            case "WeatherFragment":
                setFragment(weatherFragment, tag);
                break;
        }
    }

    public void setFragment(Fragment fragment, String tag){
        mainActivity.setFragment(fragment,tag);
        dataFragment.setActiveFragment(tag);
    }

    public void sensorInput(String sensorType, float value){
        Weather weather = new Weather(sensorType, value);
        switch(sensorType){

            case "TYPE_AMBIENT_TEMPERATURE":
                sensorContent.set(0, weather);
                break;

            case "TYPE_RELATIVE_HUMIDITY":
                sensorContent.set(1, weather);
                break;

            case "TYPE_PRESSURE":
                sensorContent.set(2, weather);
                break;

            case "TYPE_PROXIMITY":
                break;

            case "TYPE_LIGHT":
                break;
        }
        Log.d("SENSORCONTENT",""+ sensorContent.size());
    }

    public void sendVolleyRequest() {
        //http://api.openweathermap.org/data/2.5/weather?id=2692969&appid=a268f0547f91575628dc6c1121b1b54e
        //Is a call for open weather API with a specific city ID.
        JsonObjectRequest jor = new JsonObjectRequest(Request.Method.GET, malmoe_url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("API:", "VOLLEY");
                handleResponse(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("ERROR", "Error occurred ", error);
            }
        }
        );
        RequestQueue queue = Volley.newRequestQueue(mainActivity);
        queue.add(jor);
    }

    public void sendAsyncRequest(){
        MyAsyncTask asyncTask = new MyAsyncTask();
        asyncTask.execute();
    }

    private void handleResponse(JSONObject response){
        try {
            Log.d("TESTING", "BAJS2");
            JSONObject main_object = response.getJSONObject("main");

            Log.d("AllObjects", response.toString());
            Log.d("MainObject: ", main_object.toString());

            String temperature = main_object.getString("temp");
            Double doubleTemp = Double.parseDouble(temperature) - 273.15;
            Float floatTemp = doubleTemp.floatValue();
            Weather temp = new Weather("TYPE_AMBIENT_TEMPERATURE", floatTemp);

            String humidity = main_object.getString("humidity");
            Weather humi = new Weather("TYPE_RELATIVE_HUMIDITY", Float.parseFloat(humidity));

            String pressure = main_object.getString("pressure");
            Weather press = new Weather("TYPE_PRESSURE", Float.parseFloat(pressure));

            Log.d("Information", "\nTemperature: " + temperature + ", As float: " + floatTemp
                        + "\nHumidity: " + humidity + ", As float: " + Float.parseFloat(humidity) +
                        "\nPressure: " + pressure + ", As float: " + Float.parseFloat(pressure));

            APIContent.set(0, temp);
            APIContent.set(1, humi);
            APIContent.set(2, press);

            mainActivity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    apiFragment.setContent(APIContent);
                }
            });

            Calendar calendar = Calendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat("EEEE-MM-dd");
            String formatted_date = sdf.format(calendar.getTime());
            Log.d("FormattedDate: ", formatted_date);
        } catch(JSONException e){
            e.printStackTrace();
        }
    }

    private void sendContent(){     //Fråga jose om tips
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                mainActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        sensorFragment.setContent(sensorContent);
                    }
                });
            }
        };
        Timer timer = new Timer();
        timer.schedule(timerTask, 1000, 5000);
    }

    public void changeBrightness(String preference){
        if(currentLightValue > 0 && currentLightValue < 100) {
            Log.d("SystemBrightness:","Preference" + " " + preference);
            switch (preference) {
                case "ScreenBrightness":
                    Log.d("SystemBrightness:"," INSIDE CASE: ScreenBrightness");
                    changeScreenBrightness();
                    break;
                case "SystemBrightness":
                    Log.d("SystemBrightness:"," INSIDE CASE: SystemBrightness");
                    changeSystemBrightness();
                    break;
            }
        }
    }

    public void changeScreenBrightness(){
        WindowManager.LayoutParams mLayoutParams = window.getAttributes();
        mLayoutParams.screenBrightness = 1 / currentLightValue;
        window.setAttributes(mLayoutParams);
        Log.d("CHANGESWINDOWBRIGHTNESS", "TO VALUE: " + (1 / currentLightValue));

    }

    private void changeSystemBrightness(){
        Log.d("XXX", "XXX");
        int test = getBrightness();
        float mBrightness = 1 / currentLightValue;
        if(!Settings.System.canWrite(mainActivity)){
            Intent intent = new Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS);
            mainActivity.startActivity(intent);
        } else {
            Settings.System.putInt(
                    contentResolver, Settings.System.SCREEN_BRIGHTNESS, test);
            Log.d("CHANGESSYSTEMBRIGHTNESS", "TO VALUE: " + test);
        }

        WindowManager.LayoutParams mLayoutParams = window.getAttributes();
        mLayoutParams.screenBrightness = mBrightness;
        window.setAttributes(mLayoutParams);

    }

    public void setLightSensorValue(float sensorValue){
        this.currentLightValue = sensorValue;
    }

    private int getBrightness(){
        String preference = brightnessFragment.getActivePreference();
        String mode;
        int lightLow = 0;
        int lightHigh = 255;
        switch(preference){
            case "ScreenBrightness":
                mode = brightnessFragment.getScreenPreference();
                if(mode.equals("LOW")){
                    lightLow = 70;
                    lightHigh = 170;
                }
                else if(mode.equals("LOW-MEDIUM")){
                    lightLow = 90;
                    lightHigh = 190;
                }
                else if(mode.equals("MEDIUM")){
                    lightLow = 110;
                    lightHigh = 220;
                }
                else if(mode.equals("MEDIUM-HIGH")){
                    lightLow = 130;
                    lightHigh = 240;
                }
                else {
                    lightLow = 150;
                }
                float factor1 = currentLightValue / 100;
                int span1 = lightHigh - lightLow;
                float diff1 = factor1 * span1;
                float brightness1 = span1 - diff1;
                Log.d("INFORMATION:", "FACTOR1: " + factor1 + ", SPAN1: " + span1 +
                        ", DIFF1: " + diff1 + ", BRIGHTNESS1: " + brightness1);
                return Math.round(brightness1);

            case "SystemBrightness":
                mode = brightnessFragment.getSystemPreference();
                if(mode.equals("LOW")){
                    lightLow = 70;
                    lightHigh = 170;
                }
                else if(mode.equals("LOW-MEDIUM")){
                    lightLow = 90;
                    lightHigh = 190;
                }
                else if(mode.equals("MEDIUM")){
                    lightLow = 110;
                    lightHigh = 220;
                }
                else if(mode.equals("MEDIUM-HIGH")){
                    lightLow = 130;
                    lightHigh = 240;
                }
                else {
                    lightLow = 150;
                }
                float factor = currentLightValue / 100;
                int span = lightHigh - lightLow;
                float diff = factor * span;
                float brightness = span - diff;
                Log.d("INFORMATION:", "FACTOR: " + factor + ", SPAN: " + span +
                        ", DIFF: " + diff + ", BRIGHTNESS1: " + brightness);
                return Math.round(brightness);
        }
        return -1;
    }

    /*

    public void handleSensorValue(){
        float light = currentLightValue;
        String preference = brightnessFragment.getPreferences();
        setBrightness(light, preference);
    }

    public void setBrightness(float light, String mode){
        int lightLow = 0;
        int lightHigh = 255;
        switch(mode){
            case "LOW":
                lightLow = 150;
                lightHigh = 255;
                break;
            case "LOW-MEDIUM":
                lightLow = 130;
                lightHigh = 240;
                break;
            case "MEDIUM":
                lightLow = 110;
                lightHigh = 220;
                break;
            case "MEDIUM-HIGH":
                lightLow = 90;
                lightHigh = 190;
                break;
            case "HIGH":
                lightLow = 70;
                lightHigh = 170;
                break;
        }
        setPhone(light, lightLow, lightHigh);
    }

    public void setCurrentLightValue(float currentLightValue){
        this.currentLightValue = currentLightValue;
    }

    private void setPhone(float light, int lightLow, int lightHigh){
        float factor = light / 100;
        int span = lightHigh - lightLow;
        float diff = factor * span;
        int newLight =  Math.round(lightHigh - diff);
        setSystemBrightness(newLight);
    }

    private void setSystemBrightness(int newLight){
        wantedSystemBrightness = newLight;
        int currentBrightnessValue = getCurrentBrightnessValue();
        if(currentBrightnessValue > 0){
            if(currentBrightnessValue < wantedSystemBrightness) {
                if (currentBrightnessValue + 10 < wantedSystemBrightness) {
                    currentBrightnessValue += 10;
                } else {
                    currentBrightnessValue = Math.round(wantedSystemBrightness);
                }
            } else {
                if(currentBrightnessValue -10 > wantedSystemBrightness){
                    currentBrightnessValue -= 10;
                } else {
                    currentBrightnessValue = Math.round(wantedSystemBrightness);
                }
            }
        }

        if(!Settings.System.canWrite(mainActivity)){
            Intent i = new Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS);
            mainActivity.startActivity(i);
        } else {
            Settings.System.putInt(contentResolver, Settings.System.SCREEN_BRIGHTNESS, currentBrightnessValue);
        }

    }

    public int getCurrentBrightnessValue(){
        try {
            return android.provider.Settings.System.getInt(contentResolver, Settings.System.SCREEN_BRIGHTNESS);
        } catch(Settings.SettingNotFoundException e){
            e.printStackTrace();
        }
        return -1;
    }

    public void compareLightValues(){
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                handleSensorValue();
            }
        };
        Timer timer = new Timer();
        timer.schedule(timerTask, 0, 500);
    }

    */

    private class MyAsyncTask extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings) {
                try {
                    URL url = new URL(malmoe_url);
                    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                    try {
                      BufferedReader br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                      return br.readLine();
                    } finally {
                        urlConnection.disconnect();
                    }
                } catch(MalformedURLException e){
                    e.printStackTrace();
                } catch(IOException e){
                    e.printStackTrace();
                }
            return "Finished!";
        }


        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                JSONObject response = new JSONObject(s);
                Log.d("API:", "ASYNC");
                handleResponse(response);
            } catch(JSONException e){
                e.printStackTrace();
            }
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
        }
    }

}
