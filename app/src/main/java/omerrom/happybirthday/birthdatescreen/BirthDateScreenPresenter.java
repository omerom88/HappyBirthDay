package omerrom.happybirthday.birthdatescreen;

import javax.inject.Inject;

import omerrom.happybirthday.data.UserProfile;

/**
 * Created by omerom88 on 25-Oct-18
 */
public class BirthDateScreenPresenter implements BirthDateScreenContract.UserActionsListener {

    private static final int YEAR_INDEX = 0;

    private static final int MONTH_INDEX = 1;

    private static final int NO_YEARS = 0;

    private BirthDateScreenContract.View view;

    private UserProfile userProfile;

    /**
     * birth date screen presenter constructor
     * @param view birth date screen activity
     */
    @Inject
    BirthDateScreenPresenter(BirthDateScreenContract.View view) {
        this.view = view;
        this.userProfile = UserProfile.getInstance(view.getEmptyDrawableList(), view.getEmptyDrawableList());
    }

    /**
     * get user profile data
     */
    @Override
    public void getUserProfile() {
        setName();
        setAge();
        setRoundFace();
        setPhoto();
        setBackground();
    }

    /**
     * set round face to user picture
     */
    @Override
    public void setRoundFace() {
        view.setTheRoundFace(userProfile.getUserRoundFace());
    }

    /**
     * calculate age to show (less then 1 year show in month)
     * @param babyAge user array age - [0] years, [1] month
     */
    public void calcAgeToShow(int[] babyAge) {
        if (babyAge[YEAR_INDEX] == NO_YEARS){
            view.setUserAgeInMonth(babyAge[MONTH_INDEX]);
        } else {
            view.setUserAgeInYears(babyAge[YEAR_INDEX]);
        }
    }

    private void setBackground() {
        view.setTheBackground(userProfile.getUserBackground());
    }

    private void setPhoto() {
        view.setUserPhoto(userProfile.getUserPicture());
    }

    private void setAge() {
        calcAgeToShow(userProfile.getUserAge());
    }

    private void setName() {
        view.setUserName(userProfile.getUserName());
    }
}
