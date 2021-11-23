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
import java.util.Collection;

class CategoryAdapter extends ArrayAdapter<Category> {

    CategoryAdapter(Context context, int resource, ArrayList<Category> categories) {
        super(context, resource, categories);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_category,
                    parent, false);
        }

        TextView costView = convertView.findViewById(R.id.mainCostView);
        costView.setText(String.format("$%.2f", getItem(position).getAmount()));

        TextView titleView = convertView.findViewById(R.id.mainCategoryView);
        titleView.setText(getItem(position).getTitle());

        return convertView;
    }
}
