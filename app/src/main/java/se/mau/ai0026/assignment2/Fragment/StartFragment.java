package se.mau.ai0026.assignment2.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import se.mau.ai0026.assignment2.Main.Controller;
import se.mau.ai0026.assignment2.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class StartFragment extends Fragment {
    private Controller controller;
    private Button button;


    public StartFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_start, container, false);
        initComponents(view);
        return view;
    }

    public void setController(Controller controller){
        this.controller = controller;
    }

    private void initComponents(View view){
        button = (Button) view.findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //controller.checkValues();
                //controller.sendApiRequest();
            }
        });
    }

}
