package omerrom.happybirthday.birthdatescreen.injection;

import dagger.Subcomponent;
import omerrom.happybirthday.birthdatescreen.BirthDateScreenActivity;

/**
 * Created by omerom88 on 25-Oct-18
 */
@Subcomponent(modules = BirthDateScreenModule.class)
public interface BirthDateScreenComponent {

    void inject(BirthDateScreenActivity birthDateScreenActivity);

}
