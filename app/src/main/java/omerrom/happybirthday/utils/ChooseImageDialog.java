package omerrom.happybirthday.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.support.annotation.NonNull;

import omerrom.happybirthday.R;
import omerrom.happybirthday.loginActivity.LoginScreenActivity;

public class ChooseImageDialog {

    private final Context context;

    private LoginScreenActivity loginScreenActivity;

    public ChooseImageDialog(LoginScreenActivity loginScreenActivity) {
        this.loginScreenActivity = loginScreenActivity;
        this.context = loginScreenActivity.getContext();
    }

    @NonNull
    public AlertDialog.Builder createDialogBuilder(CharSequence[] items) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(R.string.add_photo);
        builder.setItems(items, (dialog, item) -> {
            boolean result = AppPermissions.checkPermission(context);
            if (items[item].equals(context.getString(R.string.take_photo))) {
                loginScreenActivity.userChosenTask = context.getString(R.string.take_photo);
                if (result)
                    loginScreenActivity.cameraIntent();
            } else if (items[item].equals(context.getString(R.string.choose_from_library))) {
                loginScreenActivity.userChosenTask = context.getString(R.string.choose_from_library);
                if (result)
                    loginScreenActivity.galleryIntent();
            } else if (items[item].equals(context.getString(R.string.cancel))) {
                dialog.dismiss();
            }
        });
        return builder;
    }
}
