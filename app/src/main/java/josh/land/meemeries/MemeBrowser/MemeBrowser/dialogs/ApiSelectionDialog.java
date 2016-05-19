package josh.land.meemeries.MemeBrowser.MemeBrowser.dialogs;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.RadioGroup;

import org.greenrobot.eventbus.EventBus;

import josh.land.meemeries.MemeBrowser.MemeBrowser.events.ApiChoiceModified;
import josh.land.meemeries.MemeBrowser.Utils.SharedPrefManager;
import josh.land.meemeries.R;

public class ApiSelectionDialog extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(inflater.inflate(R.layout.api_selection_dialog, null))
                // Add action buttons
                .setPositiveButton(R.string.save_button_text, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        Dialog d = (Dialog) dialog;
                        RadioGroup radioGroup = (RadioGroup) d.findViewById(R.id.api_selection_radio_group);
                        Button button = (Button) d.findViewById(radioGroup.getCheckedRadioButtonId());

                        if (button != null && button.getText() != null) {
                            switch(button.getText().toString().toLowerCase()) {
                                case "appery":
                                    SharedPrefManager.setApiType(SharedPrefManager.ApiType.ApperyIO, d.getContext());
                                    break;
                                case "firebase":
                                    SharedPrefManager.setApiType(SharedPrefManager.ApiType.Firebase, d.getContext());
                                    break;
                                case "backendless":
                                    SharedPrefManager.setApiType(SharedPrefManager.ApiType.Backendless, d.getContext());
                                    break;
                            }
                        }

                        // Delayed activity recreation - delaying as if you fire immediately the
                        // Recreated activity will still have the dialog.
                        Handler handler = new Handler();
                        final Runnable r = new Runnable() {
                            public void run() {
                                EventBus.getDefault().post(new ApiChoiceModified());
                            }
                        };
                        handler.postDelayed(r, 500);
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
