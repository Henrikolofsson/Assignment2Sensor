package se.mau.ai0026.assignment2.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import se.mau.ai0026.assignment2.Main.Controller;
import se.mau.ai0026.assignment2.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class BrightnessFragment extends Fragment {
    private Controller controller;
    private SeekBar sbScreenBrightness;
    private SeekBar sbSystemBrightness;
    private static String screenPref;
    private static String systemPref;
    private static String activePref;

    private RadioGroup rgBrightness;
    private RadioButton rbScreenBrigthness;
    private RadioButton rbSystemBrightness;

    private TextView tvScreenPreference;
    private TextView tvSystemPreference;
    private TextView tvSystemBrightness;
    private TextView tvWindowBrightness;
    private TextView tvLightValue;


    public BrightnessFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_brightness, container, false);
        initializeComponents(view);
        registerListeners();
        initSettings();

        return view;
    }

    private void initializeComponents(View view) {
        sbScreenBrightness = (SeekBar) view.findViewById(R.id.sbScreenBrightness);
        sbSystemBrightness = (SeekBar) view.findViewById(R.id.sbSystemBrightness);
        rgBrightness = (RadioGroup) view.findViewById(R.id.rgBrightness);
        rbScreenBrigthness = (RadioButton) view.findViewById(R.id.rbScreenBrightness);
        rbSystemBrightness = (RadioButton) view.findViewById(R.id.rbSystemBrightness);
        tvScreenPreference = (TextView) view.findViewById(R.id.tvScreenPreference);
        tvSystemPreference = (TextView) view.findViewById(R.id.tvSystemPreference);
        tvSystemBrightness = (TextView) view.findViewById(R.id.tvSystemBrightness2);
        tvWindowBrightness = (TextView) view.findViewById(R.id.tvWindowBrightness);
        tvLightValue = (TextView) view.findViewById(R.id.tvLightValue);
    }

    private void initSettings(){
        activePref = "ScreenBrightness";
        sbScreenBrightness.setProgress(50);
        sbSystemBrightness.setProgress(50);
        setScreenPreference(50);
        rbScreenBrigthness.setChecked(true);
    }

    public void setController(Controller controller){
        this.controller = controller;
    }

    public void setScreenPreference(int preference){
        if(preference <= 20){
            screenPref = "LOW";
        }
        else if(preference >= 21 && preference <= 40){
            screenPref = "LOW-MEDIUM";
        }
        else if(preference >= 41 && preference <= 60){
            screenPref = "MEDIUM";
        }
        else if(preference >= 61 && preference <= 80){
            screenPref = "MEDIUM-HIGH";
        } else {
            screenPref = "HIGH";
        }
        tvScreenPreference.setText(getString(R.string.yourpreference) + " " + screenPref);
    }

    public void setSystemPreference(int preference){
        if(preference <= 20){
            systemPref = "LOW";
        }
        else if(preference >= 21 && preference <= 40){
            systemPref = "LOW-MEDIUM";
        }
        else if(preference >= 41 && preference <= 60){
            systemPref = "MEDIUM";
        }
        else if(preference >= 61 && preference <= 80){
            systemPref = "MEDIUM-HIGH";
        } else {
            systemPref = "HIGH";
        }
        tvSystemPreference.setText(getString(R.string.yourpreference) + " " + systemPref);
    }

    public String getSystemPreference(){
        return systemPref;
    }

    public String getScreenPreference(){
        return screenPref;
    }

    public String getActivePreference(){
        return activePref;
    }

    public void setTvSystemBrightness(int valueTimes255){
        tvSystemBrightness.setText("The system brightness is: " + Integer.toString(valueTimes255));
    }

    public void setTvWindowBrightness(float invertedValue){
        tvWindowBrightness.setText("The window brightness is getting value: " + invertedValue);
    }

    public void setTvLightValue(float lightValue){
        tvLightValue.setText("The sensor is giving value: " + Float.toString(lightValue));
    }

    private void registerListeners(){
        sbScreenBrightness.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                setScreenPreference(progress);
                Log.d("ActivePreference:", activePref);
                if(activePref.equals("ScreenBrightness")){
                    controller.changeBrightness(activePref);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        sbSystemBrightness.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                setSystemPreference(progress);
                Log.d("ActivePreference:", activePref);
                if(activePref.equals("ScreenBrightness")){
                    Log.d("SystemBrightness:", "xxx");
                    controller.changeBrightness(activePref);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        rgBrightness.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch(checkedId){
                    case R.id.rbScreenBrightness:
                        activePref = "ScreenBrightness";
                        break;
                    case R.id.rbSystemBrightness:
                        activePref = "SystemBrightness";
                        break;
                }
            }
        });

    }

}
