package com.example.triviagame;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Color;
import android.media.VolumeShaper;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.ListPreference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.telecom.Conference;

import androidx.annotation.Nullable;

import java.util.Locale;

public class Preference extends PreferenceActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
        loadSettings();
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(Preference.this, MenuActivity.class));
        finish();
    }

    private void loadSettings() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        // NIGHT MODE
        boolean checkNightMode = sharedPreferences.getBoolean("NIGHT", false);
        if (checkNightMode) {
            getListView().setBackgroundColor(Color.parseColor("#222222"));
        } else {
            getListView().setBackgroundColor(Color.parseColor("#ffffff"));
        }
        CheckBoxPreference cnk_night_instance = (CheckBoxPreference) findPreference("NIGHT");
        cnk_night_instance.setOnPreferenceChangeListener(new android.preference.Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(android.preference.Preference preference, Object o) {
                boolean yes = (Boolean) o;
                if (yes) {
                    getListView().setBackgroundColor(Color.parseColor("#222222"));

                } else {
                    getListView().setBackgroundColor(Color.parseColor("#ffffff"));
                }
                return true;
            }
        });

        // ORIENTATION
        ListPreference lpOrientation = (ListPreference) findPreference("ORIENTATION");
        String orientation = sharedPreferences.getString("ORIENTATION", "false");
        if ("1".equals(orientation)) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_BEHIND);
            lpOrientation.setSummary(lpOrientation.getEntry());
        } else if ("2".equals(orientation)) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
            lpOrientation.setSummary(lpOrientation.getEntry());
        } else if ("3".equals(orientation)) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            lpOrientation.setSummary(lpOrientation.getEntry());
        }

        lpOrientation.setOnPreferenceChangeListener(new android.preference.Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(android.preference.Preference preference, Object o) {
                String items = (String) o;
                if (preference.getKey().equals("ORIENTATION")) {
                    switch (items) {
                        case "1":
                            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_BEHIND);
                            break;
                        case "2":
                            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
                            break;
                        case "3":
                            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                            break;
                    }
                    ListPreference lppOrientation = (ListPreference) preference;
                    lppOrientation.setSummary(lppOrientation.getEntries()[lppOrientation.findIndexOfValue(items)]);
                }
                return true;
            }
        });
    }


    @Override
    protected void onResume() {
        loadSettings();
        super.onResume();
    }
}