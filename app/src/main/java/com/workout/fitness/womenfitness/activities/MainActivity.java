package com.workout.fitness.womenfitness.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.core.view.GravityCompat;
import androidx.appcompat.app.ActionBarDrawerToggle;

import android.util.Log;
import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;
import com.workout.fitness.womenfitness.R;
import com.workout.fitness.womenfitness.fragments.HomeFragment;
import com.workout.fitness.womenfitness.fragments.SetReminder;
import com.workout.fitness.womenfitness.fragments.WorkoutGuideFragment;
import com.workout.fitness.womenfitness.fragments.privacyPoliceyFragment;
import com.workout.fitness.womenfitness.utils.AlarmReceiver;
import com.workout.fitness.womenfitness.utils.NotificationScheduler;
import com.workout.fitness.womenfitness.utils.SharedPrefManager;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private static MainActivity instance;
    SharedPreferences sharedpreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        sharedpreferences = getSharedPreferences("LocalReminder", Context.MODE_PRIVATE);
        instance = this;
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        this.showFragment(savedInstanceState);

        //This will Set Reminder For Re-open App after 3 Days
        if(!sharedpreferences.getBoolean("isSet",false)) {
            NotificationScheduler.setDayReminder(this, AlarmReceiver.class);
            Log.d("ALarm Set Here","Going to set Alarm");
        }
    }

    private void showFragment(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.main_frame, new HomeFragment(), null)
                    .commit();
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            // Handle the camera action
            getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.main_frame, new HomeFragment(), null)
                    .commit();
        }  else if (id == R.id.nav_mealplan) {
            startActivity(new Intent(this, MealPlan.class));
        } else if (id == R.id.nav_guide) {
            getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.main_frame, new WorkoutGuideFragment(), null).addToBackStack("routine")
                    .commit();
        } else if (id == R.id.nav_reports) {
            startActivity(new Intent(this, ReportsBMIactivity.class));
        } else if (id == R.id.nav_history) {
            startActivity(new Intent(this, HistoryCalenderFragment.class));
        } else if (id == R.id.nav_reminder) {
            getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.main_frame, new SetReminder(), null).addToBackStack("routine")
                    .commit();

        } else if (id == R.id.nav_setting) {
            startActivity(new Intent(MainActivity.this, SettingsActivity.class));

        } else if (id == R.id.nav_restart) {
            SharedPrefManager.getInstance(this).logOut();
        }/*else if (id == R.id.nav_rate_us) {
            final String appPackageName = getPackageName(); // getPackageName() from Context or Activity object
            try {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
            } catch (android.content.ActivityNotFoundException anfe) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
            }
        }*//*else if (id == R.id.nav_privacy) {
            getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.main_frame, new privacyPoliceyFragment(), null).addToBackStack("routine")
                    .commit();

        }*/

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void StartReminderFragment() {
        //getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main_frame, new SetReminder(), null).addToBackStack("routine")
                .commitAllowingStateLoss();
        ;
    }

    public void StartprivacyFragment() {
        //getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main_frame, new privacyPoliceyFragment(), null).addToBackStack("routine")
                .commitAllowingStateLoss();
        ;
    }

    public static MainActivity getInstance() {
        return instance;
    }
}
