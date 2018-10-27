package omerrom.happybirthday.loginActivity;

import android.content.Context;

import io.reactivex.disposables.Disposable;

public interface LoginScreenContract {

    interface View {

        void addDisposable(Disposable disposable);

        Context getContext();
    }

    interface UserActionsListener{

        void attach();
    }

}
