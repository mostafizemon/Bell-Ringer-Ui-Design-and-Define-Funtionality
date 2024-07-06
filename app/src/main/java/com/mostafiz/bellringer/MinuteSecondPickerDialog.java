package com.mostafiz.bellringer;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.NumberPicker;

public class MinuteSecondPickerDialog extends AlertDialog {

    private NumberPicker minutePicker;
    private NumberPicker secondPicker;

    public MinuteSecondPickerDialog(Context context, OnTimeSetListener listener) {
        super(context);
        init(context, listener);
    }

    private void init(Context context, OnTimeSetListener listener) {
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_minute_second_picker, null);
        setView(view);

        minutePicker = view.findViewById(R.id.minute_picker);
        secondPicker = view.findViewById(R.id.second_picker);

        minutePicker.setMinValue(0);
        minutePicker.setMaxValue(59);

        secondPicker.setMinValue(0);
        secondPicker.setMaxValue(59);

        view.findViewById(R.id.set_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int minutes = minutePicker.getValue();
                int seconds = secondPicker.getValue();
                listener.onTimeSet(minutes, seconds);
                dismiss();
            }
        });

        view.findViewById(R.id.cancel_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancel();
            }
        });
    }

    public interface OnTimeSetListener {
        void onTimeSet(int minutes, int seconds);
    }
}

