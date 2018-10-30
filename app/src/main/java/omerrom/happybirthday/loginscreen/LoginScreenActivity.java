package omerrom.happybirthday.loginscreen;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Locale;
import javax.inject.Inject;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnEditorAction;
import butterknife.OnFocusChange;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import omerrom.happybirthday.R;
import omerrom.happybirthday.application.HappyBirthday;
import omerrom.happybirthday.data.UserProfile;
import omerrom.happybirthday.loginscreen.injection.LoginScreenModule;
import omerrom.happybirthday.birthdatescreen.BirthDateScreenActivity;
import omerrom.happybirthday.utils.AppPermissions;
import static android.content.pm.PackageManager.PERMISSION_GRANTED;


/**
 * Created by omerom88 on 23-Oct-18
 */
public class LoginScreenActivity extends AppCompatActivity implements LoginScreenContract.View{

    private static final int REQUEST_CAMERA = 1;

    private static final int SELECT_FILE = 2;

    private CompositeDisposable compositeDisposable;

    private SharedPreferences sharedPrefs;

    private AlertDialog.Builder AddPhotoAlertBuilder;


    @Inject
    public LoginScreenContract.UserActionsListener presenter;

    @BindView(R.id.birth_date)
    Button birthDate;

    @BindView(R.id.name_tv)
    EditText userName;

    @BindView(R.id.user_picture)
    ImageView userPicture;

    @OnClick(R.id.show_birthday_screen)
    public void showBirthdayScreen() {
        if (presenter.birthDayScreenAvailable()) {
            presenter.getUserProfile().chooseBackground();
            Intent intent = new Intent(this, BirthDateScreenActivity.class);
            startActivityForResult(intent, RESULT_OK);
        }
    }

    @OnClick(R.id.birth_date)
    public void editBirthDate() {
        presenter.getCustomDatePicker().show(this.getFragmentManager(), getString(R.string.date_picker));
    }

    @OnClick(R.id.name_tv)
    public void editName() {
        userName.setCursorVisible(true);
    }

    @OnClick(R.id.user_picture)
    public void editPicture() {
        selectImage();
    }

    @OnFocusChange(R.id.name_tv)
    public void onFocusChange(boolean hasFocus) {
        if (!hasFocus) {
            presenter.finishTypingEvent();
        }
    }

    @OnEditorAction(R.id.name_tv)
    public boolean onEditorAction(int actionId, KeyEvent event) {
        if (checkActionId(actionId) || checkActionEvent(event)) {
            presenter.finishTypingEvent();
            userName.setCursorVisible(false);
            return false;
        }
        return true;
    }

    /**
     * on create for the activity
     * @param savedInstanceState activity bundle
     */
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        HappyBirthday.getAppComponent().plus(new LoginScreenModule(this)).inject(this);
        setContentView(R.layout.login_screen_layout);
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
     * add observable to composite disposable
     */
    @Override
    public void addDisposable(Disposable disposable) {
        compositeDisposable.add(disposable);
    }

    /**
     * get activity context
     */
    @Override
    public Context getContext() {
        return getApplicationContext();
    }

    /**
     * set date to birth date button
     * @param newDate date to be set
     */
    @Override
    public void setDateText(Calendar newDate) {
        int style = DateFormat.MEDIUM;
        DateFormat dateFormatter = DateFormat.getDateInstance(style, Locale.US);
        birthDate.setText(dateFormatter.format(newDate.getTime()));
    }

    /**
     * show date not valid toast
     */
    @Override
    public void openDateNotValidToast() {
        Toast.makeText(this, R.string.date_no_valid, Toast.LENGTH_SHORT).show();
    }

    /**
     * set user name to edit text
     * @param prefsName name to be set
     */
    @Override
    public void setNameText(String prefsName) {
        userName.setText(prefsName);
    }

    /**
     * string resource getter
     * @param res resource id
     * @return string resource
     */
    @Override
    public String getStringRes(int res) {
        return getString(res);
    }

    /**
     * set user photo to image view
     * @param photoUri photo path
     */
    @Override
    public void setUserPhoto(String photoUri) {
        changeUserImage(photoUri);
    }

    /**
     * user name getter
     * @return user name text
     */
    @Override
    public String getUserName() {
        return userName.getText().toString();
    }

    /**
     * create main screen activity backgrounds list
     * @return array of backgrounds drawables
     */
    @Override
    public Drawable[] createBackgroundList() {
        return new Drawable[]{getDrawable(R.drawable.android_elephant_popup),
                getDrawable(R.drawable.android_fox_popup), getDrawable(R.drawable.android_pelican_popup)};
    }

    /**
     * create main screen activity round face list
     * @return array of round face drawables
     */
    @Override
    public Drawable[] createRoundFaceList() {
        return new Drawable[]{getDrawable(R.drawable.default_place_holder_yellow),
                getDrawable(R.drawable.default_place_holder_green),
                getDrawable(R.drawable.default_place_holder_blue)};
    }

