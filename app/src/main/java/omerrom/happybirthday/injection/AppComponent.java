package omerrom.happybirthday.injection;

import javax.inject.Singleton;

import dagger.Component;
import omerrom.happybirthday.application.MainActivity;
import omerrom.happybirthday.loginscreen.injection.LoginScreenComponent;
import omerrom.happybirthday.loginscreen.injection.LoginScreenModule;
import omerrom.happybirthday.birthdatescreen.injection.BirthDateScreenComponent;
import omerrom.happybirthday.birthdatescreen.injection.BirthDateScreenModule;

/**
 * Created by omerom88 on 23-Oct-18
 */
@Singleton
@Component(modules = {AppModule.class, HappyBirthdayModule.class})
public interface AppComponent {

    void inject(MainActivity target);

    LoginScreenComponent plus(LoginScreenModule loginScreenModule);

    BirthDateScreenComponent plus(BirthDateScreenModule birthDateScreenModule);

}
