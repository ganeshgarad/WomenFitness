package com.sardar.softsolstudio.femalehomeworkout.activities;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.view.View;

import androidx.core.view.GravityCompat;
import androidx.appcompat.app.ActionBarDrawerToggle;

import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;
import com.sardar.softsolstudio.femalehomeworkout.R;
import com.sardar.softsolstudio.femalehomeworkout.fragments.HomeFragment;
import com.sardar.softsolstudio.femalehomeworkout.fragments.SetReminder;
import com.sardar.softsolstudio.femalehomeworkout.fragments.privacyPoliceyFragment;
import com.sardar.softsolstudio.femalehomeworkout.utils.SharedPrefManager;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;

import android.view.Menu;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private static MainActivity instance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        instance = this;
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        this.showFragment(savedInstanceState);
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
        } else if (id == R.id.nav_custom) {

        } else if (id == R.id.nav_mealplan) {
            startActivity(new Intent(this, MealPlan.class));
        } else if (id == R.id.nav_guide) {

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
        }

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
