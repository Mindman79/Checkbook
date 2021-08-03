package com.tk.flashcheckbook.ui;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.tk.flashcheckbook.R;
import com.tk.flashcheckbook.TransactionEditorActivity;
import com.tk.flashcheckbook.database.Account;
import com.tk.flashcheckbook.database.AppRepository;
import com.tk.flashcheckbook.database.Category;
import com.tk.flashcheckbook.database.Payee;
import com.tk.flashcheckbook.database.Transaction;
import com.tk.flashcheckbook.ui.account.AccountEditorActivity;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.tk.flashcheckbook.util.Constants.ACCOUNT_ID_KEY;
import static com.tk.flashcheckbook.util.Constants.CATEGORY_ID_KEY;
import static com.tk.flashcheckbook.util.Constants.PAYEE_ID_KEY;
import static com.tk.flashcheckbook.util.Constants.TRANSACTION_ID_KEY;

public class AccountAdapter extends RecyclerView.Adapter<AccountAdapter.ViewHolder> {

    private final List<Account> tAccounts;
    private final Context tContext;

    public AccountAdapter(List<Account> tAccounts, Context tContext) {
        this.tAccounts = tAccounts;
        this.tContext = tContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.account_list_item, parent, false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {


        final Account account = tAccounts.get(holder.getAdapterPosition());


        //AppRepository repository = new AppRepository(tContext);
        
//        //Currency
//        Locale locale = new Locale("en", "US");
//        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(locale);
//        String amount = currencyFormat.format(transaction.getAmount());
//
//        //Date
//        String pattern = "MM/dd/YY";
//        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
//        String date = dateFormat.format(transaction.getDate());

        //Bindings
        holder.accountName.setText(account.getName());

        holder.accountFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(tContext, com.tk.flashcheckbook.ui.account.AccountEditorActivity.class);

               //TODO: Resume here

                intent.putExtra(ACCOUNT_ID_KEY, account.getId());

                tContext.startActivity(intent);
            }
        });


    }


    @Override
    public int getItemCount() {
        return tAccounts.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.account_name)
        TextView accountName;

        @BindView(R.id.account_fab)
        FloatingActionButton accountFab;






        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }
}
