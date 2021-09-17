package com.tk.flashcheckbook.ui.payee;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tk.flashcheckbook.MainActivity;
import com.tk.flashcheckbook.R;
import com.tk.flashcheckbook.database.Account;
import com.tk.flashcheckbook.database.Payee;
import com.tk.flashcheckbook.ui.AccountAdapter;
import com.tk.flashcheckbook.ui.PayeeAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PayeeActivity extends AppCompatActivity {



    @BindView(R.id.payee_recycler_view)
    RecyclerView rv;


    @OnClick(R.id.new_payee_fab)
    void fabClickHandler() {

        Intent intent = new Intent(this, PayeeEditorActivity.class);
        startActivity(intent);
    }

    private AppBarConfiguration mAppBarConfiguration;

    private final List<Payee> payeeData = new ArrayList<>();
    private PayeeAdapter payeeAdapter;
    private PayeeViewModel payeeViewModel;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_payee);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Binding
        ButterKnife.bind(this);

        //RecyclerView stuff
        initRecyclerView();

        //ViewModel stuff
        initViewModel();


    }

    private void initViewModel() {


        final Observer<List<Payee>> payeeObserver = payees -> {



            payeeData.clear();
            payeeData.addAll(payees);



            if (payeeAdapter == null) {

                payeeAdapter = new PayeeAdapter(payeeData, PayeeActivity.this);
                rv.setAdapter(payeeAdapter);


            } else {

                payeeAdapter.notifyDataSetChanged();

            }

        };

        payeeViewModel = new ViewModelProvider(this).get(PayeeViewModel.class);
        payeeViewModel.getAllPayees().observe(this, payeeObserver);




    }

    private void initRecyclerView() {

        rv.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rv.setLayoutManager(layoutManager);

        DividerItemDecoration divider = new DividerItemDecoration(rv.getContext(), layoutManager.getOrientation());

        rv.addItemDecoration(divider);



    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.payee_menu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_add_sample_data) {


            return true;

        } else if (id == R.id.action_delete_sample_data) {


            return true;
        } else if (id == android.R.id.home){

            startActivity(new Intent(this, MainActivity.class));

            return true;



        }



        return super.onOptionsItemSelected(item);
    }






    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }


}