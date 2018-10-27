package omerrom.happybirthday.loginActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

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
import omerrom.happybirthday.loginActivity.injection.LoginScreenModule;
import omerrom.happybirthday.utils.AppPermissions;
import omerrom.happybirthday.utils.ChooseImageDialog;
import omerrom.happybirthday.utils.CustomDatePicker;

public class LoginScreenActivity extends AppCompatActivity implements LoginScreenContract.View{


    @Inject
    public LoginScreenContract.UserActionsListener presenter;


    @BindView(R.id.birth_date)
    Button birthDate;


    @BindView(R.id.name_tv)
    EditText userName;


    @BindView(R.id.user_picture)
    ImageView userPicture;

    private CompositeDisposable compositeDisposable;

    private CustomDatePicker customDatePicker;

    public String userChosenTask;

    public static final int NO_PADDING = 0;

    public static final int REQUEST_CAMERA = 1;

    public static final int SELECT_FILE = 2;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        HappyBirthday.getAppComponent().plus(new LoginScreenModule(this)).inject(this);
        setContentView(R.layout.login_screen_layout);
        ButterKnife.bind(this);
        initVars();
    }

    private void initVars() {
        compositeDisposable = new CompositeDisposable();
        customDatePicker = CustomDatePicker.getInstance();
        presenter.setUserDetails();
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
        if (compositeDisposable != null) {
            compositeDisposable.dispose();
        }
        presenter.saveUserDetails();
    }


    @OnClick(R.id.show_birthday_button)
    public void showBirthdayScreen() {
//        if (presenter.birthDayScreenAvailable()) {
//            Intent intent = new Intent(this, MainScreenActivity.class);
//            startActivityForResult(intent, RESULT_OK);
//        }
    }


    @OnClick(R.id.birth_date)
    public void editBirthDate() {
        customDatePicker.show(this.getFragmentManager(), getString(R.string.date_picker));
    }


    @OnClick(R.id.name_tv)
    public void editName() {
        userName.setCursorVisible(true);
    }


    @OnFocusChange(R.id.name_tv)
    public void onFocusChange(View v, boolean hasFocus) {
        if (!hasFocus) {
            presenter.finishTypingEvent();
        }
    }


    @OnEditorAction(R.id.name_tv)
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (checkActionId(actionId) || checkActionEvent(event)) {
            presenter.finishTypingEvent();
            userName.setCursorVisible(false);
            return false;
        }
        return true;
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


    @OnClick(R.id.user_picture)
    public void editPicture() {
        selectImage();
    }


    @Override
    public void addDisposable(Disposable disposable) {
        compositeDisposable.add(disposable);
    }


    @Override
    public Context getContext() {
        return getApplicationContext();
    }


    @Override
    public void setDateText(String date) {
        //TODO: doesn't look fine in the begging
        birthDate.setText(date);
    }


    @Override
    public void openDateNotValidToast() {
        Toast.makeText(this, R.string.date_no_valid, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setNameText(String prefsName) {
        userName.setText(prefsName);
    }

    @Override
    public String getStringRes(int res) {
        return getString(res);
    }

    @Override
    public void setUserPhoto(String photoUri) {
        changeUserImage(photoUri);
    }


    @Override
    public String getUserName() {
        return userName.getText().toString();
    }


    @Override
    public Drawable[] createBackRoundList() {
        return new Drawable[]{getDrawable(R.drawable.android_elephant_popup),
                getDrawable(R.drawable.android_fox_popup), getDrawable(R.drawable.android_pelican_popup)};
    }


    private void selectImage() {
        final CharSequence[] items = {getString(R.string.take_photo),
                getString(R.string.choose_from_library), getString(R.string.cancel)};
        ChooseImageDialog chooseImageDialog = new ChooseImageDialog(this);
        AlertDialog.Builder builder = chooseImageDialog.createDialogBuilder(items);
        builder.show();
    }


    public void galleryIntent() {
        Intent intent = new Intent();
        intent.setType(getString(R.string.image_flag));
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, getString(R.string.select_file)), SELECT_FILE);
    }


    public void cameraIntent() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_CAMERA);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case AppPermissions.MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (userChosenTask.equals(getString(R.string.take_photo)))
                        cameraIntent();
                    else if (userChosenTask.equals(getString(R.string.choose_from_library)))
                        galleryIntent();
                }
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && data != null) {
            loadImageToView(data);
        }
    }


    private void loadImageToView(Intent data) {
        Uri selectedImage = data.getData();
        if (selectedImage != null) {
            changeUserImage(selectedImage.toString());
        }
    }

    private void changeUserImage(String selectedImage) {
        if (selectedImage != null) {
            userPicture.setPadding(NO_PADDING, NO_PADDING, NO_PADDING, NO_PADDING);
            userPicture.setBackgroundColor(Color.TRANSPARENT);
            saveUserPicture(selectedImage);
            Picasso.get()
                    .load(selectedImage)
                    .centerCrop()
                    .fit()
                    .into(userPicture);
        }
    }


    private void saveUserPicture(String selectedImage) {
        presenter.getUserProfile().setUserPicture(selectedImage);
    }


}
