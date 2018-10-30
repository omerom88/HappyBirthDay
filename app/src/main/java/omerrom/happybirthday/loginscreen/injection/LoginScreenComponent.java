package omerrom.happybirthday.loginscreen.injection;

import dagger.Subcomponent;
import omerrom.happybirthday.loginscreen.LoginScreenActivity;

/**
 * Created by omerom88 on 23-Oct-18
 */
@Subcomponent(modules = LoginScreenModule.class)
public interface LoginScreenComponent {

    void inject(LoginScreenActivity loginScreenActivity);

}