    /**
     * open gallery intent
     */
    @Override
    public void galleryIntent() {
        Intent intent = new Intent();
        intent.setType(getString(R.string.image_flag));
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, getString(R.string.select_file)), SELECT_FILE);
    }

    /**
     * open camera intent
     */
    @Override
    public void cameraIntent() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_CAMERA);
    }

    /**
     * Callback for the result from requesting permissions. This method
     * is invoked for every call on requestPermissions()
     * @param requestCode The request code passed in requestPermissions
     * @param permissions The requested permissions
     * @param grantResults The grant results for the corresponding permissions
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        presenter.checkPermissionResults(requestCode, grantResults);
    }

    /**
     * Receive the result from a previous call to startActivityForResult
     * @param requestCode The integer request code originally supplied to
     *                    startActivityForResult(), allowing you to identify who this
     *                    result came from.
     * @param resultCode The integer result code returned by the child activity
     *                   through its setResult().
     * @param data An Intent, which can return result data to the caller
     *               (various data can be attached to Intent "extras").
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && data != null) {
            loadImageToView(data);
        }
    }

    /**
     * save user photo to shared prefs
     */
    @Override
    public void saveUserPhotoToPrefs(){
        SharedPreferences.Editor editor = sharedPrefs.edit();
        if (presenter.getUserProfile().getUserPicture() != null) {
            editor.putString(getStringRes(R.string.def_url), presenter.getUserProfile().getUserPicture());
        }
        editor.apply();
    }

    /**
     * save user birth date to shared prefs
     */
    @Override
    public void saveDateChoseToPrefs() {
        SharedPreferences.Editor editor = sharedPrefs.edit();
        editor.putBoolean(getStringRes(R.string.def_date_chose), presenter.getDateHasBeenChosen());
        editor.apply();
    }

    /**
     * save user name to shared prefs
     */
    @Override
    public void saveUserNameToPrefs() {
        SharedPreferences.Editor editor = sharedPrefs.edit();
        editor.putString(getStringRes(R.string.def_name), presenter.getUserProfile().getUserName());
        editor.apply();
    }

    /**
     * save name has been chosen boolean to shared prefs
     */
    @Override
    public void saveNameChoseToPrefs() {
        SharedPreferences.Editor editor = sharedPrefs.edit();
        editor.putBoolean(getStringRes(R.string.def_name_chose), presenter.getNameHasBeenChosen());
        editor.apply();
    }

    /**
     * save date has been chosen boolean to shared prefs
     */
    @Override
    public void saveUserDateToPrefs(){
        SharedPreferences.Editor editor = sharedPrefs.edit();
        UserProfile userProfile = presenter.getUserProfile();
        editor.putInt(getStringRes(R.string.def_year), userProfile.getUserYear());
        editor.putInt(getStringRes(R.string.def_month), userProfile.getUserMonth());
        editor.putInt(getStringRes(R.string.def_day), userProfile.getUserDay());
        editor.apply();
    }

    /**
     * shared prefs getter
     * @return shared prefs instance
     */
    @Override
    public SharedPreferences getDefaultSharedPreferences() {
        return PreferenceManager.getDefaultSharedPreferences(getContext());
    }

    /**
     * permission granted getter
     * @return permission granted id
     */
    @Override
    public int getManagerPermissionGranted() {
        return PERMISSION_GRANTED;
    }

    private void initVars() {
        compositeDisposable = new CompositeDisposable();
        sharedPrefs = PreferenceManager.getDefaultSharedPreferences(getContext());
        AddPhotoAlertBuilder = new AlertDialog.Builder(this);
        presenter.attach();
        presenter.setUserDetails();

    }

    private boolean checkActionEvent(KeyEvent event) {
        return event != null &&
                event.getAction() == KeyEvent.ACTION_DOWN &&
                event.getKeyCode() == KeyEvent.KEYCODE_ENTER;
    }

    private boolean checkActionId(int actionId) {
        return actionId == EditorInfo.IME_ACTION_SEARCH ||
                actionId == EditorInfo.IME_ACTION_DONE;
    }

    private void selectImage() {
        final CharSequence[] items = {getString(R.string.take_photo),
                getString(R.string.choose_from_library), getString(R.string.cancel)};
        createBuilder(items);
        AddPhotoAlertBuilder.show();
    }

    private void createBuilder(CharSequence[] items) {
        AddPhotoAlertBuilder.setTitle(R.string.add_photo);
        AddPhotoAlertBuilder.setItems(items, (dialog, item) -> {
            boolean result = AppPermissions.checkPermission(this);
            checkTask(items[item], dialog, result);
        });
    }

    private void checkTask(CharSequence item, DialogInterface dialog, boolean result) {
        if (checkTaskAction(item, R.string.take_photo)) {
            takePhotoTask(result);
        } else if (checkTaskAction(item, R.string.choose_from_library)) {
            fromGalleryTask(result);
        } else if (checkTaskAction(item, R.string.cancel)) {
            dialog.dismiss();
        }
    }

    private void fromGalleryTask(boolean result) {
        presenter.setUserChosenTask(getString(R.string.choose_from_library));
        if (result) {
            galleryIntent();
        }
    }

    private void takePhotoTask(boolean result) {
        presenter.setUserChosenTask(getString(R.string.take_photo));
        if (result) {
            cameraIntent();
        }
    }

    private boolean checkTaskAction(CharSequence item, int take_photo) {
        return item.equals(getString(take_photo));
    }

    private void loadImageToView(Intent data) {
        Uri selectedImage = data.getData();
        if (selectedImage != null) {
            changeUserImage(selectedImage.toString());
        }
    }

    private void changeUserImage(String selectedImage) {
        if (selectedImage != null) {
            if (selectedImage.isEmpty()){
                setNoImageFound();
            }
            else{
                setUserPictureBackground();
                saveUserPicture(selectedImage);
                showImageWithGlide(selectedImage);
            }
        }
    }

    private void setUserPictureBackground() {
        userPicture.setBackgroundColor(Color.TRANSPARENT);
    }

    private void setNoImageFound() {
        userPicture.setImageResource(R.drawable.ic_photo_white);
        userPicture.setBackgroundColor(getColor(R.color.red_nanit));
    }

    private void showImageWithGlide(String selectedImage) {
        Glide.with(this).load(selectedImage).into(userPicture);
    }

    private void saveUserPicture(String selectedImage) {
        presenter.getUserProfile().setUserPicture(selectedImage);
        saveUserPhotoToPrefs();
    }
}
