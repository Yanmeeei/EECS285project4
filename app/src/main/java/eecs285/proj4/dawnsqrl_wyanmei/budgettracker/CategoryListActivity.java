package eecs285.proj4.dawnsqrl_wyanmei.budgettracker;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Locale;

public class CategoryListActivity extends AppCompatActivity
    implements AddTransactionDialogFragment.AddTransactionDialogListener,
    ClearDataDialogFragment.ClearDataDialogListener {

  private static final String TRANSACTION_FILE = "transaction_saveFile";
  private static final String CATEGORY_FILE = "category_saveFile";
  public static final String EXTRA_TRANSACTION = "extra transaction";
  private ArrayList<Transaction> transactions = new ArrayList<>();
  private ArrayList<Category> categories = new ArrayList<>();
  private ArrayAdapter<Category> adapter_cat;

  public void addTransaction(View view) {
    DialogFragment dialog = new AddTransactionDialogFragment();
    dialog.show(getSupportFragmentManager(), "AddTransactionDialogFragment");
    dialog.setCancelable(false);
  }

  private void readBudget() {
    File file_tran = new File(getFilesDir(), TRANSACTION_FILE);
    File file_cat = new File(getFilesDir(), CATEGORY_FILE);

    try (ObjectInputStream input = new ObjectInputStream(new FileInputStream(file_tran))) {
      transactions = (ArrayList<Transaction>) input.readObject();
    } catch (IOException | ClassNotFoundException exception) {
      transactions = new ArrayList<>();
    }

    try (ObjectInputStream input = new ObjectInputStream(new FileInputStream(file_cat))) {
      categories = (ArrayList<Category>) input.readObject();
    } catch (IOException | ClassNotFoundException exception) {
      categories = new ArrayList<>();
    }
  }

  private void writeBudgets() {
    File file_tran = new File(getFilesDir(), TRANSACTION_FILE);
    File file_cat = new File(getFilesDir(), CATEGORY_FILE);

    try (ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(file_tran))) {
      output.writeObject(transactions);
    } catch (IOException exception) {
      // cause runtime error
      throw new IllegalStateException("something bad happened when saving transactions");
    }

    try (ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(file_cat))) {
      output.writeObject(categories);
    } catch (IOException exception) {
      // cause runtime error
      throw new IllegalStateException("something bad happened when saving categories");
    }
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_category_list);

    readBudget();

    adapter_cat = new CategoryAdapter(this, R.layout.item_category, categories);
    ListView listView = findViewById(R.id.categoryList);
    listView.setAdapter(adapter_cat);

    if ((getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK)
        == Configuration.UI_MODE_NIGHT_NO) {
      Toast.makeText(this, R.string.toast_dark_mode_one, Toast.LENGTH_LONG).show();
      Toast.makeText(this, R.string.toast_dark_mode_two, Toast.LENGTH_LONG).show();
    }

    viewTotal();
  }

  @Override
  public void onDialogPositiveClick_ClearData(DialogFragment dialog) {
    transactions.clear();
    categories.clear();
    viewTotal();
    writeBudgets();
    adapter_cat.notifyDataSetChanged();
  }

  static class CategoryComparator implements Comparator<Category> {
    public int compare(Category c1, Category c2) {
      return c1.getTitle().compareTo(c2.getTitle());
    }
  }

  @Override
  public void onDialogPositiveClick_AddTransaction(DialogFragment dialog, String title,
                                                   String category, String amount,
                                                   boolean isIncome) {
    Double amount_double = Double.parseDouble(amount);
    transactions.add(0, new Transaction(title, category, amount_double, isIncome));

    //update category
    boolean found = false;
    for (int i = 0; i < categories.size(); i++) {
      if (categories.get(i).getTitle().equals(category)) {
        categories.get(i).addAmount(amount_double, isIncome);
        found = true;
        break;
      }
    }
    if (!found) {
      categories.add(new Category(category, amount_double, isIncome));
    }
    categories.sort(new CategoryComparator());
    writeBudgets();
    adapter_cat.notifyDataSetChanged();

    viewTotal();
  }

  public void viewTransactions(View view) {
    Intent intent = new Intent(this, TransactionListActivity.class);
    intent.putExtra(EXTRA_TRANSACTION, transactions);
    startActivity(intent);
  }

  public void clearData_onClickButton(View view) {
    DialogFragment dialog = new ClearDataDialogFragment();
    dialog.show(getSupportFragmentManager(), "ClearDataDialogFragment");
  }

  void viewTotal() {
    Double totalAmount = 0.0;
    for (int i = 0; i < categories.size(); i++) {
      totalAmount += categories.get(i).getAmount();
    }

    TextView totalView = findViewById(R.id.totalCostView);

    if (totalAmount >= 0) {
      totalView.setText(String.format(Locale.getDefault(), "$%.2f", totalAmount));
      if ((getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK)
          == Configuration.UI_MODE_NIGHT_NO) {
        totalView.setTextColor(getResources().getColor(R.color.black, getTheme()));
      } else {
        totalView.setTextColor(getResources().getColor(R.color.white, getTheme()));
      }
    } else {
      totalView.setText(String.format(Locale.getDefault(), "-$%.2f", -totalAmount));
      totalView.setTextColor(getResources().getColor(R.color.safe, getTheme()));
    }
  }
}
