package com.stuty.studymatching.FRAGMENT;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import android.widget.DatePicker;


import com.stuty.studymatching.R;

import java.util.Calendar;


public class CustomDatePicker extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    private DialogListener dialogListener;

    public CustomDatePicker newInstance() {
        return new CustomDatePicker();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().getWindow().setBackgroundDrawableResource(R.color.transparent);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        getActivity().getWindow().setBackgroundDrawableResource(R.color.transparent);
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), R.style.MyDatePickerStyle,this, year, month, day);
        return datePickerDialog;

    }


    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
        dialogListener.result(year, month, day);
    }

    public interface DialogListener {
        void result(int year, int month, int day);
    }

    public void setListener(DialogListener dialogListener) {
        this.dialogListener = dialogListener;
    }
}