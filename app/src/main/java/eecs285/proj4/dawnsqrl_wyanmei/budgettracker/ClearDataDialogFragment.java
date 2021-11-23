package eecs285.proj4.dawnsqrl_wyanmei.budgettracker;

import eecs285.proj4.dawnsqrl_wyanmei.budgettracker.CategoryListActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import java.util.ArrayList;

public class ClearDataDialogFragment extends DialogFragment {

    interface ClearDataDialogListener {
        void onDialogPositiveClick_ClearData(DialogFragment dialog);
    }

    private ClearDataDialogListener listener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        listener = (ClearDataDialogListener) context;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstance) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        builder.setTitle(null);
        View view = inflater.inflate(R.layout.dialog_clear_data, null);

        builder.setView(view).setPositiveButton(R.string.button_yes,
                (dialog, id) -> confirmedClearData(view)).setNegativeButton(R.string.button_cancel,
                (dialog, id) -> getDialog().cancel());
        return builder.create();
    }

    void confirmedClearData(View view) {
        listener.onDialogPositiveClick_ClearData(this);
    }

}
