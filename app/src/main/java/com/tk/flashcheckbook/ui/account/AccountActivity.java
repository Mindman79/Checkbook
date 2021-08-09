package com.tk.flashcheckbook.ui.account;

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

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.tk.flashcheckbook.MainActivity;
import com.tk.flashcheckbook.R;
import com.tk.flashcheckbook.database.Account;
import com.tk.flashcheckbook.ui.AccountAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AccountActivity extends AppCompatActivity {



    @BindView(R.id.account_recycler_view)
    RecyclerView rv;


    @OnClick(R.id.new_account_fab)
    void fabClickHandler() {

        Intent intent = new Intent(this, AccountEditorActivity.class);
        startActivity(intent);
    }

    private AppBarConfiguration mAppBarConfiguration;

    private final List<Account> accountData = new ArrayList<>();
    private AccountAdapter accountAdapter;
    private AccountViewModel accountViewModel;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);




        setContentView(R.layout.activity_account);
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


        final Observer<List<Account>> accountObserver = accounts -> {


            accountData.clear();
            accountData.addAll(accounts);



            if (accountAdapter == null) {

                accountAdapter = new AccountAdapter(accountData, AccountActivity.this);
                rv.setAdapter(accountAdapter);


            } else {

                accountAdapter.notifyDataSetChanged();

            }

        };

        accountViewModel = new ViewModelProvider(this).get(AccountViewModel.class);
        accountViewModel.accountList.observe(this, accountObserver);




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
        getMenuInflater().inflate(R.menu.account_menu, menu);
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