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
import com.tk.flashcheckbook.database.Category;
import com.tk.flashcheckbook.database.Payee;
import com.tk.flashcheckbook.database.Transaction;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.tk.flashcheckbook.util.Constants.CATEGORY_ID_KEY;
import static com.tk.flashcheckbook.util.Constants.PAYEE_ID_KEY;
import static com.tk.flashcheckbook.util.Constants.TRANSACTION_ID_KEY;

public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.ViewHolder> {



    private final List<Transaction> tTransactions;
    private final List<Payee> tPayees;
    private final List<Category> tCategories;


    private final Context tContext;


    public TransactionAdapter(List<Transaction> tTransactions, List<Payee> tPayees, List<Category> tCategories, Context tContext) {
        this.tTransactions = tTransactions;
        this.tPayees = tPayees;
        this.tCategories = tCategories;
        this.tContext = tContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.transaction_list_item, parent, false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        final Transaction transaction = tTransactions.get(position);
        final Payee payee = tPayees.get(position);
        final Category category = tCategories.get(position);


        //Currency
        Locale locale = new Locale("en", "US");
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(locale);
        String amount = currencyFormat.format(transaction.getAmount());

        //Date
        String pattern = "MM/dd/YY";
        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
        String date = dateFormat.format(transaction.getDate());


        //Bindings
        //holder.payee.setText("Test Payee");
        holder.payee.setText(payee.getName());
        //holder.balance.setText(transaction.);
        holder.date.setText(date);
        holder.transAmount.setText(amount);
        //holder.category.setText(category.getName());
        holder.transFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(tContext, TransactionEditorActivity.class);
                intent.putExtra(TRANSACTION_ID_KEY, transaction.getId());
                intent.putExtra(PAYEE_ID_KEY, transaction.getPayeeId());
                intent.putExtra(CATEGORY_ID_KEY, transaction.getCategoryId());
                tContext.startActivity(intent);
            }
        });




    }

    @Override
    public int getItemCount() {
        return tTransactions.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.payee)
        TextView payee;

        @BindView(R.id.transAmount)
        TextView transAmount;

        @BindView(R.id.date)
        TextView date;

        @BindView(R.id.balance)
        TextView balance;

        @BindView(R.id.transaction_fab)
        FloatingActionButton transFab;










        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }
}
