package dk.vonfrank.ou44locationservice.Views;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import dk.vonfrank.ou44locationservice.R;

/**
 * Created by Von Frank
 */

public class MainFragment extends Fragment {

    private TextView permission_status, ble_status;
    private String[] permissions = {
            Manifest.permission.BLUETOOTH,
            Manifest.permission.BLUETOOTH_ADMIN,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.INTERNET,
            Manifest.permission.ACCESS_NETWORK_STATE};

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        permission_status = (TextView) rootView.findViewById(R.id.permission_status);
        ble_status = (TextView) rootView.findViewById(R.id.ble_status);

        grantPermissions();

        return rootView;
    }

    private void grantPermissions() {
        if(Build.VERSION.SDK_INT >= 23) {
            for(String s : permissions){
                if(getActivity().checkSelfPermission(s) != PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(this.getActivity(),
                            new String[]{s}, 1);
                }
            }
        }

        for(String s : permissions) {
            if(getActivity().checkSelfPermission(s) == PackageManager.PERMISSION_GRANTED){
                permission_status.append(s.substring(19));
                permission_status.append(": ENABLED\n");
            } else {
                permission_status.append(s.substring(19));
                permission_status.append(": DISABLED\n");
            }
        }
    }
}