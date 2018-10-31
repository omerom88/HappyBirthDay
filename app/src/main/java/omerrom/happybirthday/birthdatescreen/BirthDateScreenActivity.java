package omerrom.happybirthday.birthdatescreen;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import io.reactivex.disposables.CompositeDisposable;
import omerrom.happybirthday.R;
import omerrom.happybirthday.application.HappyBirthday;
import omerrom.happybirthday.birthdatescreen.injection.BirthDateScreenModule;


/**
 * Created by omerom88 on 25-Oct-18
 */
public class BirthDateScreenActivity extends AppCompatActivity implements BirthDateScreenContract.View{

    private CompositeDisposable compositeDisposable;

    private int[] ageIconsList = new int[]{R.drawable.n0, R.drawable.n1, R.drawable.n2, R.drawable.n3,
            R.drawable.n4,  R.drawable.n5, R.drawable.n6, R.drawable.n7, R.drawable.n8,
            R.drawable.n9, R.drawable.n10, R.drawable.n11};

    @Inject
    public BirthDateScreenContract.UserActionsListener presenter;

    @BindView(R.id.upper_text)
    TextView userNameTV;

    @BindView(R.id.lower_text)
    TextView userAgeTV;

    @BindView(R.id.age_number)
    ImageView userAgeIV;

    @BindView(R.id.layout_background)
    ImageView layoutBackground;

    @BindView(R.id.profile_image)
    CircleImageView userPhotoIV;

    @OnClick(R.id.close_screen)
    public void closeScreen(){
        finish();
    }

    /**
     * on create for the activity
     * @param savedInstanceState activity bundle
     */
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        HappyBirthday.getAppComponent().plus(new BirthDateScreenModule(this)).inject(this);
        setContentView(R.layout.birthday_screen_layout);
        ButterKnife.bind(this);
        initVars();
    }

    /**
     * on destroy for the activity
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        if (compositeDisposable != null) {
            compositeDisposable.dispose();
        }
    }

    /**
     * user name setter
     * @param babyName name to be set
     */
    @Override
    public void setUserName(String babyName) {
        String line = getString(R.string.today) + " " + babyName + " " + getString(R.string.is);
        userNameTV.setText(line);
    }

    /**
     * empty drawable array getter
     * @return empty drawable array
     */
    @Override
    public Drawable[] getEmptyDrawableList() {
        return new Drawable[0];
    }

    /**
     * user background setter
     * @param userBackground to be set
     */
    @Override
    public void setTheBackground(Drawable userBackground) {
        layoutBackground.setImageDrawable(userBackground);
    }

    /**
     * user round face setter
     * @param userRoundFace to be set
     */
    @Override
    public void setTheRoundFace(Drawable userRoundFace) {
        userPhotoIV.setImageDrawable(userRoundFace);
    }

    /**
     * user age in months setter
     * @param month to be set
     */
    @Override
    public void setUserAgeInMonth(int month) {
        userAgeTV.setText(getString(R.string.months_old));
        userAgeIV.setImageResource(ageIconsList[month]);
    }

    /**
     * user age in years setter
     * @param year to be set
     */
    @Override
    public void setUserAgeInYears(int year) {
        userAgeTV.setText(getString(R.string.years_old));
        userAgeIV.setImageResource(ageIconsList[year]);
    }

    /**
     * user photo setter
     * @param babyPhoto to be set
     */
    @Override
    public void setUserPhoto(String babyPhoto) {
        changeUserImage(babyPhoto);
    }

    private void initVars() {
        compositeDisposable = new CompositeDisposable();
        presenter.getUserProfile();
    }

    private void changeUserImage(String selectedImage) {
        if (selectedImage != null ) {
            if (selectedImage.isEmpty()){
                presenter.setRoundFace();
            }
            else{
                setUserPictureBackground();
                showImageWithGlide(selectedImage);
            }
        }
    }

    private void setUserPictureBackground() {
        userPhotoIV.setBackgroundColor(Color.TRANSPARENT);
    }

    private void showImageWithGlide(String selectedImage) {
        Glide.with(this)
                .load(selectedImage)
                .apply(new RequestOptions()
                        .dontAnimate()
                        .diskCacheStrategy(DiskCacheStrategy.ALL))
                .into(userPhotoIV);
    }
}
