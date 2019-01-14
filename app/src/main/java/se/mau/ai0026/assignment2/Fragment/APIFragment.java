package se.mau.ai0026.assignment2.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import se.mau.ai0026.assignment2.Adapters.WeatherRVAdapter;
import se.mau.ai0026.assignment2.Entities.Weather;
import se.mau.ai0026.assignment2.Main.Controller;
import se.mau.ai0026.assignment2.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class APIFragment extends Fragment {
    private final static String TAG = "Sensor";
    private Controller controller;
    private RadioGroup radioGroup;
    private RadioButton rbVolley;
    private RadioButton rbAsync;
    private Button btnGetResults;
    private RecyclerView rvApi;
    private WeatherRVAdapter adapter;
    private List<Weather> content = new ArrayList<>();


    public APIFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_api, container, false);
        initComponents(view);
        registerListeners();
        return view;
    }

    private void initComponents(View view) {
        radioGroup = (RadioGroup) view.findViewById(R.id.rgApi);
        rbVolley = (RadioButton) view.findViewById(R.id.rbVolley);
        rbAsync = (RadioButton) view.findViewById(R.id.rbAsync);
        btnGetResults = (Button) view.findViewById(R.id.btnGetApiResult);
        adapter = new WeatherRVAdapter(getActivity(), content);
        rvApi = (RecyclerView) view.findViewById(R.id.rvApi);
        rvApi.setAdapter(adapter);
        rvApi.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    public void setController(Controller controller){
        this.controller = controller;
    }

    private void registerListeners(){
        btnGetResults.setOnClickListener(new ButtonGetResultsListener());
    }

    public void setContent(List<Weather> content){
        this.content = content;
        adapter.setContent(content);
        adapter.notifyDataSetChanged();

        for(int i = 0; i < content.size(); i++){
            Log.d("SENSORCONTENTAPIFRAG", content.get(i).toString());
        }
    }

    private class ButtonGetResultsListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            Log.d("RADIOBUTTON", ""+radioGroup.getCheckedRadioButtonId());
            switch(radioGroup.getCheckedRadioButtonId()){
                case R.id.rbVolley:
                    controller.sendVolleyRequest();
                    break;
                case R.id.rbAsync:
                    Log.d("TRUE", "TRUE");
                    controller.sendAsyncRequest();
                    break;
            }

        }
    }

}
