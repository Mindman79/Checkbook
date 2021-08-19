package com.tk.flashcheckbook;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.tk.flashcheckbook.database.Category;
import com.tk.flashcheckbook.util.Formatters;
import com.tk.flashcheckbook.util.Globals;
import com.tk.flashcheckbook.util.MyProperties;
import com.tk.flashcheckbook.viewmodel.TransactionEditorViewModel;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import butterknife.OnTouch;

import static com.tk.flashcheckbook.util.Constants.CATEGORY_ID_KEY;
import static com.tk.flashcheckbook.util.Constants.EDITING_KEY;
import static com.tk.flashcheckbook.util.Constants.PAYEE_ID_KEY;
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
    AutoCompleteTextView transactionPayee;

    @BindView(R.id.transaction_detail_category)
    AutoCompleteTextView transactionCategory;

    @BindView(R.id.transaction_detail_cleared)
    Switch transactionCleared;

    private TransactionEditorViewModel transViewModel;
    private boolean tNewTrans;
    private boolean isCleared;
    private boolean isEditing;

    DatePickerDialog picker;

    Globals sharedData = Globals.getInstance();

    private int accountId = sharedData.getAccountId();


    //@RequiresApi(api = Build.VERSION_CODES.O)
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

        //Handling device orientation switches
        if (savedInstanceState != null) {

            isEditing = savedInstanceState.getBoolean(EDITING_KEY);

        }


        initViewModel();

    }


    //@RequiresApi(api = Build.VERSION_CODES.O)
    private void initViewModel() {

        transViewModel = new ViewModelProvider(this).get(TransactionEditorViewModel.class);

        transViewModel.livePayee.observe(this, payee -> {


            if (payee != null && isEditing == true) {

                transactionPayee.setText(transactionPayee.getText());

            } else {

                transactionPayee.setText(payee.getName());

            }


        });


        transViewModel.liveCategory.observe(this, category -> {


            if (category != null && isEditing == true) {

                transactionCategory.setText(transactionCategory.getText());

            } else {

                transactionCategory.setText(category.getName());

            }

        });


        //TODO: Figure out how to do drop down selections when existing payees are present in landscape mode

        transViewModel.liveTransaction.observe(this, transaction -> {

            String formattedDate = Formatters.dateToString(transaction.getDate());

            boolean switchCleared;

            if (transaction.getCleared() == 1) {

                switchCleared = true;


            } else {

                switchCleared = false;
            }

            if (transaction != null && isEditing == true) {


                transactionAmount.setText(transactionAmount.getText());
                transactionNote.setText(transactionNote.getText());
                transactionDate.setText(transactionDate.getText());
                transactionNumber.setText(transactionNumber.getText());
                transactionCleared.setChecked(transactionCleared.isChecked());


            } else {

                transactionAmount.setText(transaction.getAmount().toString());
                transactionNote.setText(transaction.getNote());
                transactionDate.setText(formattedDate);
                transactionNumber.setText(transaction.getNumber());
                transactionCleared.setChecked(switchCleared);

            }







        });


        Bundle extras = getIntent().getExtras();
        if (extras == null) {


            Instant input = LocalDateTime.now().toInstant(OffsetDateTime.now().getOffset());
            Date output = Date.from(input);


            setTitle(getString(R.string.new_transaction));
            tNewTrans = true;

            transactionDate.setText(Formatters.dateToString(output));


        } else {


            setTitle(getString(R.string.edit_transaction));
            int transId = extras.getInt(TRANSACTION_ID_KEY);
            int payeeId = extras.getInt(PAYEE_ID_KEY);
            int categoryId = extras.getInt(CATEGORY_ID_KEY);
            transViewModel.loadData(transId, payeeId, categoryId);


        }


        // Get a reference to the AutoCompleteTextView in the layout


        String[] payees = transViewModel.getAllPayeesByName(transactionPayee.getText().toString());

        // Create the adapter and set it to the AutoCompleteTextView
        ArrayAdapter<String> pAdapter =
                new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, payees);

        transactionPayee.setAdapter(pAdapter);


        String[] categories = transViewModel.getAllCategoriesByName(transactionCategory.getText().toString());

        // Create the adapter and set it to the AutoCompleteTextView
        ArrayAdapter<String> cAdapter =
                new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, categories);

        transactionCategory.setAdapter(cAdapter);

    }


    @OnTouch(R.id.transaction_detail_date)
    public boolean onTouchCropView2(MotionEvent event) {

        if (MotionEvent.ACTION_UP == event.getAction()) {



//            Calendar mCalender = Calendar.getInstance();
//            int year = mCalender.get(Calendar.YEAR);
//            int month = mCalender.get(Calendar.MONTH);
//            int dayOfMonth = mCalender.get(Calendar.DAY_OF_MONTH);
//            return new DatePickerDialog(getActivity(), (DatePickerDialog.OnDateSetListener)
//                    getActivity(), year, month, dayOfMonth);


            final Calendar cldr = Calendar.getInstance();
            int day = cldr.get(Calendar.DAY_OF_MONTH);
            int month = cldr.get(Calendar.MONTH);
            int year = cldr.get(Calendar.YEAR);




            // date picker dialog
            picker = new DatePickerDialog(TransactionEditorActivity.this,
                    (view1, year1, monthOfYear, dayOfMonth) -> {


                        String monthString = String.valueOf(monthOfYear + 1);
                        String dayString = String.valueOf(dayOfMonth);
                        String yearString = String.valueOf(year1).substring(2);
                        String finalDisplayedMonth;
                        String finalDisplayedDate;

                        if(monthOfYear < 10){
                            finalDisplayedMonth = "0" + monthString;
                        } else {
                            finalDisplayedMonth = monthString;
                        }

                        if(dayOfMonth < 10){
                            finalDisplayedDate = "0" + dayString;
                        } else {
                            finalDisplayedDate = dayString;
                        }




                        String date = finalDisplayedMonth + "/" + finalDisplayedDate + "/" + yearString;

                        //String test = Formatters.dateStringToFormattedDateString(cldr.getTime().toString());



                        transactionDate.setText(date);

                    }, year, month, day);
            picker.show();


        }

        return false;
    }




    @OnTouch(R.id.transaction_detail_category)
    public boolean onTouchCropView(MotionEvent event) {

        if (MotionEvent.ACTION_UP == event.getAction()) {

            String payeeName = transactionPayee.getText().toString();
            String categoryName = transactionCategory.getText().toString();
            String[] payees = transViewModel.getAllPayeesByName(transactionPayee.getText().toString());

            if (!payeeName.matches("")) {

                if (payees.length != 0) {

                    if (categoryName.matches("")) {
                        Toast.makeText(getApplicationContext(), "Test", Toast.LENGTH_SHORT).show();

                        Category category = transViewModel.getAssociatedCategoryIDByPayeeName(transactionPayee.getText().toString());

                        transactionCategory.setThreshold(1000);
                        //transactionCategory.setFocusable(false);
                        transactionCategory.setText(category.getName());
                        //transactionCategory.setFocusable(true);

                        transactionCategory.setThreshold(1);
                    }

                }


            }

        }

        return false;
    }



    @OnCheckedChanged(R.id.transaction_detail_cleared)
    public void checkClearedSwitch(CompoundButton buttonView, boolean isChecked) {


        Toast.makeText(this, "The Switch is " + (isChecked ? "on" : "off"),
                Toast.LENGTH_SHORT).show();
        if (isChecked) {

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
                return true;

            } catch (ParseException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else if(item.getItemId() == R.id.action_delete) {

            transViewModel.deleteTransaction();
            finish();

            startActivity(new Intent(this, MainActivity.class));
            overridePendingTransition(0, 0);




        }

        return super.onOptionsItemSelected(item);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        if (!tNewTrans) {

            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.menu_transaction_editor, menu);

        }

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onBackPressed() {

        try {
            saveAndReturn();
        } catch (Exception e) {
            e.printStackTrace();
        }

        //super.onBackPressed();
    }

    private void saveAndReturn() throws Exception {


        Integer acctId;
        String amount;
        String note;
        Date date;
        String number;
        Integer cleared;
        Integer payeeId;
        String payee;
        Integer categoryId;
        String category;


        if (isCleared == true) {

            cleared = 1;

        } else {

            cleared = 0;

        }


        acctId = accountId;
        amount = transactionAmount.getText().toString();
        note = transactionNote.getText().toString();
        date = Formatters.fullDateFormat.parse(transactionDate.getText().toString());
        number = transactionNumber.getText().toString();
        payee = transactionPayee.getText().toString();
        category = transactionCategory.getText().toString();


        if (number.length() <= 10) {


            transViewModel.saveTransaction(acctId, amount, note, date, number, cleared);
            transViewModel.savePayee(payee);
            transViewModel.saveCategory(category);

            finish();

            startActivity(new Intent(this, MainActivity.class));
            overridePendingTransition(0, 0);



        } else {


            Toast.makeText(this, R.string.number_10_digits_warning,
                    Toast.LENGTH_SHORT).show();




            //TODO: Add possible exceptions here



        }


    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putBoolean(EDITING_KEY, true);
        super.onSaveInstanceState(outState);
    }

}
