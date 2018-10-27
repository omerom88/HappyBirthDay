package omerrom.happybirthday.data;
import android.graphics.drawable.Drawable;

import java.util.Random;

public class UserProfile {

    private static final int NUM__OF_BACKGROUNDS = 2;

    private static final int AGE_FORMAT = 2;

    private static final int INIT = 0;

    private String userName;

    private int userYear;

    private int userMonth;

    private int userDay;

    private Drawable userBackground;

    private Drawable[] userBackgroundsList;

    private int[] userAge;

    private String userPicture;

    private static UserProfile userProfile;

    private UserProfile(Drawable[] backList){
        this.userName = "";
        this.userAge = new int[AGE_FORMAT];
        this.userBackgroundsList = backList;
        this.userPicture = "";
        this.userYear = INIT;
        this.userMonth = INIT;
        this.userDay = INIT;
    }


    public static UserProfile getInstance(Drawable[] backList){
        if (userProfile == null){
            userProfile = new UserProfile(backList);
        }
        return userProfile;
    }


    public void chooseBackground() {
        Random rand = new Random();
        int randomId = rand.nextInt(NUM__OF_BACKGROUNDS);
        this.userBackground = userBackgroundsList[randomId];

    }

    public Drawable getUserBackground() {
        return userBackground;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int[] getUserAge() {
        return userAge;
    }

    public void setUserAge(int[] userAge) {
        this.userAge = userAge;
    }

    public String getUserPicture() {
        return userPicture;
    }

    public void setUserPicture(String userPicture) {
        this.userPicture = userPicture;
    }

    public int getUserYear() {
        return userYear;
    }

    public void setUserYear(int userYear) {
        this.userYear = userYear;
    }

    public int getUserMonth() {
        return userMonth;
    }

    public void setUserMonth(int userMonth) {
        this.userMonth = userMonth;
    }

    public int getUserDay() {
        return userDay;
    }

    public void setUserDay(int userDay) {
        this.userDay = userDay;
    }
}
