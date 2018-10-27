package omerrom.happybirthday.loginActivity.injection;

import dagger.Module;
import dagger.Provides;
import omerrom.happybirthday.loginActivity.LoginScreenContract;
import omerrom.happybirthday.loginActivity.LoginScreenPresenter;

/**
 * Created by omerom88 on 13-Sep-18
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
