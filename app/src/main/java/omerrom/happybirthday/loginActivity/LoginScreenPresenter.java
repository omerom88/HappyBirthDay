package omerrom.happybirthday.loginActivity;

import javax.inject.Inject;

public class LoginScreenPresenter implements LoginScreenContract.UserActionsListener{

    private LoginScreenContract.View view;


    @Inject
    LoginScreenPresenter(LoginScreenContract.View view) {
        this.view = view;
    }


    @Override
    public void attach() {
    }
}
