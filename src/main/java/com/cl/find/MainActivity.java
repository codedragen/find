package com.cl.find;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    private BottomNavigationView bottomNavigation;
    private LocationFragment locationFragment;
    private HospitalFragment hospitalFragment;
    private AdviceFragment adviceFragment;
    private HealthBrainFragment healthBrainFragment;
    private ProtectionFragment protectionFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        bottomNavigation= (BottomNavigationView) findViewById(R.id.activity_main_bottomnavigation);
        bottomNavigation.setOnNavigationItemSelectedListener(this);
        if (savedInstanceState==null){
           locationFragment= LocationFragment.getInstance();
            hospitalFragment= HospitalFragment.getInstance();
            adviceFragment = AdviceFragment.getInstance();
            healthBrainFragment=HealthBrainFragment.getInstance();
            protectionFragment=ProtectionFragment.getInstance();


        }else{
            locationFragment= (LocationFragment) getSupportFragmentManager().findFragmentByTag("location");
            hospitalFragment= (HospitalFragment) getSupportFragmentManager().findFragmentByTag("hospital");
            adviceFragment= (AdviceFragment) getSupportFragmentManager().findFragmentByTag("personal");

        }
        getSupportFragmentManager().beginTransaction()
                .add(R.id.activity_main_container,locationFragment,"location")
                .add(R.id.activity_main_container,hospitalFragment,"hospital")
                .add(R.id.activity_main_container,adviceFragment,"advice")
                .add(R.id.activity_main_container,protectionFragment,"protection")
                .add(R.id.activity_main_container,healthBrainFragment,"healthbrain")
                .hide(adviceFragment)
                .hide(hospitalFragment)
                .hide(protectionFragment)
                .hide(healthBrainFragment)
                .show(locationFragment).commit();


    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.location:
                switchFragment(locationFragment);
                break;
            case R.id.hospital:
                switchFragment(hospitalFragment);
                break;
            case R.id.protection:
               switchFragment(protectionFragment);
                break;
            case R.id.advice:
                switchFragment(adviceFragment);
                break;
            case R.id.healthbrain:
                switchFragment(healthBrainFragment);
                break;

        }
        return true;
    }


    private void switchFragment(Fragment fragment){
        getSupportFragmentManager().beginTransaction()
                .hide(adviceFragment)
                .hide(hospitalFragment)
                .hide(locationFragment)
                .hide(protectionFragment)
                .hide(healthBrainFragment)
                .commit();
        getSupportFragmentManager().beginTransaction().show(fragment).commit();

    }
}
