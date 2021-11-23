package eecs285.proj4.dawnsqrl_wyanmei.budgettracker;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

public class AddTransactionDialogFragment extends DialogFragment {

  private AddTransactionDialogListener listener;

  interface AddTransactionDialogListener {
    void onDialogPositiveClick_AddTransaction(DialogFragment dialog, String title, String category,
                                              String amount);
  }

  @Override
  public void onAttach(Context context) {
    super.onAttach(context);
    listener = (AddTransactionDialogListener) context;
  }

  @NonNull
  @Override
  public Dialog onCreateDialog(Bundle savedInstance) {
    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
    LayoutInflater inflater = LayoutInflater.from(getActivity());
    builder.setTitle(R.string.title_dialog_add);
    View view = inflater.inflate(R.layout.dialog_add_transaction, null);
    builder.setView(view).setPositiveButton(R.string.button_add,
        (dialog, id) -> addTransaction(view)).setNegativeButton(R.string.button_cancel,
        (dialog, id) -> getDialog().cancel());
    return builder.create();
  }

  void addTransaction(View view) {
    EditText titleText = view.findViewById(R.id.nameInput);
    EditText categoryText = view.findViewById(R.id.categoryInput);
    EditText costText = view.findViewById(R.id.costInput);
    listener.onDialogPositiveClick_AddTransaction(this,
        titleText.getText().toString().trim(),
        categoryText.getText().toString().trim(),
        costText.getText().toString().trim());
  }
}
