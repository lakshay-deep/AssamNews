package com.sasuke.demo.assamnews.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.jaeger.library.StatusBarUtil;
import com.sasuke.demo.assamnews.R;
import com.sasuke.demo.assamnews.fragment.ChannelFragment;
import com.sasuke.demo.assamnews.fragment.DevotionalFragment;
import com.sasuke.demo.assamnews.fragment.EntertainmentFragment;
import com.sasuke.demo.assamnews.fragment.HomeFragment;
import com.sasuke.demo.assamnews.fragment.NewsFragment;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    private Toolbar toolbar;
    private TextView tvToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvToolbar = findViewById(R.id.tvToolbar);
        tvToolbar.setText("News Time Assam");


        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        StatusBarUtil.setColor(MainActivity.this, ContextCompat.getColor(this,R.color.statusbar));

        // loading the default fragment
        loadFragment(new HomeFragment());

        //getting bottom navigation and attach to listener
        BottomNavigationView navigationView = findViewById(R.id.bottomm_nav);
        navigationView.setOnNavigationItemSelectedListener(this);
    }



    private boolean loadFragment(Fragment fragment){
        // switching fragment
        if(fragment != null){
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragmentContainer, fragment)
                    .commit();
            return true;
        }
        return false;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        Fragment fragment = null;

        switch (menuItem.getItemId()){
            case R.id.home:
                fragment = new HomeFragment();
                tvToolbar.setText("News Time Assam");
                break;
            case R.id.news:
                fragment = new NewsFragment();
                tvToolbar.setText("News");
                break;
//            case R.id.channel:
//                fragment = new ChannelFragment();
//                tvToolbar.setText("Channels");
//                break;
            case R.id.entertainment:
                fragment = new EntertainmentFragment();
                tvToolbar.setText("Entertainment");
                break;
            case R.id.devotional:
                fragment = new DevotionalFragment();
                tvToolbar.setText("Devotional");
                break;
            case R.id.channel:
                fragment = new ChannelFragment();
                tvToolbar.setText("Temp");
                break;
        }
        return loadFragment(fragment);
    }

    boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }
}
