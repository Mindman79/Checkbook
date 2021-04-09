package com.tk.flashcheckbook.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.tk.flashcheckbook.R;
import com.tk.flashcheckbook.model.Transaction;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.ViewHolder> {



    private final List<Transaction> tTransactions;
    private final Context tContext;


    public TransactionAdapter(List<Transaction> tTransactions, Context tContext) {
        this.tTransactions = tTransactions;
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


        //Currency
        Locale locale = new Locale("en", "US");
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(locale);
        String amount = currencyFormat.format(transaction.getAmount());

        //Date
        String pattern = "MM/dd/YY";
        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
        String date = dateFormat.format(transaction.getDate());


        //Bindings
        holder.payee.setText(transaction.getPayee().getName());
        //holder.balance.setText(transaction.);
        holder.date.setText(date);
        holder.transAmount.setText(amount);
        //holder.category.setText(transaction.getAmount().toString());




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
//
//        @BindView(R.id.category)
//        TextView category;








        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }
}
