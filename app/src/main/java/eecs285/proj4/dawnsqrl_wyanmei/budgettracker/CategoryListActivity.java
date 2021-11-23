package eecs285.proj4.dawnsqrl_wyanmei.budgettracker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

public class CategoryListActivity extends AppCompatActivity
        implements AddTransactionDialogFragment.AddTransactionDialogListener {

  public void addTransaction(View view) {
    DialogFragment dialog = new AddTransactionDialogFragment();
    dialog.show(getSupportFragmentManager(), "AddTransactionDialogFragment");
  }

  private static final String TRANSACTION_FILE = "transaction_saveFile";
  private static final String CATEGORY_FILE = "category_saveFile";
  public static final String EXTRA_TRANSACTION = "extra transaction";

  private ArrayList<Transaction> transactions = new ArrayList<>();

  private ArrayList<Category> categories = new ArrayList<>();
  private ArrayAdapter<Category> adapter_cat;

  private void readBudget() {
    File file_tran = new File(getFilesDir(), TRANSACTION_FILE);
    File file_cat = new File(getFilesDir(), CATEGORY_FILE);

    try (ObjectInputStream input =
                 new ObjectInputStream(
                         new FileInputStream(file_tran))) {
      transactions = (ArrayList<Transaction>) input.readObject();
    } catch (IOException | ClassNotFoundException exception) {
      transactions = new ArrayList<>();
    }

    try (ObjectInputStream input =
                 new ObjectInputStream(
                         new FileInputStream(file_cat))) {
      categories = (ArrayList<Category>) input.readObject();
    } catch (IOException | ClassNotFoundException exception) {
      categories = new ArrayList<>();
    }


  }

  private void writeBudgets() {
    File file_tran = new File(getFilesDir(), TRANSACTION_FILE);
    File file_cat = new File(getFilesDir(), CATEGORY_FILE);

    try (ObjectOutputStream output =
                 new ObjectOutputStream(
                         new FileOutputStream(file_tran))) {
      output.writeObject(transactions);
    } catch (IOException exception) {
      // cause runtime error
      throw new IllegalStateException("something bad happened when saving transactions");
    }

    try (ObjectOutputStream output =
                 new ObjectOutputStream(
                         new FileOutputStream(file_cat))) {
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
    ListView listView =
            findViewById(R.id.categoryList);
    listView.setAdapter(adapter_cat);

  }


  static class CategoryComparator implements Comparator<Category>
  {
    public int compare(Category c1, Category c2)
    {
      return c1.getTitle().compareTo(c2.getTitle());
    }
  }

  @Override
  public void onDialogPositiveClick_AddTransaction(DialogFragment dialog,
                                                   String title,
                                                   String category,
                                                   String amount) {
    Double amount_double = Double.parseDouble(amount);
    transactions.add(new Transaction(title, category, amount_double));

    //update category
    boolean found = false;
    for (int i = 0; i < categories.size(); i++) {
      if (categories.get(i).getTitle().equals(category)) {
        categories.get(i).addAmount(amount_double);
        found = true;
        break;
      }
    }
    if (!found) {
      categories.add(new Category(category, amount_double));
    }
    categories.sort(new CategoryComparator());

    writeBudgets();

    adapter_cat.notifyDataSetChanged();

  }

  public void viewTransactions(View view) {
    Intent intent = new Intent(this, TransactionListActivity.class);
    intent.putExtra(EXTRA_TRANSACTION, transactions);
    startActivity(intent);
  }

  public void clearData(View view) {
    transactions.clear();
    categories.clear();

    writeBudgets();

    adapter_cat.notifyDataSetChanged();

  }


}

















