package omerrom.happybirthday.birthdatescreen;

import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class MainScreenPresenterTest {

    private BirthDateScreenPresenter loginPresenter;

    private final BirthDateScreenContract.View view = mock(BirthDateScreenContract.View.class);

    @Before
    public void initTest(){
        loginPresenter = new BirthDateScreenPresenter(view);
    }

    @Test
    public void checkCalcAgeToShow(){
        int[] babyAge = new int[]{1,5};
        loginPresenter.calcAgeToShow(babyAge);
        verify(view).setUserAgeInYears(babyAge[0]);

        babyAge = new int[]{0,2};
        loginPresenter.calcAgeToShow(babyAge);
        verify(view).setUserAgeInMonth(babyAge[1]);


        babyAge = new int[]{0,0};
        loginPresenter.calcAgeToShow(babyAge);
        verify(view).setUserAgeInMonth(babyAge[1]);
    }
}