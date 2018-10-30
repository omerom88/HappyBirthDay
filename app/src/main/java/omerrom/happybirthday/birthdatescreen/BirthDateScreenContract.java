package omerrom.happybirthday.birthdatescreen;

import android.graphics.drawable.Drawable;

/**
 * Created by omerom88 on 25-Oct-18
 */
public interface BirthDateScreenContract {

    interface View {
        void setUserName(String babyName);

        Drawable[] getEmptyDrawableList();

        void setTheBackground(Drawable userBackground);

        void setTheRoundFace(Drawable userRoundFace);

        void setUserAgeInMonth(int month);

        void setUserAgeInYears(int year);

        void setUserPhoto(String babyPhoto);
    }

    interface UserActionsListener{
        void getUserProfile();

        void setRoundFace();
    }

}
