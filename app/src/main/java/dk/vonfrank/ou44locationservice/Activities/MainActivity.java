package dk.vonfrank.ou44locationservice.Activities;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.vision.text.Text;
import com.kontakt.sdk.android.ble.connection.OnServiceReadyListener;
import com.kontakt.sdk.android.ble.manager.ProximityManager;
import com.kontakt.sdk.android.ble.manager.ProximityManagerFactory;
import com.kontakt.sdk.android.ble.manager.listeners.IBeaconListener;
import com.kontakt.sdk.android.ble.manager.listeners.simple.SimpleIBeaconListener;
import com.kontakt.sdk.android.common.KontaktSDK;
import com.kontakt.sdk.android.common.profile.IBeaconDevice;
import com.kontakt.sdk.android.common.profile.IBeaconRegion;

import java.io.IOException;

import dk.vonfrank.ou44locationservice.Models.BeaconItem;
import dk.vonfrank.ou44locationservice.R;
import dk.vonfrank.ou44locationservice.Util.AppData;
import dk.vonfrank.ou44locationservice.Util.JSONReader;
import dk.vonfrank.ou44locationservice.Views.IndoorFragment;
import dk.vonfrank.ou44locationservice.Views.MainFragment;
import dk.vonfrank.ou44locationservice.Views.MapFragment;

/**
 * Created by Von Frank
 */

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawerLayout;
    private MainFragment mf;
    private NavigationView navigationView;
    private ProximityManager proximityManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Initialize data in async task
        new InitializeDataTask().execute("beacons.json");

        // Toolbar must be initialized, in order to create hamburger menu
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        // Initialize drawer layout, and add hamburger menu to toolbar
        drawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        // Add main fragment, to the content_fragment_container. Main fragment is the default fragment
        if(savedInstanceState == null){
            mf = new MainFragment();
            getSupportFragmentManager().beginTransaction().add(R.id.content_fragment_container, mf).commit();
        }

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        KontaktSDK.initialize("McwBgjPJCXdpdbxatFqQhtWPiPDFNrnf");

        proximityManager = ProximityManagerFactory.create(this);
        proximityManager.setIBeaconListener(createIBeaconListener());
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        //Handle navigation view item clicks here!

        int id = item.getItemId();

        if(id == R.id.nav_main) {
            MainFragment mainFragment = new MainFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.content_fragment_container, mainFragment).commit();
        }

        if(id == R.id.nav_position) {
            MapFragment mapFragment = new MapFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.content_fragment_container, mapFragment).commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public class InitializeDataTask extends AsyncTask<String, Void, Boolean> {
        protected Boolean doInBackground(String... filenames) {
            try {
                for(String filename : filenames){
                    AppData.getInstance().setBeaconItems((JSONReader.ConvertJSONToBeaconItems(JSONReader.loadJSONFromFile(getResources().getAssets().open(filename)))));
                }
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }

            return true;
        }

        protected void onPostExecute(Boolean results) {
            Context context = getApplicationContext();
            int duration = Toast.LENGTH_LONG;

            Toast toast = Toast.makeText(context, R.string.initialize_data, duration);
            toast.show();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        startScanning();
    }

    @Override
    protected void onStop() {
        proximityManager.stopScanning();
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        proximityManager.disconnect();
        proximityManager = null;
        super.onDestroy();
    }

    private void startScanning() {
        proximityManager.connect(new OnServiceReadyListener() {
            @Override
            public void onServiceReady() {
                proximityManager.startScanning();
                Context context = getApplicationContext();
                int duration = Toast.LENGTH_LONG;

                Toast toast = Toast.makeText(context, R.string.scanner_start, duration);
                toast.show();
            }
        });
    }

    private IBeaconListener createIBeaconListener() {
        return new SimpleIBeaconListener() {
            //TODO implement this nicer!

            TextView room_content;

            @Override
            public void onIBeaconLost(IBeaconDevice ibeacon, IBeaconRegion region) {
                super.onIBeaconLost(ibeacon, region);
                AppData.getInstance().removeIBeaconDevice(ibeacon);
                if(AppData.getInstance().getiBeaconDevices().isEmpty()){
                    MapFragment mapFragment = new MapFragment();
                    getSupportFragmentManager().beginTransaction().replace(R.id.content_fragment_container, mapFragment).commit();
                }
            }

            @Override
            public void onIBeaconDiscovered(IBeaconDevice ibeacon, IBeaconRegion region) {
                Context context = getApplicationContext();
                int duration = Toast.LENGTH_LONG;

                room_content = (TextView) findViewById(R.id.room_content);

                if(!AppData.getInstance().checkForDevice(ibeacon)){
                    AppData.getInstance().addIBeaconDevice(ibeacon);
                }

                if(!AppData.getInstance().getiBeaconDevices().isEmpty()){
                    BeaconItem bi = AppData.getInstance().getBeaconData(AppData.getInstance().getIBeaconDeviceWithHighestRSSI());
                    if(bi != null){
                        IndoorFragment indoorFragment = new IndoorFragment();
                        if(room_content != null){
                            room_content.setText(bi.getRoom() + ", " + bi.getRoomName() + " at level " + bi.getLevel());
                        }
                        getSupportFragmentManager().beginTransaction().replace(R.id.content_fragment_container, indoorFragment).commit();
                    }
                }
            }
        };
    }
}
