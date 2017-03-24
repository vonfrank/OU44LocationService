package dk.vonfrank.ou44locationservice.Activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import dk.vonfrank.ou44locationservice.R;
import dk.vonfrank.ou44locationservice.Views.MainFragment;
import dk.vonfrank.ou44locationservice.Views.MapFragment;

/**
 * Created by Von Frank
 */

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawerLayout;
    private MainFragment mf;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        //Handle navigation view item clicks here!

        int id = item.getItemId();

        if(id == R.id.nav_main) {
            MainFragment mainFragment = new MainFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.content_fragment_container, mainFragment).commit();
        }

        if(id == R.id.nav_map) {
            MapFragment mapFragment = new MapFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.content_fragment_container, mapFragment).commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
