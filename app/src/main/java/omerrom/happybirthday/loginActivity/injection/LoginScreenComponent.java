package omerrom.happybirthday.loginActivity.injection;

import dagger.Subcomponent;
import omerrom.happybirthday.loginActivity.LoginScreenActivity;

/**
 * Created by omerom88 on 13-Sep-18
 */

@Subcomponent(modules = LoginScreenModule.class)
public interface LoginScreenComponent {

    void inject(LoginScreenActivity loginScreenActivity);

}
