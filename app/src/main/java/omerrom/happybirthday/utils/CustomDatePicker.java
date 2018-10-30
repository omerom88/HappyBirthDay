package omerrom.happybirthday.utils;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import java.util.Calendar;
import butterknife.ButterKnife;
import io.reactivex.subjects.PublishSubject;


/**
 * Created by omerom88 on 25-Oct-18
 */
public class CustomDatePicker extends DialogFragment {

    private int year;

    private int month;

    private int day;

    private Calendar calendar = Calendar.getInstance();

    private PublishSubject<Calendar> dateChangeObservable = PublishSubject.create();

    private PublishSubject<Boolean> dateNotValidObservable = PublishSubject.create();

    private static CustomDatePicker myDatePicker;

    /**
     * CustomDatePicker constructor
     */
    public CustomDatePicker(){
        year = getCurrentYear();
        month = getCurrentMonth();
        day = getCurrentDay();
    }

    /**
     * get instance of the singleton class
     * @return CustomDatePicker instance
     */
    public static CustomDatePicker getInstance(){
        if (myDatePicker == null){
            myDatePicker =  new CustomDatePicker();
        }
        return myDatePicker;
    }

    /**
     * override Dialog onCreateDialog function
     * @param savedInstanceState fragment dialog
     * @return new instance of DatePickerDialog
     */
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        super.onCreateDialog(savedInstanceState);
        ButterKnife.bind(getActivity());
        return new DatePickerDialog(getActivity(), dateSetListener, getYear(), getMonth(),getDay());
    }

    /**
     * year getter
     * @return year field
     */
    public int getYear() {
        return year;
    }

    /**
     * month getter
     * @return month field
     */
    public int getMonth() {
        return month;
    }

    /**
     * day getter
     * @return day field
     */
    public int getDay() {
        return day;
    }

    /**
     * year setter
     * @param year to be set
     */
    public void setYear(int year) {
        this.year = year;
    }

    /**
     * month setter
     * @param month to be set
     */
    public void setMonth(int month) {
        this.month = month;
    }

    /**
     * day setter
     * @param day to be set
     */
    public void setDay(int day) {
        this.day = day;
    }

    /**
     * date validation observable getter
     * @return date validation observable
     */
    public PublishSubject<Boolean> getDateNotValidObservable() {
        return dateNotValidObservable;
    }

    /**
     * date changed observable getter
     * @return date changed observable
     */
    public PublishSubject<Calendar> getDateChangeObservable() {
        return dateChangeObservable;
    }

    /**
     * check input date validation
     * @param year input year
     * @param month input month (need to add 1 by default)
     * @param day input day
     * @return true if valid
     */
    public boolean inputDateIsValid(int year, int month, int day) {
        if (year > getCurrentYear() || getCurrentYear() > year + 11) {
            return false;
        }
        if (year == getCurrentYear()){
            return !checkSameYear(month, day);
        }
        return true;
    }

    private boolean checkSameYear(int month, int day) {
        if (month > getCurrentMonth()){
            return true;
        }
        if (month == getCurrentMonth()){
            return checkSameMonth(day);
        }
        return false;
    }

    private boolean checkSameMonth(int day) {
        return day > getCurrentDay();
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

    private void sendDateNotValidEvent() {
        dateNotValidObservable.onNext(true);
    }

    private void dateHasChangeEvent() {
        dateChangeObservable.onNext(getCalenderDate());
    }

    private Calendar getCalenderDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);
        return calendar;
    }

}
