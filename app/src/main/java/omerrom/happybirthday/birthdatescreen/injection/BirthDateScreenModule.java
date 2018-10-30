package omerrom.happybirthday.birthdatescreen.injection;

import dagger.Module;
import dagger.Provides;
import omerrom.happybirthday.birthdatescreen.BirthDateScreenContract;
import omerrom.happybirthday.birthdatescreen.BirthDateScreenPresenter;

/**
 * Created by omerom88 on 25-Oct-18
 */
@Module
public class BirthDateScreenModule {

    private final BirthDateScreenContract.View view;

    public BirthDateScreenModule(BirthDateScreenContract.View view) {
        this.view = view;
    }

    @Provides
    public BirthDateScreenContract.UserActionsListener provideMainScreenPresenter(BirthDateScreenPresenter presenter){
        return presenter;
    }

    @Provides
    public BirthDateScreenContract.View providesMainScreenView(){
        return view;
    }

}
