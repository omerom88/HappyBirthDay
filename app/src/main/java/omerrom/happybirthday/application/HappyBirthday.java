package omerrom.happybirthday.application;


import android.app.Application;
import io.reactivex.annotations.NonNull;
import omerrom.happybirthday.injection.AppComponent;
import omerrom.happybirthday.injection.AppModule;
import omerrom.happybirthday.injection.DaggerAppComponent;
import omerrom.happybirthday.injection.HappyBirthdayModule;


/**
 * Created by omerom88 on 23-Oct-18
 */
public class HappyBirthday extends Application {

    private static AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        appComponent = initDagger();
    }

    @NonNull
    private AppComponent initDagger() {
        return DaggerAppComponent.builder()
                                 .appModule(new AppModule(this))
                                 .happyBirthdayModule(new HappyBirthdayModule())
                                 .build();
    }

    public static AppComponent getAppComponent() {
        return appComponent;
    }
}
