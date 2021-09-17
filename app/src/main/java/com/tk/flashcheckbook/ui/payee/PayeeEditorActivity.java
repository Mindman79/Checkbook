package com.tk.flashcheckbook.ui.payee;

import static com.tk.flashcheckbook.util.Constants.EDITING_KEY;
import static com.tk.flashcheckbook.util.Constants.PAYEE_ID_KEY;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.tk.flashcheckbook.MainActivity;
import com.tk.flashcheckbook.R;
import com.tk.flashcheckbook.viewmodel.PayeeEditorViewModel;

import java.text.ParseException;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PayeeEditorActivity extends AppCompatActivity {


    @BindView(R.id.payee_detail_name)
    TextView payeeName;



    private PayeeEditorViewModel payeeEditorViewModel;
    private boolean isNewPayee;
    private boolean isEditing;



    //@RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payee_editor);
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



    }


    //@RequiresApi(api = Build.VERSION_CODES.O)
    private void initViewModel() {

        payeeEditorViewModel = new ViewModelProvider(this).get(PayeeEditorViewModel.class);


        payeeEditorViewModel.livePayee.observe(this, payee -> {



            if (payee != null && isEditing == true) {

                payeeName.setText(payeeName.getText());


            } else {

                payeeName.setText(payee.getName());


            }


        });


        Bundle extras = getIntent().getExtras();
        if (extras == null) {



            setTitle(getString(R.string.new_payee));
            isNewPayee = true;



        } else {


            setTitle(getString(R.string.edit_payee));
            int payeeId = extras.getInt(PAYEE_ID_KEY);
            
            payeeEditorViewModel.loadData(payeeId);


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

        } else if(item.getItemId() == R.id.action_delete_payee) {

            payeeEditorViewModel.deletePayee();
            finish();

            startActivity(new Intent(this, PayeeActivity.class));
            overridePendingTransition(0, 0);


        }

        return super.onOptionsItemSelected(item);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        if (!isNewPayee) {

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


        String name;

        name = payeeName.getText().toString();


        //TODO: Resume here 9/15

        if (!name.isEmpty()) {


            payeeEditorViewModel.savePayee(name);

            finish();

            startActivity(new Intent(this, PayeeActivity.class));

            overridePendingTransition(0, 0);


            //TODO: Change this warning

        } else {

            Toast.makeText(this, R.string.number_10_digits_warning,
                    Toast.LENGTH_SHORT).show();








        }




    }






    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putBoolean(EDITING_KEY, true);
        super.onSaveInstanceState(outState);
    }
}
