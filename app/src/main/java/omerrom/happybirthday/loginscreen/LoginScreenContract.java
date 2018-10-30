package omerrom.happybirthday.loginscreen;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import java.util.Calendar;
import io.reactivex.disposables.Disposable;
import omerrom.happybirthday.data.UserProfile;
import omerrom.happybirthday.utils.CustomDatePicker;


/**
 * Created by omerom88 on 23-Oct-18
 */
public interface LoginScreenContract {

    interface View {

        String getUserName();

        Drawable[] createBackgroundList();

        Drawable[] createRoundFaceList();

        void addDisposable(Disposable disposable);

        void setDateText(Calendar newDate);

        Context getContext();

        void openDateNotValidToast();

        void setNameText(String prefsName);

        String getStringRes(int res);

        void setUserPhoto(String photoUrl);

        void galleryIntent();

        void cameraIntent();

        void saveUserPhotoToPrefs();

        void saveNameChoseToPrefs();

        void saveDateChoseToPrefs();

        void saveUserNameToPrefs();

        void saveUserDateToPrefs();

        SharedPreferences getDefaultSharedPreferences();

        int getManagerPermissionGranted();

    }

    interface UserActionsListener{

        void finishTypingEvent();

        void attach();

        void changeDateText(Calendar prefsDate);

        boolean birthDayScreenAvailable();

        UserProfile getUserProfile();

        void setUserDetails();

        boolean getNameHasBeenChosen();

        boolean getDateHasBeenChosen();

        CustomDatePicker getCustomDatePicker();

        void checkPermissionResults(int requestCode, int[] grantResults);

        void setUserChosenTask(String string);
    }

}
