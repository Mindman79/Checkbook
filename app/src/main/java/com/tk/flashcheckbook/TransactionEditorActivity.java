package com.tk.flashcheckbook;

import android.app.DatePickerDialog;
import android.os.Bundle;

import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.tk.flashcheckbook.database.Transaction;
import com.tk.flashcheckbook.util.Formatters;
import com.tk.flashcheckbook.viewmodel.TransactionEditorViewModel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;

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
    private boolean isCleared;

    DatePickerDialog picker;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_editor);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //TODO: Change graphic for back button
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

                String formattedDate = Formatters.dateToString(transaction.getDate());

                transactionAmount.setText(transaction.getAmount().toString());
                transactionNote.setText(transaction.getNote());
                transactionDate.setText(formattedDate);
                transactionNumber.setText(String.valueOf(transaction.getNumber()));
                //transactionDate.setText(String.valueOf(transaction.getDate()));


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



    @OnClick(R.id.transaction_detail_date)
    public void submitClick(View view) {


        //Toast.makeText(this, "Click", Toast.LENGTH_LONG).show();


        final Calendar cldr = Calendar.getInstance();
        int day = cldr.get(Calendar.DAY_OF_MONTH);
        int month = cldr.get(Calendar.MONTH);
        int year = cldr.get(Calendar.YEAR);
        // date picker dialog
        picker = new DatePickerDialog(TransactionEditorActivity.this,
                (view1, year1, monthOfYear, dayOfMonth) -> {

                    transactionDate.setText((monthOfYear + 1) + "/" + dayOfMonth + "/" + year);

                }, year, month, day);
        picker.show();


    }

    @OnCheckedChanged(R.id.transaction_detail_cleared)
    public void checkClearedSwitch(CompoundButton buttonView, boolean isChecked) {


        Toast.makeText(this, "The Switch is " + (isChecked ? "on" : "off"),
                Toast.LENGTH_SHORT).show();
        if(isChecked) {
            //do stuff when Switch is ON
            isCleared = true;
        } else {
            isCleared = false;
        }



    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == android.R.id.home) {

            try {
                saveAndReturn();
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return true;

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {

        try {
            saveAndReturn();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        super.onBackPressed();
    }

    private void saveAndReturn() throws ParseException {


        String amount;
        String note;
        Date date;
        String number;
        Integer cleared;


        if (isCleared == true) {

            cleared = 1;

        } else {

            cleared = 0;

        }
        //checkClearedSwitch(transactionCleared, cleared);


        amount = transactionAmount.getText().toString();
        note = transactionNote.getText().toString();
        date = Formatters.fullDateFormat.parse(transactionDate.getText().toString());
        number = transactionNumber.getText().toString();



        transViewModel.saveTransaction(amount, note, date, number, cleared);
        //transViewModel.saveTransaction(tempTransView.);
        finish();

    }
}
