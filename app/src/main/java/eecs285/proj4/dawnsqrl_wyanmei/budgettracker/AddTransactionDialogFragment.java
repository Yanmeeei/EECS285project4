package eecs285.proj4.dawnsqrl_wyanmei.budgettracker;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.textfield.TextInputLayout;

public class AddTransactionDialogFragment extends DialogFragment {

  private AddTransactionDialogListener listener;
  private Context thisContext;

  interface AddTransactionDialogListener {
    void onDialogPositiveClick_AddTransaction(DialogFragment dialog, String title, String category,
                                              String amount, boolean isIncome);
  }

  @Override
  public void onAttach(@NonNull Context context) {
    super.onAttach(context);
    listener = (AddTransactionDialogListener) context;
    thisContext = context;
  }

  @NonNull
  @Override
  public Dialog onCreateDialog(Bundle savedInstance) {
    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
    LayoutInflater inflater = LayoutInflater.from(getActivity());
    builder.setTitle(R.string.title_dialog_add);
    View view = inflater.inflate(R.layout.dialog_add_transaction, null);

    TextInputLayout titleInputLayout = view.findViewById(R.id.titleInputLayout);
    TextInputLayout categoryInputLayout = view.findViewById(R.id.categoryInputLayout);
    TextInputLayout costInputLayout = view.findViewById(R.id.costInputLayout);

    EditText titleText = view.findViewById(R.id.nameInput);
    EditText categoryText = view.findViewById(R.id.categoryInput);
    EditText costText = view.findViewById(R.id.costInput);

    titleText.addTextChangedListener(new TextWatcher() {
      @Override
      public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (s.length() != 0){
          titleInputLayout.setError(null);
        } else {
          titleInputLayout.setError(thisContext.getString(R.string.text_error_name));
          titleText.requestFocus();
        }
      }

      @Override
      public void afterTextChanged(Editable s) {
        if (s.length() != 0){
          titleInputLayout.setError(null);
        }
      }

      @Override
      public void beforeTextChanged(CharSequence s, int start, int count, int after) {
      }
    });

    categoryText.addTextChangedListener(new TextWatcher() {
      @Override
      public void onTextChanged(CharSequence s, int start, int before, int count) {
        categoryInputLayout.setError(null);
      }

      @Override
      public void afterTextChanged(Editable s) {
        categoryText.setError(null);
      }

      @Override
      public void beforeTextChanged(CharSequence s, int start, int count, int after) {
      }
    });

    costText.addTextChangedListener(new TextWatcher() {
      @Override
      public void onTextChanged(CharSequence s, int start, int before, int count) {
        costInputLayout.setError(null);
      }

      @Override
      public void afterTextChanged(Editable s) {
        costText.setError(null);
      }

      @Override
      public void beforeTextChanged(CharSequence s, int start, int count, int after) {
      }
    });

    builder.setView(view).setPositiveButton(R.string.button_add,
        (dialogInterface, id) -> {
          if (!validate(view)) {
            ((ViewGroup) view.getParent()).removeView(view);
            builder.show();
          } else {
            addTransaction(view);
          }
        });

    builder.setView(view).setNegativeButton(R.string.button_cancel,
        (dialogInterface, id) -> {
          if (getDialog() != null) {
            getDialog().cancel();
          }
        });

    return builder.create();
  }

  void addTransaction(View view) {
    EditText titleText = view.findViewById(R.id.nameInput);
    EditText categoryText = view.findViewById(R.id.categoryInput);
    EditText costText = view.findViewById(R.id.costInput);
    SwitchCompat isIncome = view.findViewById(R.id.incomeSwitch);

    if (validate(view)) {
      listener.onDialogPositiveClick_AddTransaction(this,
          titleText.getText().toString().trim(),
          categoryText.getText().toString().trim(),
          costText.getText().toString().trim(),
          isIncome.isChecked());
    }
  }

  boolean validate(View view) {
    TextInputLayout titleInputLayout = view.findViewById(R.id.titleInputLayout);
    TextInputLayout categoryInputLayout = view.findViewById(R.id.categoryInputLayout);
    TextInputLayout costInputLayout = view.findViewById(R.id.costInputLayout);

    EditText titleText = view.findViewById(R.id.nameInput);
    EditText categoryText = view.findViewById(R.id.categoryInput);
    EditText costText = view.findViewById(R.id.costInput);

    String titleString = titleText.getText().toString();
    String categoryString = categoryText.getText().toString();
    String costString = costText.getText().toString();

    if (TextUtils.isEmpty(titleString) || TextUtils.isEmpty(categoryString)
        || TextUtils.isEmpty(costString)
        || !costString.matches("^0|[1-9][0-9]*|0\\.[0-9]{1,2}|[1-9][0-9]*\\.[0-9]{1,2}$")) {

      if (TextUtils.isEmpty(titleString)) {
        titleInputLayout.setError(thisContext.getString(R.string.text_error_name));
        titleText.requestFocus();
      } else {
        titleInputLayout.setError(null);
      }

      if (TextUtils.isEmpty(categoryString)) {
        categoryInputLayout.setError(thisContext.getString(R.string.text_error_category));
        categoryText.requestFocus();
      } else {
        categoryInputLayout.setError(null);
      }

      if (TextUtils.isEmpty(costString)) {
        costInputLayout.setError(thisContext.getString(R.string.text_error_cost_empty));
        costText.requestFocus();
      } else if (!costString.matches("^0|[1-9][0-9]*|0\\.[0-9]{1,2}|[1-9][0-9]*\\.[0-9]{1,2}$")) {
        costInputLayout.setError(thisContext.getString(R.string.text_error_cost_format));
        costText.requestFocus();
      } else {
        costInputLayout.setError(null);
      }

      return false;
    } else {
      titleInputLayout.setErrorEnabled(false);
      return true;
    }
  }
}
