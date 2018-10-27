package omerrom.happybirthday.loginActivity;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.inject.Inject;

import io.reactivex.schedulers.Schedulers;
import omerrom.happybirthday.R;
import omerrom.happybirthday.data.UserProfile;
import omerrom.happybirthday.utils.CustomDatePicker;

public class LoginScreenPresenter implements LoginScreenContract.UserActionsListener{

    private static final int NO_INT = -1;

    private LoginScreenContract.View view;

    private UserProfile userProfile;

    private CustomDatePicker customDatePicker;

    private SharedPreferences preferences;

    private static final int LOWER_MONTH = 0;

    private static final int UPPER_MONTH = 12;

    private boolean dateHasBeenChosen = false;

    private boolean nameHasBeenChosen = false;


    @Inject
    LoginScreenPresenter(LoginScreenContract.View view) {
        this.view = view;
        this.userProfile = UserProfile.getInstance(view.createBackRoundList());
        this.customDatePicker = CustomDatePicker.getInstance();
    }


    @Override
    public void finishTypingEvent() {
        nameHasBeenChosen = true;
        userProfile.setUserName(view.getUserName());
    }


    @Override
    public void attach() {
        userProfile.chooseBackground();
        subscribeToDateChange();
        subscribeToDateNotValid();

    }


    private void subscribeToDateNotValid() {
        view.addDisposable(customDatePicker.getDateNotValidObservable()
                .doOnNext(this::dateNotValid)
                .subscribe()

        );
    }


    private void dateNotValid(boolean notValid) {
        view.openDateNotValidToast();
    }


    private void subscribeToDateChange() {
        view.addDisposable(customDatePicker.getDateChangeObservable()
                .doOnNext(this::changeDateText)
                .doOnNext(__ -> dateHasBeenChosen = true)
                .observeOn(Schedulers.computation())
                .doOnNext(this::calculateUserAge)
                .subscribe()
        );
    }


    @Override
    public void changeDateText(Date date) {
        userProfile.setUserYear(date.getYear());
        userProfile.setUserMonth(date.getMonth());
        userProfile.setUserDay(date.getDay());
        view.setDateText(parsDateToString(date));
    }


    @Override
    public boolean birthDayScreenAvailable() {
        return nameHasBeenChosen && dateHasBeenChosen;
    }


    @Override
    public UserProfile getUserProfile() {
        return userProfile;
    }


    @Override
    public void saveUserDetails() {
        preferences = PreferenceManager.getDefaultSharedPreferences(view.getContext());
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(view.getStringRes(R.string.def_name),userProfile.getUserName());
        editor.putInt(view.getStringRes(R.string.def_year), userProfile.getUserYear());
        editor.putInt(view.getStringRes(R.string.def_month), userProfile.getUserMonth());
        editor.putInt(view.getStringRes(R.string.def_day), userProfile.getUserDay());
        if (userProfile.getUserPicture() != null) {
            editor.putString(view.getStringRes(R.string.def_url), userProfile.getUserPicture());
        }
        editor.apply();
    }


    @Override
    public void setUserDetails() {
        preferences = PreferenceManager.getDefaultSharedPreferences(view.getContext());
        view.setNameText(getPrefsName());
        changeDateText(getPrefsDate());
        view.setUserPhoto(getPrefPhoto());
    }

    private String getPrefPhoto() {
        String imageUriString = preferences.getString(view.getStringRes(R.string.def_url), null);
        if (imageUriString != null){
            return imageUriString;
        }
        return null;
    }

    private Date getPrefsDate() {
        int year = preferences.getInt(view.getStringRes(R.string.def_year), NO_INT);
        int month = preferences.getInt(view.getStringRes(R.string.def_month), NO_INT);
        int day = preferences.getInt(view.getStringRes(R.string.def_day), NO_INT);
        if (year != NO_INT && month != NO_INT && day != NO_INT){
            return new Date(year,month,day);
        }
        return new Date();
    }

    @NonNull
    private String getPrefsName() {
        String name = preferences.getString(view.getStringRes(R.string.def_name), view.getStringRes(R.string.no_string));
        if (!name.equalsIgnoreCase(view.getStringRes(R.string.no_string))){
            return name;
        }
        return view.getStringRes(R.string.def_name);
    }


    private void calculateUserAge(Date date) {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR) - date.getYear();
        int month = getUserAgeInMonths(calendar, date);
        userProfile.setUserAge(new int[]{year, month});
    }


    private int getUserAgeInMonths(Calendar calendar, Date date) {
        int diff = calendar.get(Calendar.MONTH) - date.getMonth();
        return diff >= LOWER_MONTH ? diff : diff + UPPER_MONTH;
    }


    private String parsDateToString(Date date) {
        DateFormat df = android.text.format.DateFormat.getDateFormat(view.getContext());
        return df.format(date);
    }
}
