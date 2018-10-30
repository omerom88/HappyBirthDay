package omerrom.happybirthday.data;
import android.graphics.drawable.Drawable;
import java.util.Random;


/**
 * Created by omerom88 on 25-Oct-18
 */
public class UserProfile {

    private static final int NUM__OF_BACKGROUNDS = 3;

    private static final int AGE_FORMAT = 2;

    private static final int INIT = 0;

    private String userName;

    private int userYear;

    private int userMonth;

    private int userDay;

    private Drawable userBackground;

    private Drawable roundFace;

    private Drawable[] userBackgroundsList;

    private Drawable[] userRoundFaceList;

    private int[] userAge;

    private String userPicture;

    private static UserProfile userProfile;

    /**
     * get instance of the singleton class
     * @param backList drawable background list
     * @param roundFaceList drawable round face list
     * @return UserProfile instance
     */
    public static UserProfile getInstance(Drawable[] backList, Drawable[] roundFaceList){
        if (userProfile == null){
            userProfile = new UserProfile(backList, roundFaceList);
        }
        return userProfile;
    }

    /**
     * User profile constructor
     * @param backList drawable background list
     * @param roundFaceList drawable round face list
     */
    private UserProfile(Drawable[] backList, Drawable[] roundFaceList){
        this.userName = "";
        this.userAge = new int[AGE_FORMAT];
        this.userBackgroundsList = backList;
        this.userRoundFaceList = roundFaceList;
        this.userPicture = "";
        this.userYear = INIT;
        this.userMonth = INIT;
        this.userDay = INIT;
    }

    /**
     * choose random background
     */
    public void chooseBackground() {
        Random rand = new Random();
        int randomId = rand.nextInt(NUM__OF_BACKGROUNDS);
        this.userBackground = userBackgroundsList[randomId];
        this.roundFace = userRoundFaceList[randomId];

    }

    /**
     * user background getter
     * @return user background
     */
    public Drawable getUserBackground() {
        return userBackground;
    }

    /**
     * user round face getter
     * @return user round face
     */
    public Drawable getUserRoundFace() {
        return roundFace;
    }

    /**
     * user name getter
     * @return user name
     */
    public String getUserName() {
        return userName;
    }

    /**
     * user name setter
     * @param userName name to be set
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * user age getter
     * @return user age
     */
    public int[] getUserAge() {
        return userAge;
    }

    /**
     * user age setter
     * @param userAge to be set
     */
    public void setUserAge(int[] userAge) {
        this.userAge = userAge;
    }

    /**
     * user picture getter
     * @return user picture
     */
    public String getUserPicture() {
        return userPicture;
    }

    /**
     * user picture setter
     * @param userPicture to be set
     */
    public void setUserPicture(String userPicture) {
        this.userPicture = userPicture;
    }

    /**
     * user year getter
     * @return user year
     */
    public int getUserYear() {
        return userYear;
    }

    /**
     * user year setter
     * @param userYear to be set
     */
    public void setUserYear(int userYear) {
        this.userYear = userYear;
    }

    /**
     * user month getter
     * @return user month
     */
    public int getUserMonth() {
        return userMonth;
    }

    /**
     * user month setter
     * @param userMonth to be set
     */
    public void setUserMonth(int userMonth) {
        this.userMonth = userMonth;
    }

    /**
     * user day getter
     * @return user day
     */
    public int getUserDay() {
        return userDay;
    }

    /**
     * user day setter
     * @param userDay to be set
     */
    public void setUserDay(int userDay) {
        this.userDay = userDay;
    }
}
