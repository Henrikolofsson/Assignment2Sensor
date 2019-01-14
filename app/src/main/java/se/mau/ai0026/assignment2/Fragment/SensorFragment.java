package se.mau.ai0026.assignment2.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import se.mau.ai0026.assignment2.Entities.Weather;
import se.mau.ai0026.assignment2.Adapters.WeatherRVAdapter;
import se.mau.ai0026.assignment2.Main.Controller;
import se.mau.ai0026.assignment2.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class SensorFragment extends Fragment {
    private final static String TAG = "Sensor";
    private Controller controller;
    private RecyclerView recyclerView;
    private WeatherRVAdapter adapter;
    private List<Weather> content = new ArrayList<>();

    public SensorFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sensor, container, false);
        initComponents(view);
        return view;
    }

    private void initComponents(View view) {
        adapter = new WeatherRVAdapter(getActivity(), content);
        recyclerView = (RecyclerView) view.findViewById(R.id.rv_sensor);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    public void setController(Controller controller){
        this.controller = controller;
    }

    public void setContent(List<Weather> content){
        this.content = content;
        adapter.setContent(content);
        adapter.notifyDataSetChanged();

        for(int i = 0; i < content.size(); i++){
            Log.d("SENSORCONTENT", content.get(i).toString());
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if(content!=null){
            adapter.setContent(content);
        }
    }
}
