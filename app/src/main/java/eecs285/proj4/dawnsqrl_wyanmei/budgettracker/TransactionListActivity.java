package eecs285.proj4.dawnsqrl_wyanmei.budgettracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class TransactionListActivity extends AppCompatActivity
    implements AdapterView.OnItemLongClickListener {

  private ArrayAdapter<Transaction> adapter_transaction;
  private Intent intentBack;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_transaction_list);

    //set layout
    setContentView(R.layout.activity_transaction_list);
    setTitle(R.string.title_activity_transaction_list);

    //get passed info
    Intent intent = getIntent();
    ArrayList<Transaction> transactions = (ArrayList<Transaction>)
        intent.getSerializableExtra(CategoryListActivity.EXTRA_TRANSACTION);
    ArrayList<Category> categories = (ArrayList<Category>)
        intent.getSerializableExtra(CategoryListActivity.EXTRA_CATEGORY);
    adapter_transaction = new TransactionsAdapter(this, R.layout.activity_transaction_list,
        transactions);
    ListView listView = findViewById(R.id.transactionList);
    listView.setAdapter(adapter_transaction);
    listView.setOnItemLongClickListener((parent, view, position, id) -> {
      String thisName = transactions.get(position).getTitle();
      String thisCategory = transactions.get(position).getCategory();
      int categoryIndex = 0;
      for (int i = 0; i < categories.size(); i++) {
        if (categories.get(i).getTitle().equals(thisCategory)) {
          categories.get(i).subtractAmount(transactions.get(position).getAmount(),
              transactions.get(position).isIncome());
          categoryIndex = i;
          break;
        }
      }
      transactions.remove(position);
      boolean hasRemaining = false;
      if (!transactions.isEmpty()) {
        for (int i = 0; i < transactions.size(); i++) {
          if (transactions.get(i).getCategory().equals(thisCategory)) {
            hasRemaining = true;
            break;
          }
        }
      }
      if (!hasRemaining) categories.remove(categoryIndex);
      Toast.makeText(TransactionListActivity.this, "Item \"" + thisName + "\" deleted",
          Toast.LENGTH_SHORT).show();
      adapter_transaction.notifyDataSetChanged();
      listView.setAdapter(adapter_transaction);
      return true;
    });
    intentBack = new Intent(this, CategoryListActivity.class);
    intentBack.putExtra(CategoryListActivity.EXTRA_TRANSACTION, transactions);
    intentBack.putExtra(CategoryListActivity.EXTRA_CATEGORY, categories);
  }

  @Override
  public void onBackPressed() {
    setResult(RESULT_OK, intentBack);
    finish();
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    setResult(RESULT_OK, intentBack);
    finish();
    return true;
  }

  @Override
  public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
    return true;
  }
}
