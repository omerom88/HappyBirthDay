package omerrom.happybirthday.utils;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Calendar;

public class CustomDatePickerTest {

    private CustomDatePicker customDatePicker;
    private Calendar calendar;

    @Before
    public void initTest(){
        customDatePicker = CustomDatePicker.getInstance();
        calendar = Calendar.getInstance();
    }

    @Test
    public void checkInputDateValidation(){
        NotValidCheck();
        ValidCheck();
    }

    private void ValidCheck() {
        calendar.set(2017, 8, 9);
        checkTrueValidation();
        calendar.set(2018, 8, 12);
        checkTrueValidation();
        calendar.set(2018, 9, 27);
        checkTrueValidation();
    }

    private void checkTrueValidation() {
        Assert.assertTrue(customDatePicker.inputDateIsValid(calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)));
    }

    private void NotValidCheck() {
        calendar.set(2019, 9, 9);
        checkFalseValidation();
        calendar.set(2018, 12, 12);
        checkFalseValidation();
        calendar.set(2018, 11, 30);
        checkFalseValidation();
    }

    private void checkFalseValidation() {
        Assert.assertFalse(customDatePicker.inputDateIsValid(calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)));
    }

}