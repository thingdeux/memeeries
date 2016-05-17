package josh.land.meemeries.MemeBrowser.MemeBrowser.dialogs;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

import android.view.LayoutInflater;
import android.widget.EditText;

import josh.land.meemeries.MemeBrowser.Utils.SharedPrefManager;
import josh.land.meemeries.R;

public class UsernameEntryDialog extends DialogFragment{

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(inflater.inflate(R.layout.username_dialog, null))
            // Add action buttons
            .setPositiveButton(R.string.save_button_text, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int id) {
                    Dialog d = (Dialog) dialog;
                    EditText editText = (EditText) d.findViewById(R.id.username_entry);
                    if (editText != null && editText.getText() != null
                        && !editText.getText().toString().isEmpty()) {
                        SharedPrefManager.setUsername(editText.getText().toString().toLowerCase(), getContext());
                    }
                }
            })
            .setNegativeButton(R.string.cancel_button_text, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    // Intentionally empty
                }
            });



        return builder.create();
    }
}
