package com.tk.flashcheckbook.ui;

import static com.tk.flashcheckbook.util.Constants.PAYEE_ID_KEY;

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
import com.tk.flashcheckbook.database.AppRepository;
import com.tk.flashcheckbook.database.Payee;
import com.tk.flashcheckbook.ui.payee.PayeeEditorActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PayeeAdapter extends RecyclerView.Adapter<PayeeAdapter.ViewHolder> {

    private final List<Payee> payees;
    private final Context context;

    public PayeeAdapter(List<Payee> payees, Context context) {
        this.payees = payees;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.payee_list_item, parent, false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {


        final Payee payee = payees.get(holder.getAdapterPosition());


        //AppRepository repository = new AppRepository(context);
        


        //Bindings
        holder.payeeName.setText(payee.getName());

        holder.payeeFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, PayeeEditorActivity.class);

               //TODO: Resume here

                intent.putExtra(PAYEE_ID_KEY, payee.getId());

                context.startActivity(intent);
            }
        });


    }


    @Override
    public int getItemCount() {
        return payees.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.payee_name)
        TextView payeeName;

        @BindView(R.id.payee_fab)
        FloatingActionButton payeeFab;






        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }
}
