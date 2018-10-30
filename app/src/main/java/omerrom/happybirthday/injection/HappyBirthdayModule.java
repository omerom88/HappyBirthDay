package omerrom.happybirthday.injection;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;

/**
 * Created by omerom88 on 23-Oct-18
 */
@Module
public class HappyBirthdayModule {

    @Provides
    @Singleton
    public Scheduler provideScheduler() {
        return AndroidSchedulers.mainThread();
    }


}
