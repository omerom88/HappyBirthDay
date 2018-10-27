package omerrom.happybirthday.utils;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;

import java.util.Calendar;
import java.util.Date;

import butterknife.ButterKnife;
import io.reactivex.subjects.PublishSubject;


public class CustomDatePicker extends DialogFragment {

    private int year;

    private int month;

    private int day;

    private Calendar calendar = Calendar.getInstance();

    private PublishSubject<Date> dateChangeObservable = PublishSubject.create();

    private PublishSubject<Boolean> dateNotValidObservable = PublishSubject.create();

    private static CustomDatePicker myDatePicker;

    public CustomDatePicker(){
        year = getCurrentYear();
        month = getCurrentMonth();
        day = getCurrentDay();
    }

    private int getCurrentDay() {
        return calendar.get(Calendar.DAY_OF_MONTH);
    }

    private int getCurrentMonth() {
        return calendar.get(Calendar.MONTH);
    }

    private int getCurrentYear() {
        return calendar.get(Calendar.YEAR);
    }

    public static CustomDatePicker getInstance(){
        if (myDatePicker == null){
            myDatePicker =  new CustomDatePicker();
        }
        return myDatePicker;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        super.onCreateDialog(savedInstanceState);
        ButterKnife.bind(getActivity());
        return new DatePickerDialog(getActivity(), dateSetListener, year, month, day);
    }


    public int getYear() {
        return year;
    }

    public int getMonth() {
        return month;
    }

    public int getDay() {
        return day;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public void setYear(int year) {
        this.year = year;
    }


    private DatePickerDialog.OnDateSetListener dateSetListener =
            (view, year, month, day) -> {
                if (inputDateIsValid(year, month, day)){
                    setYear(year);
                    setMonth(month);
                    setDay(day);
                    dateHasChangeEvent();
                } else {
                    sendDateNotValidEvent();
                }
            };

    private boolean inputDateIsValid(int year, int month, int day) {
        return year <= getCurrentYear() && month <= getCurrentMonth() && day <= getCurrentDay();
    }

    private void sendDateNotValidEvent() {
        dateNotValidObservable.onNext(true);
    }

    public PublishSubject<Boolean> getDateNotValidObservable() {
        return dateNotValidObservable;
    }


    private void dateHasChangeEvent() {
        dateChangeObservable.onNext(new Date(year, month, day));
    }


    public PublishSubject<Date> getDateChangeObservable() {
        return dateChangeObservable;
    }
}
