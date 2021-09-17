package com.tk.flashcheckbook.ui.payee;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

public class PayeeFragment extends Fragment {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        Intent intent = new Intent(PayeeFragment.this.getActivity(), PayeeActivity.class);
        startActivity(intent);




    }


}