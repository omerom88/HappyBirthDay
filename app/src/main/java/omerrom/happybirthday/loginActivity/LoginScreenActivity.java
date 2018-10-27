package omerrom.happybirthday.loginActivity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import omerrom.happybirthday.R;
import omerrom.happybirthday.application.HappyBirthday;
import omerrom.happybirthday.loginActivity.injection.LoginScreenModule;

public class LoginScreenActivity extends AppCompatActivity implements LoginScreenContract.View{

    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Inject
    public LoginScreenContract.UserActionsListener presenter;


    @BindView(R.id.birth_date)
    Button birthDate;


    @BindView(R.id.name_tv)
    EditText userName;


    @BindView(R.id.user_picture)
    ImageView userPicture;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        HappyBirthday.getAppComponent().plus(new LoginScreenModule(this)).inject(this);
        setContentView(R.layout.login_screen_layout);
        ButterKnife.bind(this);
        presenter.attach();
    }


    @Override
    public void onStart() {
        super.onStart();
    }


    @Override
    public void onStop() {
        super.onStop();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        compositeDisposable.clear();
        if (compositeDisposable != null) {
            compositeDisposable.dispose();
        }
    }


    @OnClick(R.id.show_birthday_button)
    public void showBirthdayScreen() {
    }


    @OnClick(R.id.birth_date)
    public void editBirthDate() {
    }


    @OnClick(R.id.name_tv)
    public void editName() {
    }


    @OnClick(R.id.user_picture)
    public void editPicture() {
    }

    @Override
    public void addDisposable(Disposable disposable) {
        compositeDisposable.add(disposable);
    }

    @Override
    public Context getContext() {
        return getApplicationContext();
    }
}
