package com.example.donelistapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.donelistapp.model.SharedPrefManager;

import java.util.Timer;
import java.util.TimerTask;

public class HomePageActivity extends AppCompatActivity {

    Context context;
    SharedPrefManager sharedPrefManager;
    Timer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        context = HomePageActivity.this;
        sharedPrefManager = new SharedPrefManager(context);
        getSupportFragmentManager().beginTransaction()
                .setReorderingAllowed(true)
                .add(R.id.hostHomePageFragment, HomePageFragment.class, null)
                .commit();
    }

    @Override
    protected void onPause() {
        super.onPause();

        timer = new Timer();
        LogOutTimerTask logoutTimeTask = new LogOutTimerTask();
        timer.schedule(logoutTimeTask, 300000);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

    private class LogOutTimerTask extends TimerTask {
        @Override
        public void run() {
            sharedPrefManager.saveSPBoolean(SharedPrefManager.SP_IS_ACTIVE, false);
            sharedPrefManager.clearAll();
            startActivity(new Intent(HomePageActivity.this, LandingPageActivity.class)
                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
            finish();
        }
    }
}