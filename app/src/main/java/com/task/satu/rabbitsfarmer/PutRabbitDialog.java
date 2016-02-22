package com.task.satu.rabbitsfarmer;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.task.satu.rabbitsfarmer.Data.Rabbit;
import com.task.satu.rabbitsfarmer.Interfaces.OnRabbitPut;

import java.util.Date;

/**
 * Created by Piotrek on 2015-05-22.
 */

public class PutRabbitDialog extends DialogFragment {

    OnRabbitPut onRabbitPut;
    EditText editText_name ;
    EditText editText_color ;
    DatePicker date_birthday;
    TextView warning ;

        public PutRabbitDialog() {
        }

        @Override
        public Dialog onCreateDialog(final Bundle savedInstanceState) {

            LayoutInflater inflater = getActivity().getLayoutInflater();
            final View view = inflater.inflate(R.layout.put_measure_dialog, null);

            editText_name = (EditText) view.findViewById(R.id.editText_name);
            editText_color = (EditText) view.findViewById(R.id.editText_color);
            date_birthday = (DatePicker) view.findViewById(R.id.datePicker);
            warning = (TextView) view.findViewById(R.id.textView_warning);

            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setMessage("Dodaj królika").setView(view);
            builder.setNegativeButton("Anuluj", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                }
            });
            builder.setPositiveButton("Dodaj",
                    new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialog, int which)
                        {}
                    });
            return builder.create();
        }

    @Override
    public void onStart()
    {
        super.onStart();    //super.onStart() is where dialog.show() is actually called on the underlying dialog, so we have to do it after this point
        final AlertDialog d = (AlertDialog)getDialog();
        if(d != null)
        {
            Button positiveButton = (Button) d.getButton(Dialog.BUTTON_POSITIVE);
            positiveButton.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {

                        if (editText_color.getText().toString().matches("") || editText_name.getText().toString().matches("") ) {
                            warning.setText(R.string.warning);
                            warning.setVisibility(View.VISIBLE);
                        }
                        else {
                            Date date = new Date();
                            date.setDate(date_birthday.getDayOfMonth());
                            date.setMonth(date_birthday.getMonth());
                            date.setYear(date_birthday.getYear()-1900);
                            Rabbit rabbit = new Rabbit(date, String.valueOf(editText_color.getText()),String.valueOf(editText_name.getText()));
                            onRabbitPut =  (OnRabbitPut) getActivity() ;
                            onRabbitPut.onMedicinePut(rabbit);
                            dismiss();
                        }
                }
            });
        }
    }
}
