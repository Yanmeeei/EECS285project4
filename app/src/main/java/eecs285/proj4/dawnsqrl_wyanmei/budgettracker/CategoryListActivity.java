package eecs285.proj4.dawnsqrl_wyanmei.budgettracker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.content.Intent;
import android.os.Bundle;
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

  private static final String BUDGET_FILE = "budget_tracker_saveFile";
  public static final String EXTRA_TRANSACTION = "extra transaction";

  private ArrayList<Transaction> transactions = new ArrayList<>();

//  private TreeMap<String, Category> categories = new TreeMap<>();
  private ArrayList<Category> categories = new ArrayList<>();
  private ArrayAdapter<Category> adapter_cat;

  private void readBudget() {
    File file = new File(getFilesDir(), BUDGET_FILE);
    try (ObjectInputStream input =
                 new ObjectInputStream(
                         new FileInputStream(file))) {
      //只储存transaction，之后生成category
      //或者都储存）
      transactions = (ArrayList<Transaction>) input.readObject();
//      categories = (ArrayList<Category>) input.readObject();
      categories = new ArrayList<>();
      //TODO: 处理Category

    } catch (IOException | ClassNotFoundException exception) {
      transactions = new ArrayList<>();
//      categories = new TreeMap<>();
      categories = new ArrayList<>();
    }
  }

  private void writeTransactions() {
    File file = new File(getFilesDir(), BUDGET_FILE);
    try (ObjectOutputStream output =
                 new ObjectOutputStream(
                         new FileOutputStream(file))) {
      output.writeObject(transactions);
//      output.writeObject(categories);
    } catch (IOException exception) {
      // cause runtime error
      throw new IllegalStateException("something bad happened");
    }
  }

  private void writeCategories() {
    File file = new File(getFilesDir(), BUDGET_FILE);
    try (ObjectOutputStream output =
                 new ObjectOutputStream(
                         new FileOutputStream(file))) {
//      output.writeObject(transactions);
      output.writeObject(categories);
    } catch (IOException exception) {
      // cause runtime error
      throw new IllegalStateException("something bad happened");
    }
  }


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_category_list);

//    readBudget();

    adapter_cat = new CategoryAdapter(this, R.layout.item_category, categories);
    ListView listView =
            findViewById(R.id.categoryList);
    listView.setAdapter(adapter_cat);



//    Button btn = (Button) findViewById(R.id.transactionButton);
//
//    btn.setOnClickListener(new View.OnClickListener() {
//      @Override
//      public void onClick(View v) {
////        startActivity(new Intent(CategoryListActivity.this, TransactionListActivity.class));
//        viewTransactions();
//      }
//    });

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
    writeTransactions();
//    adapter_tran.notifyDataSetChanged();

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




//    if (categories.containsKey(category)) {
//      categories.put(category,
//                      new Category(category,
//                              amount_double + categories.get(category).getAmount()));
//    } else {
//      categories.put(category, new Category(category, amount_double));
//    }
//    adapter_cat = new CategoryAdapter(this, R.layout.item_category, convertTreeMap(categories));
    adapter_cat.notifyDataSetChanged();
//    writeCategories();
//    adapter_cat.notifyDataSetChanged();
  }

  ArrayList<Category> convertTreeMap(TreeMap<String, Category> categories) {
    ArrayList<Category> tray = new ArrayList<>();
    for (Map.Entry<String, Category> entry : categories.entrySet()) {
      tray.add(new Category(entry.getValue().getTitle(), entry.getValue().getAmount()));
    }
    return tray;
  }


  public void viewTransactions(View view) {
    Intent intent = new Intent(this, TransactionListActivity.class);
    intent.putExtra(EXTRA_TRANSACTION, transactions);
    startActivity(intent);
  }

}

















