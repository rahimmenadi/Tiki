package com.example.ardino_administration;

import android.app.DatePickerDialog;
import android.content.Context;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.TextView;

import java.util.Calendar;

public class ShowDate {
    public DatePickerDialog datePickerDialog;
    private TextView tv_date;
    private Context context;
    private boolean max;

    public ShowDate(TextView tv_date, Context context, boolean max) {
        this.tv_date = tv_date;
        this.context = context;
        this.max = max;
    }

    public static String getTodayDate() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        month = month + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        return makeDateString(year, month, day);
    }

    public void initdate() {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                String date = makeDateString(year, month, day);
                tv_date.setText(date);
            }
        };

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

       this.datePickerDialog = new DatePickerDialog(context, dateSetListener, year, month, day);
        if (max) {
            datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
        }else {
            datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
        }
    }

    public static String makeDateString(int year, int month, int day) {
        return month + "/" + day + "/" + year;

    }
}
