package com.tk.flashcheckbook.ui.account;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.tk.flashcheckbook.MainActivity;
import com.tk.flashcheckbook.R;
import com.tk.flashcheckbook.util.Formatters;
import com.tk.flashcheckbook.viewmodel.AccountEditorViewModel;

import java.text.ParseException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTouch;
import butterknife.Optional;

import static com.tk.flashcheckbook.util.Constants.ACCOUNT_ID_KEY;
import static com.tk.flashcheckbook.util.Constants.EDITING_KEY;

public class AccountEditorActivity extends AppCompatActivity {


    @BindView(R.id.account_detail_name)
    TextView accountName;

    @BindView(R.id.account_detail_starting_date)
    TextView accountStartDate;

    @BindView(R.id.account_detail_starting_balance)
    TextView accountStartBalance;

    @BindView(R.id.account_save_button)
    Button addAccountButton;

    DatePickerDialog picker;


    private AccountEditorViewModel accountViewModel;
    private boolean isNewAccount;
    private boolean isEditing;
    private boolean isFirstAccount;



    //@RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_editor);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //TODO: Change graphic for back button
        //getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu_gallery);


        CollapsingToolbarLayout toolBarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        toolBarLayout.setTitle(getTitle());

        ButterKnife.bind(this);

        //Handling device orientation switches
        if (savedInstanceState != null) {

            isEditing = savedInstanceState.getBoolean(EDITING_KEY);

        }

        initViewModel();



        if (accountViewModel.getAccountCount() == 0) {

            isFirstAccount = true;
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            addAccountButton.setVisibility(View.VISIBLE);
            setTitle(getString(R.string.add_initial_account));


        } else {

            isFirstAccount = false;
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            addAccountButton.setVisibility(View.INVISIBLE);
            //addAccountButton.setEnabled(false);


        }


    }


    //@RequiresApi(api = Build.VERSION_CODES.O)
    private void initViewModel() {

        accountViewModel = new ViewModelProvider(this).get(AccountEditorViewModel.class);

        //TODO: Investigate what to do about picking dates with in landscape mode

        accountViewModel.liveAccount.observe(this, account -> {

            String formattedDate = Formatters.dateToString(account.getStartDate());

            if (account != null && isEditing == true) {

                accountName.setText(accountName.getText());
                accountStartDate.setText(accountStartDate.getText().toString());
                accountStartBalance.setText(accountStartBalance.getText());

            } else {

                accountName.setText(account.getName());
                accountStartDate.setText(formattedDate);
                accountStartBalance.setText(account.getStartBal().toString());

            }

            //TODO: Maybe reuse this code for setting the switch of the account balance to positive or negative

//            boolean switchCleared;
//
//            if (transaction.getCleared() == 1) {
//
//                switchCleared = true;
//
//
//            } else {
//
//                switchCleared = false;
//            }



            

        });


        Bundle extras = getIntent().getExtras();
        if (extras == null) {


            Instant input = LocalDateTime.now().toInstant(OffsetDateTime.now().getOffset());
            Date output = Date.from(input);


            setTitle(getString(R.string.new_account));
            isNewAccount = true;

            accountStartDate.setText(Formatters.dateToString(output));


        } else {


            setTitle(getString(R.string.edit_account));
            int accountId = extras.getInt(ACCOUNT_ID_KEY);
            
            accountViewModel.loadData(accountId);


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

        } else if(item.getItemId() == R.id.action_delete_account) {

            accountViewModel.deleteAccount();
            finish();

            startActivity(new Intent(this, AccountActivity.class));
            overridePendingTransition(0, 0);



            //TODO: Fix bug with account deletion not refreshing the RecyclerView


        }

        return super.onOptionsItemSelected(item);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        if (!isNewAccount) {

            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.menu_account_editor, menu);

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


        String amount;
        Date date;
        String name;
        


        amount = accountStartBalance.getText().toString();
        date = Formatters.fullDateFormat.parse(accountStartDate.getText().toString());
        name = accountName.getText().toString();


        //TODO: Add more checks

        if (!name.isEmpty() && !amount.isEmpty()) {


            accountViewModel.saveAccount(name, amount, date);


            finish();

            if (isFirstAccount == true ) {

                startActivity(new Intent(this, MainActivity.class));

            } else {

                startActivity(new Intent(this, AccountActivity.class));

            }


            overridePendingTransition(0, 0);



        } else {


            //TODO: Set custom warning
            Toast.makeText(this, R.string.number_10_digits_warning,
                    Toast.LENGTH_SHORT).show();








        }




    }

    @OnTouch(R.id.account_detail_starting_date)
    public boolean onTouchCropView2(MotionEvent event) {

        if (MotionEvent.ACTION_UP == event.getAction()) {


            final Calendar cldr = Calendar.getInstance();
            int day = cldr.get(Calendar.DAY_OF_MONTH);
            int month = cldr.get(Calendar.MONTH);
            int year = cldr.get(Calendar.YEAR);




            // date picker dialog
            picker = new DatePickerDialog(AccountEditorActivity.this,
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


                        accountStartDate.setText(date);

                    }, year, month, day);
            picker.show();


        }

        return false;
    }


    @Optional
    @OnClick(R.id.account_save_button)
    public void submit(View view)  {

        try {
            saveAndReturn();
        } catch (Exception e) {
            e.printStackTrace();
        }
//        Intent intent = new Intent(this, MainActivity.class);
//        startActivity(intent);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putBoolean(EDITING_KEY, true);
        super.onSaveInstanceState(outState);
    }
}
