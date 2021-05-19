package com.tk.flashcheckbook;

import android.os.Bundle;

import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.tk.flashcheckbook.database.Transaction;
import com.tk.flashcheckbook.viewmodel.TransactionEditorViewModel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.MenuItem;
import android.widget.Switch;
import android.widget.TextView;

import java.math.BigDecimal;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.tk.flashcheckbook.util.Constants.TRANSACTION_ID_KEY;

public class TransactionEditorActivity extends AppCompatActivity {

    @BindView(R.id.transaction_detail_note)
    TextView transactionNote;

    @BindView(R.id.transaction_detail_amount)
    TextView transactionAmount;

    @BindView(R.id.transaction_detail_date)
    TextView transactionDate;

    @BindView(R.id.transaction_detail_number)
    TextView transactionNumber;

    @BindView(R.id.transaction_detail_payee)
    TextView transactionPayee;

    @BindView(R.id.transaction_detail_category)
    TextView transactionCategory;

    @BindView(R.id.transaction_detail_cleared)
    Switch transactionCleared;

    private TransactionEditorViewModel transViewModel;
    private boolean tNewTrans;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_editor);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Change graphic for back button
        //getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu_gallery);
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


                transactionAmount.setText(transaction.getAmount().toString());
                transactionNote.setText(transaction.getNote());


            }
        });


        Bundle extras = getIntent().getExtras();
        if (extras == null) {

            setTitle(getString(R.string.new_transaction));
            tNewTrans = true;

        } else {

            setTitle(getString(R.string.edit_transaction));
            int transId = extras.getInt(TRANSACTION_ID_KEY);
            transViewModel.loadData(transId);

        }



    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == android.R.id.home) {

            saveAndReturn();
            return true;

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {

        saveAndReturn();

        super.onBackPressed();
    }

    private void saveAndReturn() {


        String amount;
        String note;





        amount = transactionAmount.getText().toString();
        note = transactionNote.getText().toString();


        transViewModel.saveTransaction(amount, note);
        //transViewModel.saveTransaction(tempTransView.);
        finish();

    }
}
