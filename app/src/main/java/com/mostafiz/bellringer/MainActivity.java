package com.mostafiz.bellringer;

import android.app.TimePickerDialog;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private LinearLayout mainLayout;
    private TextView selectedOptionTextView;
    private View currentSelectedPrimaryButton = null;
    private View currentSecondaryOptions = null;
    private TextView currentHighlightedButton = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainLayout = findViewById(R.id.main);
        selectedOptionTextView = new TextView(this);
        selectedOptionTextView.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        ));
        selectedOptionTextView.setPadding(16, 16, 16, 16);
        mainLayout.addView(selectedOptionTextView);

        TextView buttonCutting = findViewById(R.id.button_cutting);
        TextView buttonMaintenance = findViewById(R.id.button_maintenance);
        TextView buttonTechnical = findViewById(R.id.button_technical);
        TextView buttonStore = findViewById(R.id.button_store);

        View.OnClickListener primaryButtonClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleSecondaryOptions((TextView) v);
            }
        };

        buttonCutting.setOnClickListener(primaryButtonClickListener);
        buttonMaintenance.setOnClickListener(primaryButtonClickListener);
        buttonTechnical.setOnClickListener(primaryButtonClickListener);
        buttonStore.setOnClickListener(primaryButtonClickListener);
    }

    private void toggleSecondaryOptions(TextView selectedButton) {
        if (currentSelectedPrimaryButton == selectedButton && currentSecondaryOptions != null) {
            // Hide secondary options if the same primary button is clicked again
            mainLayout.removeView(currentSecondaryOptions);
            currentSecondaryOptions = null;
            currentSelectedPrimaryButton = null;
        } else {
            // Show secondary options
            showSecondaryOptions(selectedButton);
        }
    }

    private void showSecondaryOptions(TextView selectedButton) {
        // Remove existing secondary options if any
        if (currentSecondaryOptions != null) {
            mainLayout.removeView(currentSecondaryOptions);
        }

        // Create a new LinearLayout for secondary options
        LinearLayout secondaryOptions = new LinearLayout(this);
        secondaryOptions.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        ));
        secondaryOptions.setOrientation(LinearLayout.VERTICAL);
        secondaryOptions.setTag("secondary_options");

        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        LinearLayout.LayoutParams buttonLayoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        int marginStart = dpToPx(40, displayMetrics);
        int marginEnd = dpToPx(40, displayMetrics);
        int marginTop = dpToPx(5, displayMetrics);
        int marginBottom = dpToPx(5, displayMetrics);
        buttonLayoutParams.setMargins(marginStart, marginTop, marginEnd, marginBottom);

        TextView buttonOn = createSecondaryButton("On", buttonLayoutParams);
        buttonOn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedOptionTextView.setText("On selected");
                highlightSelectedButton((TextView) v);
            }
        });

        TextView buttonOff = createSecondaryButton("Off", buttonLayoutParams);
        buttonOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedOptionTextView.setText("Off selected");
                highlightSelectedButton((TextView) v);
            }
        });

        TextView buttonSetTimer = createSecondaryButton("Set Timer", buttonLayoutParams);
        buttonSetTimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePickerDialog();
            }
        });

        secondaryOptions.addView(buttonOn);
        secondaryOptions.addView(buttonOff);
        secondaryOptions.addView(buttonSetTimer);

        // Find the index of the selected button
        int index = mainLayout.indexOfChild(selectedButton);

        // Add secondary options below the selected button
        mainLayout.addView(secondaryOptions, index + 1);

        // Update the current selected primary button and secondary options
        currentSelectedPrimaryButton = selectedButton;
        currentSecondaryOptions = secondaryOptions;
    }

    private void showTimePickerDialog() {
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        selectedOptionTextView.setText("Timer set to: " + hourOfDay + ":" + minute);
                    }
                }, hour, minute, true);

        timePickerDialog.show();
    }

    private TextView createSecondaryButton(String text, LinearLayout.LayoutParams layoutParams) {
        TextView button = new TextView(this);
        button.setLayoutParams(layoutParams);
        button.setText(text);
        button.setPadding(16, 16, 16, 16);
        button.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        button.setGravity(View.TEXT_ALIGNMENT_CENTER);
        button.setTextSize(18);
        button.setTypeface(null, Typeface.BOLD);
        button.setBackgroundResource(R.drawable.secondary_button_design);
        return button;
    }

    private void highlightSelectedButton(TextView selectedButton) {
        if (currentHighlightedButton != null) {
            // Reset the background of the previously highlighted button
            currentHighlightedButton.setBackgroundResource(R.drawable.secondary_button_design);
        }

        // Highlight the selected button
        selectedButton.setBackgroundResource(R.drawable.selected_button_design);
        currentHighlightedButton = selectedButton;
    }

    private int dpToPx(float dp, DisplayMetrics metrics) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, metrics);
    }
}
