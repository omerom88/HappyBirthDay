package omerrom.happybirthday.loginscreen;

import java.util.Calendar;
import javax.inject.Inject;
import io.reactivex.schedulers.Schedulers;
import omerrom.happybirthday.R;
import omerrom.happybirthday.data.UserProfile;
import omerrom.happybirthday.utils.AppPermissions;
import omerrom.happybirthday.utils.CustomDatePicker;


/**
 * Created by omerom88 on 23-Oct-18
 */
public class LoginScreenPresenter implements LoginScreenContract.UserActionsListener{

    private static final int EMPTY_LIST = 0;

    private static final int FIRST_ELEMENT = 0;

    private static final int NO_MONTH = 0;

    private static final int SAME_MONTH = 1;

    private static final int FULL_YEAR = 12;

    private static final int INC_MONTH = 1;

    private LoginScreenContract.View view;

    private UserProfile userProfile;

    private CustomDatePicker customDatePicker;

    private boolean dateHasBeenChosen = false;

    private boolean nameHasBeenChosen = false;

    private String userChosenTask;


    /**
     * login screen presenter constructor
     * @param view login screen activity
     */
    @Inject
    public LoginScreenPresenter(LoginScreenContract.View view) {
        this.view = view;
        this.userProfile = UserProfile.getInstance(view.createBackgroundList(), view.createRoundFaceList());
        this.customDatePicker = CustomDatePicker.getInstance();
    }

    /**
     * attach presenter observables
     */
    @Override
    public void attach() {
        subscribeToDateChange();
        subscribeToDateNotValid();
        chooseProfileBackground();
    }

    /**
     * user has finished typing
     */
    @Override
    public void finishTypingEvent() {
        saveNameWasEdit();
        saveNameToProfile();
        view.saveUserNameToPrefs();
    }

    /**
     * change user date text
     * @param prefsDate date to be set
     */
    @Override
    public void changeDateText(Calendar prefsDate) {
        userProfile.setUserYear(prefsDate.get(Calendar.YEAR));
        userProfile.setUserMonth(prefsDate.get(Calendar.MONTH));
        userProfile.setUserDay(prefsDate.get(Calendar.DAY_OF_MONTH));
        showDateText(prefsDate);
    }

    /**
     * check if name and date was edit to enable birth date screen
     * @return true if both was edited
     */
    @Override
    public boolean birthDayScreenAvailable() {
        return nameHasBeenChosen && dateHasBeenChosen;
    }

    /**
     * user profile getter
     * @return user profile instance
     */
    @Override
    public UserProfile getUserProfile() {
        return userProfile;
    }

    /**
     * set user details that was saved in shared prefs
     */
    @Override
    public void setUserDetails() {
        savedUserName();
        savedUserDate();
        savedUserPhoto();
        savedUserChooses();
    }

    /**
     * name is been chosen flag getter
     * @return true if name was changed
     */
    @Override
    public boolean getNameHasBeenChosen() {
        return nameHasBeenChosen;
    }

    /**
     * date is been chosen flag getter
     * @return true if date was changed
     */
    @Override
    public boolean getDateHasBeenChosen() {
        return dateHasBeenChosen;
    }

    /**
     * custom date picker getter
     * @return CustomDatePicker instance
     */
    @Override
    public CustomDatePicker getCustomDatePicker() {
        return customDatePicker;
    }

    /**
     * check system permission results
     * @param requestCode The request code passed in requestPermissions
     * @param grantResults The grant results for the corresponding permissions
     */
    @Override
    public void checkPermissionResults(int requestCode,int[] grantResults) {
        switch (requestCode) {
            case AppPermissions.MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE:
                readExternalStorage(grantResults);
        }
    }

    /**
     * user chosen task setter
     * @param chosenTask the task user chose
     */
    @Override
    public void setUserChosenTask(String chosenTask) {
        userChosenTask = chosenTask;
    }

    private void readExternalStorage(int[] grantResults) {
        if (checkGrantResults(grantResults)) {
            if (userChosenTask.equals(view.getContext().getString(R.string.take_photo)))
                view.cameraIntent();
            else if (userChosenTask.equals(view.getContext().getString(R.string.Choose_from_Library)))
                view.galleryIntent();
        }
    }

    private boolean checkGrantResults(int[] grantResult) {
        return grantResult.length > EMPTY_LIST && grantResult[FIRST_ELEMENT] == view.getManagerPermissionGranted();
    }

