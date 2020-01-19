package com.example.pruebadeserializerjsondetemblorescongson.activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.pruebadeserializerjsondetemblorescongson.R;

import es.dmoral.toasty.Toasty;

import static com.example.pruebadeserializerjsondetemblorescongson.utils.Utils.getMaxResFromSharedPrefs;
import static com.example.pruebadeserializerjsondetemblorescongson.utils.Utils.getMinMagFromSharedPrefs;
import static com.example.pruebadeserializerjsondetemblorescongson.utils.Utils.getValueFromSharedPrefs;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        setToolbar();
    }


    private void setToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //Añade el icono de menu lateral
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);
        //Muestra el icono
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //Titulo del toolbar
        getSupportActionBar().setTitle(R.string.settings_activity_title);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public static class EarthquakePreferenceFragment extends PreferenceFragment {

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setHasOptionsMenu(true);
            addPreferencesFromResource(R.xml.settings_main);
            setSummaries();
            updateSettings();
        }

        private void setSummary(int key, SharedPreferences sharedPreferences, Context context) {
            getPreferenceManager().findPreference(getString(key))
                    .setSummary(getValueFromSharedPrefs(context, sharedPreferences, key));
        }

        private void setSummaries() {
            setSummary(R.string.key_max_res, getPreferenceManager().getSharedPreferences(), getPreferenceManager().findPreference(getString(R.string.key_max_res)).getContext());
            setSummary(R.string.key_min_mag, getPreferenceManager().getSharedPreferences(), getPreferenceManager().findPreference(getString(R.string.key_min_mag)).getContext());
        }

        private void resetSettings() {
            getPreferenceManager().getSharedPreferences().edit()
                    .putString(getString(R.string.key_max_res), getString(R.string.def_max_res))
                    .putString(getString(R.string.key_min_mag), getString(R.string.def_min_mag))
                    .putString(getString(R.string.key_order_by), getString(R.string.def_order_by))
                    .apply();
            setSummaries();
        }

        private void updateSettings() {
            getPreferenceManager().findPreference(getString(R.string.key_max_res)).setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                @Override
                public boolean onPreferenceChange(Preference preference, Object newValue) {
                    try {
                        if (Long.parseLong(newValue.toString()) > 199999) {
                            getPreferenceManager().getSharedPreferences().edit()
                                    .putString(preference.getKey(), getMaxResFromSharedPrefs(preference.getContext(), PreferenceManager.getDefaultSharedPreferences(preference.getContext())))
                                    .apply();
                            Toasty.error(preference.getContext(), R.string.dialog_max_results_toast, Toasty.LENGTH_SHORT, true).show();
                            return false;
                        } else {
                            preference.setSummary(newValue.toString());
                            return true;
                        }
                    } catch (java.lang.NumberFormatException e) {
                        Toasty.error(preference.getContext(), R.string.dialog_max_results_toast_fail, Toasty.LENGTH_SHORT, true).show();
                        return false;
                    }

                }
            });

            getPreferenceManager().findPreference(getString(R.string.key_min_mag)).setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                @Override
                public boolean onPreferenceChange(Preference preference, Object newValue) {
                    if (Double.parseDouble(newValue.toString()) > 10.0) {
                        getPreferenceManager().getSharedPreferences().edit()
                                .putString(preference.getKey(), getMinMagFromSharedPrefs(preference.getContext(), PreferenceManager.getDefaultSharedPreferences(preference.getContext())))
                                .apply();
                        Toasty.error(preference.getContext(), R.string.dialog_min_mag_msg, Toasty.LENGTH_SHORT, true).show();
                        return false;
                    } else {
                        preference.setSummary(newValue.toString());
                        return true;
                    }
                }
            });
        }

        @Override
        public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
            inflater.inflate(R.menu.menu_reset_settings, menu);
            super.onCreateOptionsMenu(menu, inflater);
        }

        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            switch (item.getItemId()) {
                case R.id.menu_reset_settings:
                    resetSettings();
                    break;
            }
            return super.onOptionsItemSelected(item);
        }

        @Override
        public void onDetach() {
            super.onDetach();
            getPreferenceManager().findPreference(getString(R.string.key_min_mag)).setOnPreferenceChangeListener(null);
            getPreferenceManager().findPreference(getString(R.string.key_max_res)).setOnPreferenceChangeListener(null);
        }
    }
}
