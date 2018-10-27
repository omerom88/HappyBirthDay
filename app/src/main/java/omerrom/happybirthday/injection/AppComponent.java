package omerrom.happybirthday.injection;

import javax.inject.Singleton;

import dagger.Component;
import omerrom.happybirthday.application.MainActivity;
import omerrom.happybirthday.loginActivity.injection.LoginScreenComponent;
import omerrom.happybirthday.loginActivity.injection.LoginScreenModule;

/**
 * Created by omerom88 on 13-Sep-18
 */
@Singleton
@Component(modules = {AppModule.class, HappyBirthdayModule.class})
public interface AppComponent {

    void inject(MainActivity target);

    LoginScreenComponent plus(LoginScreenModule loginScreenModule);

}
