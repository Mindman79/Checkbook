package com.tk.flashcheckbook.ui.account;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.badge.BadgeDrawable;
import com.tk.flashcheckbook.R;
import com.tk.flashcheckbook.TransactionEditorActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FirstTimeSetup extends AppCompatActivity {

    @BindView(R.id.account_add_button)
    Button addNewAccountButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_time_setup);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.account_add_button)
    public void submit(View view) {

        Intent intent = new Intent(this, AccountEditorActivity.class);
        startActivity(intent);
    }

}