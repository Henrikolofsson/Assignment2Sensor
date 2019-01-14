package se.mau.ai0026.assignment2.Fragment;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import se.mau.ai0026.assignment2.Adapters.SensorApiViewPageAdapter;
import se.mau.ai0026.assignment2.Main.Controller;
import se.mau.ai0026.assignment2.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class WeatherFragment extends Fragment {
    private Controller controller;
    private SensorApiViewPageAdapter viewPageAdapter;
    private ViewPager viewPager;
    private SensorFragment sensorFragment;
    private APIFragment apiFragment;

    public WeatherFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_weather, container, false);
        initComponents(view);
        setupViewPageAdapter(viewPager);
        initTabLayouts(view);
        return view;
    }

    private void setupViewPageAdapter(ViewPager viewPager) {
        viewPageAdapter = new SensorApiViewPageAdapter(getFragmentManager());
        viewPageAdapter.addFragment(sensorFragment, "Sensor");
        viewPageAdapter.addFragment(apiFragment, "API");
        viewPager.setAdapter(viewPageAdapter);
    }

    public void setController(Controller controller){
        this.controller = controller;
    }

    private void initComponents(View view) {
        viewPageAdapter = new SensorApiViewPageAdapter(getFragmentManager());
        viewPager = (ViewPager) view.findViewById(R.id.vp_weather);
    }

    private void initTabLayouts(View view){
        TabLayout tabLayout = (TabLayout) view.findViewById(R.id.tablayoutWeather);
        tabLayout.setupWithViewPager(viewPager);
    }

    public void setSensorFragment(SensorFragment fragment){
        this.sensorFragment = fragment;
    }

    public void setApiFragment(APIFragment fragment){
        this.apiFragment = fragment;
    }
}
