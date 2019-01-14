package se.mau.ai0026.assignment2.Fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class DataFragment extends Fragment {
    private String activeFragment;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    public void setActiveFragment(String activeFragment){
        this.activeFragment = activeFragment;
    }

    public String getActiveFragment(){
        return activeFragment;
    }
}
