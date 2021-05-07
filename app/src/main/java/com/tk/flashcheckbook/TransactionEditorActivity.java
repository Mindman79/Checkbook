package com.tk.flashcheckbook;

import android.os.Bundle;

import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.tk.flashcheckbook.database.Transaction;
import com.tk.flashcheckbook.viewmodel.MainViewModel;
import com.tk.flashcheckbook.viewmodel.TransactionEditorViewModel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TransactionEditorActivity extends AppCompatActivity {

    @BindView(R.id.transaction_detail)
    TextView tempTransView;

    private TransactionEditorViewModel transViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_editor);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        CollapsingToolbarLayout toolBarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        toolBarLayout.setTitle(getTitle());

        ButterKnife.bind(this);

        initViewModel();


        }

    private void initViewModel() {

        transViewModel = new ViewModelProvider(this).get(TransactionEditorViewModel.class);
        transViewModel.tLiveTransaction.observe(this, new Observer<Transaction>() {
            @Override
            public void onChanged(Transaction transaction) {

                tempTransView.setText(transaction.getNote());

            }
        });


    }


}
