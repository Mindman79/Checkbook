package com.tk.flashcheckbook.ui.settings;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceFragmentCompat;

import com.tk.flashcheckbook.MainActivity;
import com.tk.flashcheckbook.R;
import com.tk.flashcheckbook.ui.account.AccountActivity;

import java.text.ParseException;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);
        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.settings, new SettingsFragment())
                    .commit();
        }
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    public static class SettingsFragment extends PreferenceFragmentCompat {
        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey);
        }
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == android.R.id.home) {

            startActivity(new Intent(this, MainActivity.class));
            overridePendingTransition(0, 0);

        }

        return super.onOptionsItemSelected(item);

    }

}