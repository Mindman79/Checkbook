package com.tk.flashcheckbook.ui.account;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.tk.flashcheckbook.R;
import com.tk.flashcheckbook.ui.settings.SettingsActivity;
import com.tk.flashcheckbook.ui.settings.SettingsFragment;

public class AccountFragment extends Fragment {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        Intent intent = new Intent(AccountFragment.this.getActivity(), AccountActivity.class);
        startActivity(intent);




    }


}