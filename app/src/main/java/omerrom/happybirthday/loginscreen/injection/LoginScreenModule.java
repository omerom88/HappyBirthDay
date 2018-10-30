package omerrom.happybirthday.loginscreen.injection;

import dagger.Module;
import dagger.Provides;
import omerrom.happybirthday.loginscreen.LoginScreenContract;
import omerrom.happybirthday.loginscreen.LoginScreenPresenter;

/**
 * Created by omerom88 on 23-Oct-18
 */
@Module
public class LoginScreenModule {

    private final LoginScreenContract.View view;

    public LoginScreenModule(LoginScreenContract.View view) {
        this.view = view;
    }

    @Provides
    public LoginScreenContract.UserActionsListener provideLoginPresenter(LoginScreenPresenter presenter){
        return presenter;
    }

    @Provides
    public LoginScreenContract.View providesLoginView(){
        return view;
    }

}
