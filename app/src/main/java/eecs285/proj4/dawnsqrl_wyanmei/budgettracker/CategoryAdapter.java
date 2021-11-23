package eecs285.proj4.dawnsqrl_wyanmei.budgettracker;

import android.content.Context;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

class CategoryAdapter extends ArrayAdapter<Category> {
//    private Context context;
//    private TreeMap<String, Category> treeMap;
//    private ArrayList<Category> mapKeys;

    CategoryAdapter(Context context, int resource, ArrayList<Category> categories) {
            super(context, resource, categories);
//            this.context=context;
//            this.treeMap=categories;
//
//        for (Map.Entry<String, Category> entry : categories.entrySet()) {
//            mapKeys.add(new Category(entry.getValue().getTitle(), entry.getValue().getAmount()));
//        }

//            mapKeys= (Category[]) categories.values().toArray();
    }

    @NonNull
    @Override
    public View getView(int position,
                        @Nullable View convertView,
                        @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_category, parent, false);
        }

        TextView costView = convertView.findViewById(R.id.mainCostView);
        costView.setText(getItem(position).getAmount().toString());

        TextView titleView = convertView.findViewById(R.id.mainCategoryView);
        titleView.setText(getItem(position).getTitle());

        return convertView;
    }

}


