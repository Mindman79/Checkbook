package com.tk.flashcheckbook;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;
import com.tk.flashcheckbook.database.Category;
import com.tk.flashcheckbook.database.Payee;
import com.tk.flashcheckbook.database.Transaction;
import com.tk.flashcheckbook.ui.TransactionAdapter;
import com.tk.flashcheckbook.ui.account.FirstTimeSetup;
import com.tk.flashcheckbook.viewmodel.MainViewModel;

import androidx.appcompat.app.ActionBar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemClick;
import butterknife.OnItemSelected;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.main_recycler_view)
    RecyclerView rv;

    @BindView(R.id.clearedAmount)
    TextView clearedAmountTextView;

    @BindView(R.id.balanceAmount)
    TextView balanceAmountTextView;

    @BindView(R.id.account_select_spinner)
    Spinner accountSelectSpinner;


    @OnClick(R.id.newtransfab)
    void fabClickHandler() {

        Intent intent = new Intent(this, TransactionEditorActivity.class);
        startActivity(intent);
    }

    //private RecyclerView rv;
    private AppBarConfiguration mAppBarConfiguration;

    private List<Transaction> transactionData = new ArrayList<>();
    private List<Payee> payeeData = new ArrayList<>();
    private List<Category> categoryData = new ArrayList<>();
    private TransactionAdapter transAdapter;
    private MainViewModel mainViewModel;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


                setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Set custom view for spinner in Action Bar
        ActionBar actionBar = getSupportActionBar();
        LayoutInflater inflator = (LayoutInflater) this .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflator.inflate(R.layout.account_spinner, null);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setCustomView(v);
        actionBar.setDisplayShowTitleEnabled(false);


        //Binding
        ButterKnife.bind(this);



        //RecyclerView stuff
        initRecyclerView();

        //ViewModel stuff
        initViewModel();

        initSpinner();

        if (mainViewModel.getAccountCount() == 0) {

            Intent intent = new Intent(this, FirstTimeSetup.class);
            startActivity(intent);

        }

        //clearedAmount.invalidate();
        String balanceAmount = String.valueOf(mainViewModel.getTotalofAllTransactions());
        String clearedAmount = String.valueOf(mainViewModel.getTotalofClearedTransactions());

        clearedAmountTextView.setText(clearedAmount);
        balanceAmountTextView.setText(balanceAmount);


        //View Sample data. Actual data displayed is configured below in the initRecyclerView
        //transactionData.addAll(mainViewModel.transactionsList);





        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    private void initSpinner() {

        String[] accountNames = mainViewModel.getAccountNames();

        String[] arraySpinner = new String[] {
                "1", "2", "3", "4", "5", "6", "7"
        };


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, accountNames);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        accountSelectSpinner.setAdapter(adapter);


    }

    @OnItemSelected(R.id.account_select_spinner)
    public void updateViewUsingSpinner() {




    }

    private void initViewModel() {

        final Observer<List<Payee>> payeeObserver = payees -> {

            payeeData.clear();
            payeeData.addAll(payees);


        };

        final Observer<List<Category>> categoryObserver = categories -> {

            categoryData.clear();
            categoryData.addAll(categories);

        };

        final Observer<List<Transaction>> transactionObserver = transactions -> {


            transactionData.clear();
            transactionData.addAll(transactions);



            if (transAdapter == null) {

                transAdapter = new TransactionAdapter(transactionData, payeeData, categoryData, MainActivity.this);
                rv.setAdapter(transAdapter);


            } else {

                transAdapter.notifyDataSetChanged();

            }

        };

        mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);
        mainViewModel.transactionsList.observe(this, transactionObserver);
        mainViewModel.payeesList.observe(this, payeeObserver);
        mainViewModel.categoryList.observe(this, categoryObserver);



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
        getMenuInflater().inflate(R.menu.main_menu, menu);




        return true;
    }




    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_add_sample_data) {

            addSampleData();
            return true;

        } else if (id == R.id.action_delete_sample_data) {

            deleteAllData();

            return true;
        }



        return super.onOptionsItemSelected(item);
    }

    //This is a good example of the chain of calls using the repository
    private void deleteAllData() {

        mainViewModel.deleteAllNotes();

    }

    private void addSampleData() {


        mainViewModel.addSampleData();


    }


    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}