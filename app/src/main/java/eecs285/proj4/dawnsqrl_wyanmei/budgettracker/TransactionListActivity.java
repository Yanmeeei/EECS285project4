package eecs285.proj4.dawnsqrl_wyanmei.budgettracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class TransactionListActivity extends AppCompatActivity {

  private ArrayAdapter<Transaction> adapter_transaction;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_transaction_list);

    //set layput
    setContentView(R.layout.activity_transaction_list);
    setTitle(R.string.title_activity_transaction_list);

    //get passed info
    Intent intent = getIntent();
    ArrayList<Transaction> transactions = (ArrayList<Transaction>)
        intent.getSerializableExtra(CategoryListActivity.EXTRA_TRANSACTION);
    adapter_transaction = new TransactionsAdapter(this, R.layout.activity_transaction_list,
        transactions);
    ListView listView = findViewById(R.id.transactionList);
    listView.setAdapter(adapter_transaction);
  }
}
