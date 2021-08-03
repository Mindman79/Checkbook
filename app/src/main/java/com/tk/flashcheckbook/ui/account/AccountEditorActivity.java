package com.tk.flashcheckbook.ui.account;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.tk.flashcheckbook.MainActivity;
import com.tk.flashcheckbook.R;
import com.tk.flashcheckbook.database.Category;
import com.tk.flashcheckbook.util.Formatters;
import com.tk.flashcheckbook.viewmodel.AccountEditorViewModel;
import com.tk.flashcheckbook.viewmodel.TransactionEditorViewModel;

import java.text.ParseException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnTouch;

import static com.tk.flashcheckbook.util.Constants.ACCOUNT_ID_KEY;
import static com.tk.flashcheckbook.util.Constants.CATEGORY_ID_KEY;
import static com.tk.flashcheckbook.util.Constants.PAYEE_ID_KEY;
import static com.tk.flashcheckbook.util.Constants.TRANSACTION_ID_KEY;

public class AccountEditorActivity extends AppCompatActivity {


    @BindView(R.id.transaction_detail_note)
    TextView accountName;
    TextView accountStartDate;
    TextView accountStartBalance;



    private AccountEditorViewModel accountViewModel;
    private boolean isNewAccount;
    private boolean isCleared;



    //@RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_editor);
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


    //@RequiresApi(api = Build.VERSION_CODES.O)
    private void initViewModel() {

        accountViewModel = new ViewModelProvider(this).get(AccountEditorViewModel.class);



        accountViewModel.liveAccount.observe(this, account -> {

            String formattedDate = Formatters.dateToString(account.getStartDate());


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


            accountName.setText(account.getName());
            accountStartDate.setText(formattedDate);
            accountStartBalance.setText(account.getStartBal().toString());
            

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

        } else if(item.getItemId() == R.id.action_delete) {

            accountViewModel.deleteAccount();
            finish();

            startActivity(new Intent(this, AccountActivity.class));
            overridePendingTransition(0, 0);




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

        if (name.length() < 1) {


            accountViewModel.saveAccount(name, amount, date);


            finish();

            startActivity(new Intent(this, AccountEditorActivity.class));
            overridePendingTransition(0, 0);



        } else {


            //TODO: Set custom warning
            Toast.makeText(this, R.string.number_10_digits_warning,
                    Toast.LENGTH_SHORT).show();








        }




    }
}
