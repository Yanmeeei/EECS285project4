package eecs285.proj4.dawnsqrl_wyanmei.budgettracker;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.textfield.TextInputLayout;

public class AddTransactionDialogFragment extends DialogFragment {

    private AddTransactionDialogListener listener;

    interface AddTransactionDialogListener {
        void onDialogPositiveClick_AddTransaction(DialogFragment dialog, String title, String category,
                                                  String amount, boolean isIncome);
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
                (dialogInterface, id) -> {
                    if(!validate(view))
                    {
                        ((ViewGroup) view.getParent()).removeView(view);
                        builder.show();
                    } else {
                        addTransaction(view);
                    }
                });


        builder.setView(view).setNegativeButton(R.string.button_cancel,
                (dialog, id) -> getDialog().cancel());
        return builder.create();
    }

    void addTransaction(View view) {
        EditText titleText = view.findViewById(R.id.nameInput);
        EditText categoryText = view.findViewById(R.id.categoryInput);
        EditText costText = view.findViewById(R.id.costInput);
        Switch isIncome = view.findViewById(R.id.incomeSwitch);

        if (!validate(view)) {
            return;
        }

        listener.onDialogPositiveClick_AddTransaction(this,
                titleText.getText().toString().trim(),
                categoryText.getText().toString().trim(),
                costText.getText().toString().trim(),
                isIncome.isChecked());
    }

    boolean validate(View view) {
        TextInputLayout titleInputLayout = view.findViewById(R.id.titleInputLayout);
        TextInputLayout categoryInputLayout = view.findViewById(R.id.categoryInputLayout);
        TextInputLayout costInputLayout = view.findViewById(R.id.costInputLayout);

        EditText titleText = view.findViewById(R.id.nameInput);
        EditText categoryText = view.findViewById(R.id.categoryInput);
        EditText costText = view.findViewById(R.id.costInput);

        if (TextUtils.isEmpty(titleText.getText().toString())
            || TextUtils.isEmpty(categoryText.getText().toString())
            || TextUtils.isEmpty(costText.getText().toString())) {

            if (TextUtils.isEmpty(titleText.getText().toString())) {
                titleInputLayout.setError("required input");
                titleText.requestFocus();
            }

            if (TextUtils.isEmpty(categoryText.getText().toString())) {
                categoryInputLayout.setError("required input");
                categoryText.requestFocus();
            }

            if (TextUtils.isEmpty(costText.getText().toString())) {
                costInputLayout.setError("required input");
                costText.requestFocus();
            }

            return false;
        } else {
            titleInputLayout.setErrorEnabled(false);
        }
        return true;
    }

}
