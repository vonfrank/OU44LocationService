package dk.vonfrank.ou44locationservice.Views;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import dk.vonfrank.ou44locationservice.R;

/**
 * Created by Von Frank on 31-03-2017.
 */

public class IndoorFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_indoor, container, false);

        return rootView;
    }
}
