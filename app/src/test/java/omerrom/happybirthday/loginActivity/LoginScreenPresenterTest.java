package omerrom.happybirthday.loginActivity;

import android.graphics.drawable.Drawable;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Calendar;

import omerrom.happybirthday.data.UserProfile;
import omerrom.happybirthday.loginscreen.LoginScreenContract;
import omerrom.happybirthday.loginscreen.LoginScreenPresenter;

import static org.mockito.Mockito.mock;

@RunWith(MockitoJUnitRunner.class)
public class LoginScreenPresenterTest {

    private final LoginScreenContract.View view = mock(LoginScreenContract.View.class);

    private Drawable[] mockDrawablesList = new Drawable[0];

    private LoginScreenPresenter loginPresenter;

    @Before
    public void initTest(){
        loginPresenter = new LoginScreenPresenter(view);
    }

    @Test
    public void checkUserProfileCreation(){
        UserProfile userProfile = UserProfile.getInstance(mockDrawablesList, mockDrawablesList);
        Assert.assertEquals(userProfile.getUserName(), "");
        userProfile.setUserName("nanit");
        Assert.assertEquals(userProfile.getUserName(), "nanit");
        userProfile.setUserName("omer");
        userProfile = createAnotherInstance();
        Assert.assertEquals(userProfile.getUserName(), "omer");

    }

    private UserProfile createAnotherInstance() {
        return UserProfile.getInstance(mockDrawablesList, mockDrawablesList);
    }

    @Test
    public void testUserAgeCalculation(){
        UserProfile userProfile = UserProfile.getInstance(mockDrawablesList, mockDrawablesList);
        Calendar date = Calendar.getInstance();
        date.set(2018, 9, 27);
        loginPresenter.calculateUserAge(date);
        int[] noAge = new int[]{0,0};
        Assert.assertEquals(noAge[0], userProfile.getUserAge()[0]);
        Assert.assertEquals(noAge[0], userProfile.getUserAge()[1]);
        date.set(2017, 9, 29);
        loginPresenter.calculateUserAge(date);
        int[] oneYear = new int[]{1,0};
        Assert.assertEquals(oneYear[0], userProfile.getUserAge()[0]);
        Assert.assertEquals(oneYear[1], userProfile.getUserAge()[1]);
        date.set(2016, 12, 29);
        loginPresenter.calculateUserAge(date);
        int[] oneYearFewMonth = new int[]{1,9};
        Assert.assertEquals(oneYearFewMonth[0], userProfile.getUserAge()[0]);
        Assert.assertEquals(oneYearFewMonth[1], userProfile.getUserAge()[1]);
    }

}