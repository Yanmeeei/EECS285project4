package eecs285.proj4.dawnsqrl_wyanmei.budgettracker;

import android.content.Context;
import android.content.res.Configuration;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

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
    if (getItem(position).getAmount() < 0) {
      costView.setText(String.format("-$%.2f", -getItem(position).getAmount()));
      costView.setTextColor(getContext().getResources().getColor(R.color.safe, getContext().getTheme()));
    } else {
      costView.setText(String.format("$%.2f", getItem(position).getAmount()));
      if ((getContext().getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK)
          == Configuration.UI_MODE_NIGHT_NO) {
        costView.setTextColor(getContext().getResources().getColor(R.color.black, getContext().getTheme()));
      } else {
        costView.setTextColor(getContext().getResources().getColor(R.color.white, getContext().getTheme()));
      }
    }

    TextView titleView = convertView.findViewById(R.id.mainCategoryView);
    titleView.setText(getItem(position).getTitle());

    return convertView;
  }
}