    public void calculateUserAge(Calendar date) {
        Calendar calendar = Calendar.getInstance();
        int year = getUserAgeInYears(date, calendar);
        int month = getUserAgeInMonths(calendar, date);
        userProfile.setUserAge(new int[]{year, month});
    }

    private void saveNameToProfile() {
        userProfile.setUserName(view.getUserName());
    }

    private void saveNameWasEdit() {
        nameHasBeenChosen = true;
        view.saveNameChoseToPrefs();
    }

    private void chooseProfileBackground() {
        userProfile.chooseBackground();
    }


    private void subscribeToDateNotValid() {
        view.addDisposable(customDatePicker.getDateNotValidObservable()
                .doOnNext(__ -> dateNotValid())
                .subscribe()

        );
    }

    private void dateNotValid() {
        view.openDateNotValidToast();
    }


    private void subscribeToDateChange() {
        view.addDisposable(customDatePicker.getDateChangeObservable()
                .doOnNext(this::changeDateText)
                .doOnNext(__ -> saveUserBirthDate())
                .observeOn(Schedulers.computation())
                .doOnNext(this::calculateUserAge)
                .subscribe()
        );
    }

    private void saveUserBirthDate() {
        view.saveUserDateToPrefs();
        saveDateWasEdit();
    }

    private void saveDateWasEdit() {
        dateHasBeenChosen = true;
        view.saveDateChoseToPrefs();

    }


    private void showDateText(Calendar calendar) {
        view.setDateText(calendar);
    }

    private void savedUserChooses() {
        nameHasBeenChosen = view.getDefaultSharedPreferences().getBoolean(view.getStringRes(R.string.def_name_chose), false);
        dateHasBeenChosen = view.getDefaultSharedPreferences().getBoolean(view.getStringRes(R.string.def_date_chose), false);
    }

    private void savedUserPhoto() {
        String userPhoto = getPrefPhoto();
        view.setUserPhoto(userPhoto);
        userProfile.setUserPicture(userPhoto);
    }

    private void savedUserDate() {
        changeDateText(getPrefsDate());
    }

    private void savedUserName() {
        String prefsName = getPrefsName();
        view.setNameText(prefsName);
        userProfile.setUserName(prefsName);
    }

    private String getPrefPhoto() {
        return view.getDefaultSharedPreferences().getString(view.getStringRes(R.string.def_url), userProfile.getUserPicture());
    }

    private Calendar getPrefsDate() {
        int year = view.getDefaultSharedPreferences().getInt(view.getStringRes(R.string.def_year), customDatePicker.getYear());
        int month = view.getDefaultSharedPreferences().getInt(view.getStringRes(R.string.def_month), customDatePicker.getMonth());
        int day = view.getDefaultSharedPreferences().getInt(view.getStringRes(R.string.def_day), customDatePicker.getDay());
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);
        return calendar;
    }

    private String getPrefsName() {
        return view.getDefaultSharedPreferences().getString(view.getStringRes(R.string.def_name),
                view.getStringRes(R.string.no_name));
    }


    private int getUserAgeInYears(Calendar date, Calendar calendar) {
        if (calendar.get(Calendar.MONTH) > date.get(Calendar.MONTH)){
            return calendar.get(Calendar.YEAR) - date.get(Calendar.YEAR);
        } else if (calendar.get(Calendar.MONTH) == date.get(Calendar.MONTH)){
            if (sameMonth(date, calendar))
                return calendar.get(Calendar.YEAR) - date.get(Calendar.YEAR);
        }
        return calendar.get(Calendar.YEAR) - date.get(Calendar.YEAR) - INC_MONTH;
    }

    private boolean sameMonth(Calendar date, Calendar calendar) {
        return calendar.get(Calendar.DAY_OF_MONTH) >= date.get(Calendar.DAY_OF_MONTH);
    }


    private int getUserAgeInMonths(Calendar calendar, Calendar date) {
        int diff = calendar.get(Calendar.MONTH) - date.get(Calendar.MONTH);
        diff = laterDay(calendar, date, diff);
        if (diff >= NO_MONTH){
            return diff;
        }
        return diff + FULL_YEAR;
    }

    private int laterDay(Calendar calendar, Calendar date, int diff) {
        if (calendar.get(Calendar.DAY_OF_MONTH) < date.get(Calendar.DAY_OF_MONTH)){
            diff -= SAME_MONTH;
        }
        return diff;
    }

}
