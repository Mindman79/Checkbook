package com.tk.flashcheckbook.ui.settings;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

public class SettingsFragment extends Fragment {



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


                Intent intent = new Intent(SettingsFragment.this.getActivity(), SettingsActivity.class);
                startActivity(intent);




            }

}


