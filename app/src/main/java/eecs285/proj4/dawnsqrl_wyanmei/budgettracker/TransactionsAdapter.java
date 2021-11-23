package eecs285.proj4.dawnsqrl_wyanmei.budgettracker;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class TransactionsAdapter extends ArrayAdapter<Transaction> {
    TransactionsAdapter(Context context, int resource, ArrayList<Transaction> transactions) {
        super(context, resource, transactions);
    }

    @NonNull
    @Override
    public View getView(int position,
                        @Nullable View convertView,
                        @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_transaction, parent, false);
        }
        TextView nameView = convertView.findViewById(R.id.nameView_tran);
        nameView.setText(getItem(position).getTitle());

        TextView categoryView = convertView.findViewById(R.id.categoryView_tran);
        categoryView.setText(getItem(position).getCategory());

        TextView costView = convertView.findViewById(R.id.costView_tran);
        costView.setText(getItem(position).getAmount().toString());

        TextView timeView = convertView.findViewById(R.id.timeView_tran);
        timeView.setText(getItem(position).getTimeStamp());

        return convertView;
    }
}