package omerrom.happybirthday.loginActivity;

import android.content.Context;
import android.graphics.drawable.Drawable;

import java.util.Date;

import io.reactivex.disposables.Disposable;
import omerrom.happybirthday.data.UserProfile;

public interface LoginScreenContract {

    interface View {

        String getUserName();

        Drawable[] createBackRoundList();

        void addDisposable(Disposable disposable);

        void setDateText(String date);

        Context getContext();

        void openDateNotValidToast();

        void setNameText(String prefsName);

        String getStringRes(int res);

        void setUserPhoto(String photoUrl);
    }

    interface UserActionsListener{

        void finishTypingEvent();

        void attach();

        void changeDateText(Date date);

        boolean birthDayScreenAvailable();

        UserProfile getUserProfile();

        void saveUserDetails();

        void setUserDetails();
    }

}
